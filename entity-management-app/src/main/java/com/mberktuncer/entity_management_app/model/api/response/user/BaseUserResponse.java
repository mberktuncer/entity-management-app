package com.mberktuncer.entity_management_app.model.api.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseUserResponse {

    private String id;
    private String email;

}
