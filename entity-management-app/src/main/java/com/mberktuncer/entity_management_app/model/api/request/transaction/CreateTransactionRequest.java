package com.mberktuncer.entity_management_app.model.api.request.transaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateTransactionRequest extends BaseTransactionRequest{

    private String currencyCode;
    private String senderAccountNumber;
    private String receiverAccountType;

}
