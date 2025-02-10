package com.mberktuncer.entity_management_app.service.concract;

import com.mberktuncer.entity_management_app.model.api.request.transaction.CreateTransactionRequest;
import com.mberktuncer.entity_management_app.model.api.request.transaction.UpdateTransactionRequest;
import com.mberktuncer.entity_management_app.model.api.response.transaction.CreateTransactionResponse;
import com.mberktuncer.entity_management_app.model.api.response.transaction.RetrieveTransactionResponse;
import com.mberktuncer.entity_management_app.model.api.response.transaction.UpdateTransactionResponse;

import java.util.List;


public interface TransactionService {

    List<RetrieveTransactionResponse> findAll();
    List<RetrieveTransactionResponse> findAllByAccountId(String id);
    RetrieveTransactionResponse findById(String id);
    CreateTransactionResponse transferToSameAccountType(CreateTransactionRequest createTransactionRequest);
    CreateTransactionResponse transferToDifferentAccountType(CreateTransactionRequest createTransactionRequest);
    UpdateTransactionResponse addMoney(UpdateTransactionRequest updateTransactionRequest);
    UpdateTransactionResponse withdrawMoney(UpdateTransactionRequest updateTransactionRequest);

}
