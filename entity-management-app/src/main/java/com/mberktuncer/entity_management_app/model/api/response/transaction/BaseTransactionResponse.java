package com.mberktuncer.entity_management_app.model.api.response.transaction;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseTransactionResponse {
    private String receiverAccountNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String senderAccountNumber;
    private BigDecimal transferAmount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal senderNewBalance;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal receiverNewBalance;
    private String transactionType;
    private LocalDateTime transactionDate;
    private String description;
}
