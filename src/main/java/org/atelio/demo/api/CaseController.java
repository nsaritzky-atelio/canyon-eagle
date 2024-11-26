package org.atelio.demo.api;

import lombok.Getter;
import lombok.Setter;
import org.atelio.demo.model.caseData.Status;
import org.atelio.demo.model.caseData.Tag;
import org.atelio.demo.service.ICaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cases/{caseRef}")
public class CaseController {
  private final ICaseService caseService;
  private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

  @Autowired
  public CaseController(ICaseService caseService) {
    this.caseService = caseService;
  }

  @GetMapping("/caseId")
  public ResponseEntity<?> get(@PathVariable String caseRef) {
    return caseService.getCaseId(caseRef).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/lock")
  public ResponseEntity<?> lock(@PathVariable String caseRef) {
    logger.info("Attempting to lock case: {}", caseRef);
    if (caseService.lock(caseRef)) {
      logger.info("Successfully locked case: {}", caseRef);
      return ResponseEntity.ok().build();
    }
    logger.warn("Failed to lock case: {} - not found", caseRef);
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/lock")
  public ResponseEntity<?> unlock(@PathVariable String caseRef) {
    logger.info("Unlocking case: {}", caseRef);
    caseService.unlock(caseRef);
    logger.info("Successfully unlocked case: {}", caseRef);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/demographicInfo")
  public ResponseEntity<?> getDemographicInfo(@PathVariable String caseRef) {
    logger.info("Retrieving demographic info for case: {}", caseRef);
    var demographicInfo = caseService.getDemographicInfo(caseRef);
    logger.debug("Retrieved demographic info for case: {}", caseRef);
    return ResponseEntity.ok().body(demographicInfo);
  }

  @GetMapping("/transactions")
  public ResponseEntity<?> getTransactions(@PathVariable String caseRef) {
    logger.info("Retrieving transactions for case: {}", caseRef);
    var transactions = caseService.getTransactions(caseRef);
    logger.debug("Retrieved {} transactions for case: {}", transactions.size(), caseRef);
    return ResponseEntity.ok().body(transactions);
  }

  @Getter
  @Setter
  public static class TransactionTagRequest {
    private Tag tag;
  }

  @PostMapping("/transactions/{transactionId}")
  public ResponseEntity<?> tagTransaction(
      @PathVariable String caseRef, @PathVariable String transactionId, @RequestBody TransactionTagRequest transactionTagRequest) {
    logger.info("Tagging transaction {} in case {} with tag {}", transactionId, caseRef, transactionTagRequest.getTag());
    caseService.tagTransaction(caseRef, transactionId, transactionTagRequest.getTag());
    logger.info("Successfully tagged transaction {} in case {}", transactionId, caseRef);
    return ResponseEntity.ok().build();
  }

  @Getter
  @Setter
  public static class UpdateCaseRequest {
    private Status status;
    private Tag tag;
  }

  @PatchMapping
  public ResponseEntity<?> updateCase(
      @PathVariable String caseRef, @RequestBody UpdateCaseRequest requestBody) {
    logger.info("Updating case {} with status {} and tag {}", caseRef, requestBody.getStatus(), requestBody.getTag());
    caseService.updateCase(caseRef, requestBody.getTag(), requestBody.getStatus());
    logger.info("Successfully updated case {}", caseRef);
    return ResponseEntity.ok().build();
  }
}
