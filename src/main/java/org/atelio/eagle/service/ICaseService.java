package org.atelio.eagle.service;

import org.atelio.eagle.model.caseData.Status;
import org.atelio.eagle.model.caseData.Tag;
import org.atelio.eagle.model.caseData.Transaction;
import org.atelio.eagle.model.demographicInfo.DemographicInfo;

import java.util.List;
import java.util.Optional;

public interface ICaseService {
    Optional<String> getCaseId(String caseId);
    boolean lock(String caseId);
    void unlock(String caseId);
    DemographicInfo getDemographicInfo(String caseId);
    List<Transaction> getTransactions(String caseId);
    void tagTransaction(String caseId, String transactionId, Tag tag);
    void updateCase(String caseId, Tag tag, Status status);
}
