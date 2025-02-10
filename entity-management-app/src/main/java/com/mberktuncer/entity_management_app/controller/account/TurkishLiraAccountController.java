package com.mberktuncer.entity_management_app.controller.account;

import com.mberktuncer.entity_management_app.model.api.request.account.CreateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.DeleteAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.UpdateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.response.account.CreateAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.DeleteAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.RetrieveAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.UpdateAccountResponse;
import com.mberktuncer.entity_management_app.service.concract.AccountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/turkish-lira-account")
public class TurkishLiraAccountController {

    private final AccountService accountService;

    public TurkishLiraAccountController(@Qualifier("TurkishLiraAccountService") AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<RetrieveAccountResponse>> findAll(){
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetrieveAccountResponse> getAccount(@PathVariable String id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CreateAccountResponse> addAccount(@RequestBody CreateAccountRequest createAccountRequest){
        return new ResponseEntity<>(accountService.create(createAccountRequest), HttpStatus.CREATED);
    }

    @PutMapping("/enable")
    public ResponseEntity<UpdateAccountResponse> enableAccount(@RequestBody UpdateAccountRequest updateAccountRequest){
        return new ResponseEntity<>(accountService.enable(updateAccountRequest), HttpStatus.ACCEPTED);
    }

    @PutMapping("/disable")
    public ResponseEntity<UpdateAccountResponse> disableAccount(@RequestBody UpdateAccountRequest updateAccountRequest){
        return new ResponseEntity<>(accountService.disable(updateAccountRequest), HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<DeleteAccountResponse> deleteAccount(@RequestBody DeleteAccountRequest deleteAccountRequest){
        return new ResponseEntity<>(accountService.delete(deleteAccountRequest), HttpStatus.ACCEPTED);
    }

}
