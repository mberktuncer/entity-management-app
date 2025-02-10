package com.mberktuncer.entity_management_app.controller.user;

import com.mberktuncer.entity_management_app.model.api.request.user.CreateUserRequest;
import com.mberktuncer.entity_management_app.model.api.request.user.DeleteUserRequest;
import com.mberktuncer.entity_management_app.model.api.request.user.UpdateUserRequest;
import com.mberktuncer.entity_management_app.model.api.response.user.CreateUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.DeleteUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.RetrieveUserResponse;
import com.mberktuncer.entity_management_app.model.api.response.user.UpdateUserResponse;
import com.mberktuncer.entity_management_app.service.concract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<RetrieveUserResponse>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetrieveUserResponse> getUser(@PathVariable String id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> addUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(userService.save(createUserRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest){
        return new ResponseEntity<>(userService.update(updateUserRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<DeleteUserResponse> deleteById(@RequestBody DeleteUserRequest deleteUserRequest){
        return new ResponseEntity<>(userService.delete(deleteUserRequest), HttpStatus.ACCEPTED);
    }

}
