package com.mberktuncer.entity_management_app.service.helper;

import com.mberktuncer.entity_management_app.exception.AccountNotFoundException;
import com.mberktuncer.entity_management_app.exception.ValidationException;
import com.mberktuncer.entity_management_app.model.api.request.transaction.UpdateTransactionRequest;
import com.mberktuncer.entity_management_app.model.api.response.transaction.UpdateTransactionResponse;
import com.mberktuncer.entity_management_app.model.entity.currency.GoldData;
import com.mberktuncer.entity_management_app.model.entity.Transaction;
import com.mberktuncer.entity_management_app.model.entity.currency.UsdData;
import com.mberktuncer.entity_management_app.model.entity.account.Account;
import com.mberktuncer.entity_management_app.model.entity.account.GoldAccount;
import com.mberktuncer.entity_management_app.model.entity.account.TurkishLiraAccount;
import com.mberktuncer.entity_management_app.model.entity.account.UsdAccount;
import com.mberktuncer.entity_management_app.repository.TransactionRepository;
import com.mberktuncer.entity_management_app.repository.account.GoldAccountRepository;
import com.mberktuncer.entity_management_app.repository.account.TurkishLiraAccountRepository;
import com.mberktuncer.entity_management_app.repository.account.UsdAccountRepository;
import com.mberktuncer.entity_management_app.repository.currency.GoldCurrencyRepository;
import com.mberktuncer.entity_management_app.repository.currency.UsdCurrencyRepository;
import com.mberktuncer.entity_management_app.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.mberktuncer.entity_management_app.constants.Constants.*;

@Component
@RequiredArgsConstructor
public class TransactionServiceHelper {

    private final TransactionRepository transactionRepository;
    private final GoldAccountRepository goldAccountRepository;
    private final TurkishLiraAccountRepository turkishLiraAccountRepository;
    private final UsdAccountRepository usdAccountRepository;
    private final MapperUtil mapperUtil;
    private final UsdCurrencyRepository usdCurrencyRepository;
    private final GoldCurrencyRepository goldCurrencyRepository;


    public Transaction findEntityById(String id){
        return transactionRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(
                String.format("Transaction not found with id: %s", id))
        );
    }

    public <T extends Account> void saveFinalAmountToAccountByAccountType(T account, String accountType, BigDecimal newBalance){
        switch (accountType){
            case ACCOUNT_TYPE_GOLD:
                GoldAccount goldAccount = (GoldAccount) account;
                goldAccount.setBalance(newBalance);
                goldAccountRepository.save(goldAccount);
                break;
            case ACCOUNT_TYPE_TL:
                TurkishLiraAccount turkishLiraAccount = (TurkishLiraAccount) account;
                turkishLiraAccount.setBalance(newBalance);
                turkishLiraAccountRepository.save(turkishLiraAccount);
                break;
            case ACCOUNT_TYPE_USD:
                UsdAccount usdAccount = (UsdAccount) account;
                usdAccount.setBalance(newBalance);
                usdAccountRepository.save(usdAccount);
                break;

            default:
                throw new ValidationException("Account type not found ");
        }
    }

    public void checkBalanceForTransaction(BigDecimal currentBalance, BigDecimal transactionAmount){

        if (currentBalance.compareTo(transactionAmount) < 0){
            throw new ValidationException("Insufficient balance !!");
        }

    }

    public UpdateTransactionResponse mapToUpdateTransactionResponse(UpdateTransactionRequest updateTransactionRequest, Account account, BigDecimal newBalance) {

        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(now);
        transaction.setDescription(updateTransactionRequest.getDescription());
        transaction.setTransactionType(TRANSACTION_TYPE_SELF);
        transaction.setAccountType(account.getAccountType());
        transaction.setTransferAmount(updateTransactionRequest.getTransferAmount());
        transaction.setReceiverAccountNumber(updateTransactionRequest.getReceiverAccountNumber());
        transactionRepository.save(transaction);

        return mapperUtil.mapSourceToDestinationType(transaction, UpdateTransactionResponse.class);
    }

    public BigDecimal getExchangeRate(String toCurrency) {

        switch (toCurrency) {
            case "TL":
                UsdData usdData = usdCurrencyRepository.findFirstByCodeOrderByIdDesc(ACCOUNT_TYPE_USD)
                        .orElseThrow(() -> new ValidationException("USD rate not found"));
                return new BigDecimal(usdData.getSale().replace(",", "."));
            case "GOLD":
                GoldData goldData = goldCurrencyRepository.findFirstByCodeOrderByIdDesc("GA")
                        .orElseThrow(() -> new ValidationException("USD to GOLD rate not found"));
                return new BigDecimal(goldData.getSale().replace(".", "").replace(",", "."));



            default:
                throw new UnsupportedOperationException("Currency conversion not supported for: USD to " + toCurrency);
        }
    }

}
