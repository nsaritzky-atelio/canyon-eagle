package org.atelio.eagle.api;

import lombok.Getter;
import lombok.Setter;
import org.atelio.eagle.model.caseData.Status;
import org.atelio.eagle.model.caseData.Tag;
import org.atelio.eagle.service.ICaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cases")
public class CaseController {
  private final ICaseService caseService;
  private static final Logger logger = LoggerFactory.getLogger(CaseController.class);

  @Autowired
  public CaseController(ICaseService caseService) {
    this.caseService = caseService;
  }

  @GetMapping("/ref/{caseRef}")
  public ResponseEntity<?> getCaseIdByReference(@PathVariable String caseRef) {
    logger.info("Finding case with ref {}", caseRef);
    return caseService.getCaseId(caseRef).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{caseId}/lock")
  public ResponseEntity<?> lockCase(@PathVariable String caseId) {
    logger.info("Attempting to lock case: {}", caseId);
    if (caseService.lock(caseId)) {
      logger.info("Successfully locked case: {}", caseId);
      return ResponseEntity.ok().build();
    }
    logger.warn("Failed to lock case: {} - not found", caseId);
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{caseId}/lock")
  public ResponseEntity<?> unlockCase(@PathVariable String caseId) {
    logger.info("Unlocking case: {}", caseId);
    caseService.unlock(caseId);
    logger.info("Successfully unlocked case: {}", caseId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{caseId}/demographicInfo")
  public ResponseEntity<?> getDemographicInfo(@PathVariable String caseId) {
    logger.info("Retrieving demographic info for case: {}", caseId);
    var demographicInfo = caseService.getDemographicInfo(caseId);
    logger.debug("Retrieved demographic info for case: {}", caseId);
    return ResponseEntity.ok().body(demographicInfo);
  }

  @GetMapping("/{caseId}/transactions")
  public ResponseEntity<?> getTransactions(@PathVariable String caseId) {
    logger.info("Retrieving transactions for case: {}", caseId);
    var transactions = caseService.getTransactions(caseId);
    logger.debug("Retrieved {} transactions for case: {}", transactions.size(), caseId);
    return ResponseEntity.ok().body(transactions);
  }

  @Getter
  @Setter
  public static class TransactionTagRequest {
    private Tag tag;
  }

  @PostMapping("/{caseId}/transactions/{transactionId}/tag")
  public ResponseEntity<?> tagTransaction(
          @PathVariable String caseId, @PathVariable String transactionId, @RequestBody TransactionTagRequest transactionTagRequest) {
    logger.info("Tagging transaction {} in case {} with tag {}", transactionId, caseId, transactionTagRequest.getTag());
    caseService.tagTransaction(caseId, transactionId, transactionTagRequest.getTag());
    logger.info("Successfully tagged transaction {} in case {}", transactionId, caseId);
    return ResponseEntity.ok().build();
  }

  @Getter
  @Setter
  public static class UpdateCaseRequest {
    private Status status;
    private Tag tag;
  }

  @PatchMapping("/{caseId}")
  public ResponseEntity<?> updateCase(
          @PathVariable String caseId, @RequestBody UpdateCaseRequest requestBody) {
    logger.info("Updating case {} with status {} and tag {}", caseId, requestBody.getStatus(), requestBody.getTag());
    caseService.updateCase(caseId, requestBody.getTag(), requestBody.getStatus());
    logger.info("Successfully updated case {}", caseId);
    return ResponseEntity.ok().build();
  }
}
