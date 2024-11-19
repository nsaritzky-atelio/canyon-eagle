package org.atelio.demo.model.caseData;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
public class Transaction {
    private String transactionId;

    private String payee;

    private BigDecimal amount;

    private String currency;

    private String timestamp;
}
