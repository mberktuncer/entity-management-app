package com.mberktuncer.entity_management_app.controller.transaction;

import com.mberktuncer.entity_management_app.model.api.request.transaction.CreateTransactionRequest;
import com.mberktuncer.entity_management_app.model.api.request.transaction.UpdateTransactionRequest;
import com.mberktuncer.entity_management_app.model.api.response.transaction.CreateTransactionResponse;
import com.mberktuncer.entity_management_app.model.api.response.transaction.RetrieveTransactionResponse;
import com.mberktuncer.entity_management_app.model.api.response.transaction.UpdateTransactionResponse;
import com.mberktuncer.entity_management_app.service.concract.TransactionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mberktuncer.entity_management_app.constants.Constants.QUALIFIER_GOLD;

@RestController
@RequestMapping("/api/transaction/gold")
public class GoldTransactionController {

    private final TransactionService transactionService;

    public GoldTransactionController(@Qualifier(QUALIFIER_GOLD) TransactionService transactionService){
        this.transactionService = transactionService;
    }


    @GetMapping
    public ResponseEntity<List<RetrieveTransactionResponse>> findAll(){
        return ResponseEntity.ok(transactionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetrieveTransactionResponse> findById(@PathVariable String id){
        return ResponseEntity.ok(transactionService.findById(id));
    }

    @GetMapping("/userTransaction/{accountId}")
    public ResponseEntity<List<RetrieveTransactionResponse>> findAllByUserId(@PathVariable String accountId){
        return ResponseEntity.ok(transactionService.findAllByAccountId(accountId));
    }

    @PutMapping("/transfer")
    public ResponseEntity<CreateTransactionResponse> transfer(@RequestBody CreateTransactionRequest createTransactionRequest){
        return ResponseEntity.ok(transactionService.transferToSameAccountType(createTransactionRequest));
    }

    @PutMapping("/add")
    public ResponseEntity<UpdateTransactionResponse> addMoney(@RequestBody UpdateTransactionRequest updateTransactionRequest){
        return ResponseEntity.ok(transactionService.addMoney(updateTransactionRequest));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<UpdateTransactionResponse> withdraw(@RequestBody UpdateTransactionRequest updateTransactionRequest){
        return ResponseEntity.ok(transactionService.withdrawMoney(updateTransactionRequest));
    }


}
