package com.mberktuncer.entity_management_app.service.helper;

import com.mberktuncer.entity_management_app.exception.AccountNotFoundException;
import com.mberktuncer.entity_management_app.exception.UserNotFoundException;
import com.mberktuncer.entity_management_app.model.entity.Transaction;
import com.mberktuncer.entity_management_app.model.entity.User;
import com.mberktuncer.entity_management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {

    private final UserRepository userRepository;

    public User findEntityById(String id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("User not found with id: %s", id))
        );
    }

    public boolean isEmailExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
