package org.atelio.demo.service;

import jakarta.validation.constraints.NotNull;
import org.atelio.demo.model.caseData.Status;
import org.atelio.demo.model.caseData.Tag;
import org.atelio.demo.model.caseData.Transaction;
import org.atelio.demo.model.demographicInfo.DemographicInfo;
import org.atelio.demo.model.demographicInfo.Type;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CaseService implements ICaseService {
  private HashMap<String, String> idRefMap = new HashMap<>();

  @Override
  public boolean get(String caseRef) {
    try {
      Integer.parseInt(caseRef);
      getCaseIdFromRef(caseRef);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean lock(String caseRef) {
    getCaseIdFromRef(caseRef);
    return true;
  }

  @Override
  public void unlock(String caseRef) {
    getCaseIdFromRef(caseRef);
  }

  @Override
  @NonNull
  public DemographicInfo getDemographicInfo(String caseRef) {
    getCaseIdFromRef(caseRef);
    return new DemographicInfo(
        "Hans Gruber",
        Type.BUSINESS,
        "9876",
        null,
        "2121 Avenue of the Stars",
        List.of(),
        "2121 Avenue of the Stars");
  }

  @Override
  public List<Transaction> getTransactions(String caseRef) {
    getCaseIdFromRef(caseRef);
    return List.of();
  }

  @Override
  public void tagTransaction(String caseRef, String transactionId, Tag tag) {
    getCaseIdFromRef(caseRef);
  }

  @Override
  public void updateCase(String caseRef, Tag tag, Status status) {
    getCaseIdFromRef(caseRef);
  }

  private String getCaseIdFromRef(String ref) {
    if (idRefMap.containsKey(ref)) {
      return idRefMap.get(ref);
    } else {
      String id = String.valueOf(idRefMap.size() + 1);
      idRefMap.put(id, ref);
      return id;
    }
  }
}
