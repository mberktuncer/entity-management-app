package com.mberktuncer.entity_management_app.service.impl.account;


import com.mberktuncer.entity_management_app.exception.AccountNotFoundException;
import com.mberktuncer.entity_management_app.exception.UserNotFoundException;
import com.mberktuncer.entity_management_app.exception.ValidationException;
import com.mberktuncer.entity_management_app.model.api.request.account.CreateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.DeleteAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.UpdateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.response.account.CreateAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.DeleteAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.RetrieveAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.UpdateAccountResponse;
import com.mberktuncer.entity_management_app.model.entity.account.GoldAccount;
import com.mberktuncer.entity_management_app.model.entity.account.TurkishLiraAccount;
import com.mberktuncer.entity_management_app.model.entity.account.UsdAccount;
import com.mberktuncer.entity_management_app.repository.UserRepository;
import com.mberktuncer.entity_management_app.repository.account.TurkishLiraAccountRepository;
import com.mberktuncer.entity_management_app.service.concract.AccountService;
import com.mberktuncer.entity_management_app.service.helper.AccountServiceHelper;
import com.mberktuncer.entity_management_app.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Qualifier("TurkishLiraAccountService")
public class TurkishLiraAccountServiceImpl implements AccountService {

    private final TurkishLiraAccountRepository turkishLiraAccountRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final AccountServiceHelper accountServiceHelper;

    @Override
    public List<RetrieveAccountResponse> findAll() {
        return turkishLiraAccountRepository.findAll()
                .stream()
                .map(turkishLiraAccount -> mapperUtil.mapSourceToDestinationType(turkishLiraAccount, RetrieveAccountResponse.class))
                .toList();
    }

    @Override
    public RetrieveAccountResponse findById(String id) {
        var entity = accountServiceHelper.findEntityById(id, "TurkishLira");
        return mapperUtil.mapSourceToDestinationType(entity, RetrieveAccountResponse.class);
    }

    @Override
    public List<RetrieveAccountResponse> findByUserId(String id) {
        return null;
    }

    @Override
    public CreateAccountResponse create(CreateAccountRequest request) {
        userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id - " + request.getUserId()));

        TurkishLiraAccount turkishLiraAccount = new TurkishLiraAccount();
        turkishLiraAccount.setUserId(request.getUserId());
        turkishLiraAccount.setBalance(BigDecimal.valueOf(0));
        turkishLiraAccount.setAccountType("TurkishLira");

        LocalDateTime now = LocalDateTime.now();
        turkishLiraAccount.setUpdatedDate(now);
        turkishLiraAccount.setCreatedDate(now);
        turkishLiraAccount.setActive(true);

        TurkishLiraAccount savedTurkishLiraAccount = turkishLiraAccountRepository.save(turkishLiraAccount);

        return mapperUtil.mapSourceToDestinationType(savedTurkishLiraAccount, CreateAccountResponse.class);
    }

    @Override
    public UpdateAccountResponse enable(UpdateAccountRequest request) {

        var entity = (TurkishLiraAccount) accountServiceHelper.findEntityById(request.getId(),"TurkishLira");
        accountServiceHelper.checkAccountForEnable(entity);
        entity.setActive(true);

        var savedEntity = turkishLiraAccountRepository.save(entity);

        return mapperUtil.mapSourceToDestinationType(savedEntity, UpdateAccountResponse.class);
    }

    @Override
    public UpdateAccountResponse disable(UpdateAccountRequest request) {
        var entity = (TurkishLiraAccount) accountServiceHelper.findEntityById(request.getId(), "Usd");
        accountServiceHelper.checkAccountForEnable(entity);
        entity.setActive(false);
        var savedEntity = turkishLiraAccountRepository.save(entity);
        return mapperUtil.mapSourceToDestinationType(savedEntity, UpdateAccountResponse.class);
    }

    @Override
    public DeleteAccountResponse delete(DeleteAccountRequest request) {
        var entity = turkishLiraAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account not found with id: %s", request.getAccountId())
                ));

        turkishLiraAccountRepository.deleteById(entity.getId());
        return mapperUtil.mapSourceToDestinationType(entity, DeleteAccountResponse.class);
    }
}
