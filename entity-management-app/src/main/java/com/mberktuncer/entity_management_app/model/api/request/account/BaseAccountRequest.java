package com.mberktuncer.entity_management_app.model.api.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class BaseAccountRequest {
    private String userId;
}
