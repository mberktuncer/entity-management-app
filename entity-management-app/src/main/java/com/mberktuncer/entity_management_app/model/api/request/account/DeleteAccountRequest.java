package com.mberktuncer.entity_management_app.model.api.request.account;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteAccountRequest extends BaseAccountRequest{
    private String accountId;
}
