package com.mberktuncer.entity_management_app.service.helper;

import com.mberktuncer.entity_management_app.exception.AccountNotFoundException;
import com.mberktuncer.entity_management_app.exception.ValidationException;
import com.mberktuncer.entity_management_app.model.entity.account.Account;
import com.mberktuncer.entity_management_app.model.entity.account.GoldAccount;
import com.mberktuncer.entity_management_app.model.entity.account.TurkishLiraAccount;
import com.mberktuncer.entity_management_app.model.entity.account.UsdAccount;
import com.mberktuncer.entity_management_app.repository.account.GoldAccountRepository;
import com.mberktuncer.entity_management_app.repository.account.TurkishLiraAccountRepository;
import com.mberktuncer.entity_management_app.repository.account.UsdAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountServiceHelper {

    private final GoldAccountRepository goldAccountRepository;
    private final TurkishLiraAccountRepository turkishLiraAccountRepository;
    private final UsdAccountRepository usdAccountRepository;

    public <T extends Account> Account findEntityById(String id,String accountType){

        return switch (accountType) {
            case "GOLD" -> goldAccountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(
                    String.format("Account not found with id: %s", id))
            );
            case "TL" ->
                    turkishLiraAccountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(
                            String.format("Account not found with id: %s", id))
                    );
            case "USD" -> usdAccountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(
                    String.format("Account not found with id: %s", id))
            );
            default -> throw new ValidationException("Account type not found ");
        };

    }

    public void updateAccount(Account account) {
        switch (account.getAccountType()) {
            case "GOLD":
                goldAccountRepository.save((GoldAccount) account);
                break;
            case "TL":
                turkishLiraAccountRepository.save((TurkishLiraAccount) account);
                break;
            case "USD":
                usdAccountRepository.save((UsdAccount) account);
                break;
            default:
                throw new ValidationException("Account type not found");
        }
    }


    public <T extends Account> void checkAccountForEnable(T entity){
        if (entity.getActive()){
            throw new ValidationException("Account already active");
        }
    }
    public <T extends Account> void checkAccountForDisable(T entity){
        if (!(entity.getActive())){
            throw new ValidationException("Account already disable");
        }
    }

}
