package com.mberktuncer.entity_management_app.service.impl.account;

import com.mberktuncer.entity_management_app.exception.AccountNotFoundException;
import com.mberktuncer.entity_management_app.exception.UserNotFoundException;
import com.mberktuncer.entity_management_app.model.api.request.account.CreateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.DeleteAccountRequest;
import com.mberktuncer.entity_management_app.model.api.request.account.UpdateAccountRequest;
import com.mberktuncer.entity_management_app.model.api.response.account.CreateAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.DeleteAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.RetrieveAccountResponse;
import com.mberktuncer.entity_management_app.model.api.response.account.UpdateAccountResponse;
import com.mberktuncer.entity_management_app.model.entity.account.UsdAccount;
import com.mberktuncer.entity_management_app.repository.UserRepository;
import com.mberktuncer.entity_management_app.repository.account.UsdAccountRepository;
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
@Qualifier("UsdAccountService")
public class UsdAccountServiceImpl implements AccountService {
    private final UsdAccountRepository usdAccountRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final AccountServiceHelper accountServiceHelper;

    @Override
    public List<RetrieveAccountResponse> findAll() {
        return usdAccountRepository.findAll()
                .stream()
                .map(usdAccount -> mapperUtil.mapSourceToDestinationType(usdAccount, RetrieveAccountResponse.class))
                .toList();
    }

    @Override
    public RetrieveAccountResponse findById(String id) {
        var entity = accountServiceHelper.findEntityById(id, "Usd");
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

        UsdAccount usdAccount = new UsdAccount();
        usdAccount.setUserId(request.getUserId());
        usdAccount.setBalance(BigDecimal.valueOf(0));
        usdAccount.setAccountType("Usd");

        LocalDateTime now = LocalDateTime.now();
        usdAccount.setCreatedDate(now);
        usdAccount.setUpdatedDate(now);
        usdAccount.setActive(true);

        UsdAccount savedUsdAccount = usdAccountRepository.save(usdAccount);

        return mapperUtil.mapSourceToDestinationType(savedUsdAccount, CreateAccountResponse.class);
    }

    @Override
    public UpdateAccountResponse enable(UpdateAccountRequest request) {
        var entity = (UsdAccount) accountServiceHelper.findEntityById(request.getId(), "Usd");
        accountServiceHelper.checkAccountForEnable(entity);
        entity.setActive(true);
        var savedEntity = usdAccountRepository.save(entity);
        return mapperUtil.mapSourceToDestinationType(savedEntity, UpdateAccountResponse.class);
    }

    @Override
    public UpdateAccountResponse disable(UpdateAccountRequest request) {
        var entity = (UsdAccount) accountServiceHelper.findEntityById(request.getId(), "Usd");
        accountServiceHelper.checkAccountForEnable(entity);
        entity.setActive(false);
        var savedEntity = usdAccountRepository.save(entity);
        return mapperUtil.mapSourceToDestinationType(savedEntity, UpdateAccountResponse.class);
    }

    @Override
    public DeleteAccountResponse delete(DeleteAccountRequest request) {
        var entity = usdAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(
                        String.format("Account not found with id: %s", request.getAccountId())
                ));

        usdAccountRepository.deleteById(entity.getId());
        return mapperUtil.mapSourceToDestinationType(entity, DeleteAccountResponse.class);
    }
}
