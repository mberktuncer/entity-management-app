package com.mberktuncer.entity_management_app.service.concract;

import com.mberktuncer.entity_management_app.model.api.request.user.CreateUserRequest;
import com.mberktuncer.entity_management_app.model.api.request.user.DeleteUserRequest;
import com.mberktuncer.entity_management_app.model.api.request.user.UpdateUserRequest;
import com.mberktuncer.entity_management_app.model.api.response.user.CreateUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.DeleteUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.RetrieveUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.UpdateUserResponse;

import java.util.List;

public interface UserService {

    List<RetrieveUserResponse> findAll();
    RetrieveUserResponse findById(String id);
    CreateUserResponse save(CreateUserRequest createUserRequest);
    UpdateUserResponse update(UpdateUserRequest updateUserRequest);
    DeleteUserResponse delete(DeleteUserRequest deleteUserRequest);

}
