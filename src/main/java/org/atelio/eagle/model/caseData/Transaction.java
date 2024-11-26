package org.atelio.eagle.model.caseData;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@JsonSerialize
@Getter
@AllArgsConstructor
@ToString
public class Transaction {
    private String transactionId;

    private String payee;

    private BigDecimal amount;

    private String currency;

    private String timestamp;
}
