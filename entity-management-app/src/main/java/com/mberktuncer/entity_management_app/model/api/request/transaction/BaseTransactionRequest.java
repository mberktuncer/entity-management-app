package com.mberktuncer.entity_management_app.model.api.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class BaseTransactionRequest {
    private String receiverAccountNumber;
    private BigDecimal transferAmount;
    private String description;
}
