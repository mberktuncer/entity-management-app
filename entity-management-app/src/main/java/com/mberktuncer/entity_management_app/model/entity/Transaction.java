package com.mberktuncer.entity_management_app.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@TypeAlias("Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    private LocalDateTime transactionDate;
    private String description;
    private String transactionType;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal transferAmount;

    private String accountType;


}
