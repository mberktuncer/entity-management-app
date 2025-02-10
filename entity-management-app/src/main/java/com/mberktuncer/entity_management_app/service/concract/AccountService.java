package com.mberktuncer.entity_management_app.service.concract;

import com.mberktuncer.entity_management_app.model.api.request.account.CreateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.DeleteAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.UpdateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.response.account.CreateAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.DeleteAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.RetrieveAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.UpdateAccountResponse;
import com.mberktuncer.entity_management_app.model.entity.account.GoldAccount;

import java.util.List;

public interface AccountService {

    List<RetrieveAccountResponse> findAll();
    RetrieveAccountResponse findById(String id);
    List<RetrieveAccountResponse> findByUserId (String id);
    CreateAccountResponse create(CreateAccountRequest request);
    UpdateAccountResponse enable(UpdateAccountRequest request);
    UpdateAccountResponse disable(UpdateAccountRequest request);
    DeleteAccountResponse delete(DeleteAccountRequest request);

}
