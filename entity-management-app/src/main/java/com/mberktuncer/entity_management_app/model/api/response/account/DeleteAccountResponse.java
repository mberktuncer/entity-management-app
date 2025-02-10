package com.mberktuncer.entity_management_app.model.api.response.account;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteAccountResponse extends BaseAccountResponse{
    private String accountId;
}
