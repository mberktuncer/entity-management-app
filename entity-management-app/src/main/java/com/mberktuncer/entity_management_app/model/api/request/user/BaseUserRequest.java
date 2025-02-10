package com.mberktuncer.entity_management_app.model.api.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class BaseUserRequest {
    private String email;
}
