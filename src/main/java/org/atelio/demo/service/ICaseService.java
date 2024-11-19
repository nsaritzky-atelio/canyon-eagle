package org.atelio.demo.service;

import org.atelio.demo.model.caseData.Status;
import org.atelio.demo.model.caseData.Tag;
import org.atelio.demo.model.caseData.Transaction;
import org.atelio.demo.model.demographicInfo.DemographicInfo;

import java.util.List;

public interface ICaseService {
    boolean get(String caseId);
    boolean lock(String caseId);
    void unlock(String caseId);
    DemographicInfo getDemographicInfo(String caseId);
    List<Transaction> getTransactions(String caseId);
    void tagTransaction(String caseId, String transactionId, Tag tag);
    void updateCase(String caseId, Tag tag, Status status);
}
