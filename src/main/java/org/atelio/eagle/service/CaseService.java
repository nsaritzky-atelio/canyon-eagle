package org.atelio.demo.service;

import org.atelio.demo.model.caseData.Status;
import org.atelio.demo.model.caseData.Tag;
import org.atelio.demo.model.caseData.Transaction;
import org.atelio.demo.model.demographicInfo.DemographicInfo;
import org.atelio.demo.model.demographicInfo.Type;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CaseService implements ICaseService {
  private final HashMap<String, String> idRefMap = new HashMap<>();

  @Override
  @Nullable
  public Optional<String> getCaseId(String caseRef) {
    try {
      Integer.parseInt(caseRef);
      return Optional.of(getCaseIdFromRef(caseRef));
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  @Override
  public boolean lock(String caseRef) {
    return true;
  }

  @Override
  public void unlock(String caseRef) {
  }

  @Override
  @NonNull
  public DemographicInfo getDemographicInfo(String caseRef) {
    var caseId = getCaseIdFromRef(caseRef);
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
    return List.of(new Transaction("1", "McDonalds", BigDecimal.valueOf(8.27), "USD", "now"));
  }

  @Override
  public void tagTransaction(String caseRef, String transactionId, Tag tag) {

  }

  @Override
  public void updateCase(String caseRef, Tag tag, Status status) {
  }

  private String getCaseIdFromRef(String ref) {
    if (idRefMap.containsKey(ref)) {
      return idRefMap.get(ref);
    } else {
      String id = String.valueOf(idRefMap.size() + 1);
      idRefMap.put(ref, id);
      return id;
    }
  }
}
