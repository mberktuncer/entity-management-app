package com.mberktuncer.entity_management_app.model.api.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateUserRequest extends BaseUserRequest{
    private String id;
    private long userNumber;
}
