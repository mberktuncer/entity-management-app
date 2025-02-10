package com.mberktuncer.entity_management_app.service.impl.account;

import com.mberktuncer.entity_management_app.exception.AccountNotFoundException;
import com.mberktuncer.entity_management_app.exception.UserNotFoundException;
import com.mberktuncer.entity_management_app.exception.ValidationException;
import com.mberktuncer.entity_management_app.model.api.request.account.CreateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.DeleteAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.UpdateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.response.account.*;
import com.mberktuncer.entity_management_app.model.entity.account.GoldAccount;
import com.mberktuncer.entity_management_app.repository.UserRepository;
import com.mberktuncer.entity_management_app.repository.account.GoldAccountRepository;
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
@Qualifier("GoldAccountService")
public class GoldAccountServiceImpl implements AccountService {
    private final GoldAccountRepository goldAccountRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final AccountServiceHelper accountServiceHelper;

    @Override
    public List<RetrieveAccountResponse> findAll() {
        return goldAccountRepository.findAll()
                .stream()
                .map(goldAccount -> mapperUtil.mapSourceToDestinationType(goldAccount,RetrieveAccountResponse.class))
                .toList();
    }

    @Override
    public RetrieveAccountResponse findById(String id) {
        var entity = accountServiceHelper.findEntityById(id,"Gold");
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

        GoldAccount goldAccount = new GoldAccount();
        goldAccount.setUserId(request.getUserId());
        goldAccount.setBalance(BigDecimal.valueOf(0));
        goldAccount.setAccountType("Gold");

        LocalDateTime now = LocalDateTime.now();
        goldAccount.setUpdatedDate(now);
        goldAccount.setCreatedDate(now);
        goldAccount.setActive(true);

        GoldAccount savedGoldAccount = goldAccountRepository.save(goldAccount);

        return mapperUtil.mapSourceToDestinationType(savedGoldAccount, CreateAccountResponse.class);
    }

    @Override
    public UpdateAccountResponse enable(UpdateAccountRequest request) {
        var entity = (GoldAccount) accountServiceHelper.findEntityById(request.getId(),"Gold");
        accountServiceHelper.checkAccountForEnable(entity);
        entity.setActive(true);
        var savedEntity = goldAccountRepository.save(entity);
        return mapperUtil.mapSourceToDestinationType(savedEntity, UpdateAccountResponse.class);
    }

    @Override
    public UpdateAccountResponse disable(UpdateAccountRequest request) {
        var entity =(GoldAccount) accountServiceHelper.findEntityById(request.getId(),"Gold");
        accountServiceHelper.checkAccountForEnable(entity);
        entity.setActive(false);
        var savedEntity = goldAccountRepository.save(entity);
        return mapperUtil.mapSourceToDestinationType(savedEntity, UpdateAccountResponse.class);
    }


    @Override
    public DeleteAccountResponse delete(DeleteAccountRequest request) {
        var entity = goldAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account not found with id: %s", request.getAccountId())
        ));

        goldAccountRepository.deleteById(entity.getId());
        return mapperUtil.mapSourceToDestinationType(entity, DeleteAccountResponse.class);

    }
}
