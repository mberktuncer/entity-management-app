package com.mberktuncer.entity_management_app.model.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseErrorResponse {
    private String message;

    private int errorCode;
}
