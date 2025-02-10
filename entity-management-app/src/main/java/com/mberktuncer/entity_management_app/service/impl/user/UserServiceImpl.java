package com.mberktuncer.entity_management_app.service.impl.user;

import com.mberktuncer.entity_management_app.exception.EmailAlreadyExistException;
import com.mberktuncer.entity_management_app.exception.UserNotFoundException;
import com.mberktuncer.entity_management_app.model.api.request.user.CreateUserRequest;
import com.mberktuncer.entity_management_app.model.api.request.user.DeleteUserRequest;
import com.mberktuncer.entity_management_app.model.api.request.user.UpdateUserRequest;
import com.mberktuncer.entity_management_app.model.api.response.user.CreateUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.DeleteUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.RetrieveUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.UpdateUserResponse;
import com.mberktuncer.entity_management_app.model.entity.User;
import com.mberktuncer.entity_management_app.repository.UserRepository;
import com.mberktuncer.entity_management_app.service.concract.UserService;
import com.mberktuncer.entity_management_app.service.helper.UserServiceHelper;
import com.mberktuncer.entity_management_app.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;
    private final MapperUtil mapperUtil;
    @Override
    public List<RetrieveUserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map(user -> mapperUtil.mapSourceToDestinationType(user, RetrieveUserResponse.class))
                .toList();

    }

    @Override
    public RetrieveUserResponse findById(String id) {
        var entity = userServiceHelper.findEntityById(id);
        return mapperUtil.mapSourceToDestinationType(entity, RetrieveUserResponse.class);
    }

    @Override
    public CreateUserResponse save(CreateUserRequest createUserRequest) {

        User user = new User();

        if (userServiceHelper.isEmailExist(createUserRequest.getEmail())){
            throw new EmailAlreadyExistException("Email already used !");
        }
        else{
            user.setEmail(createUserRequest.getEmail());
        }

        User savedUser = userRepository.save(user);

        return mapperUtil.mapSourceToDestinationType(savedUser, CreateUserResponse.class);
    }

    @Override
    public UpdateUserResponse update(UpdateUserRequest updateUserRequest) {
        var entity = userRepository.findById(updateUserRequest.getId());

        if (entity.isPresent()){
            var userData = entity.get();
            userData.setEmail(updateUserRequest.getEmail());
            userRepository.save(userData);
            return mapperUtil.mapSourceToDestinationType(userData, UpdateUserResponse.class);
        }
        else{
            throw new UserNotFoundException("User not found with id - " + updateUserRequest.getId());
        }

    }

    @Override
    public DeleteUserResponse delete(DeleteUserRequest deleteUserRequest) {
        var entity = userRepository.findById(deleteUserRequest.getId());
        if (entity.isPresent()){
            var userData = entity.get();
            userRepository.deleteById(userData.getId());
            return mapperUtil.mapSourceToDestinationType(userData, DeleteUserResponse.class);
        }else{
            throw new UserNotFoundException("User not found with id - " + deleteUserRequest.getId());
        }

    }
}
