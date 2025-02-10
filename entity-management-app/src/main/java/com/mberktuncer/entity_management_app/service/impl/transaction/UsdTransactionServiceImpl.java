package com.mberktuncer.entity_management_app.service.impl.transaction;

import com.mberktuncer.entity_management_app.model.api.request.transaction.CreateTransactionRequest;
import com.mberktuncer.entity_management_app.model.api.request.transaction.UpdateTransactionRequest;
import com.mberktuncer.entity_management_app.model.api.response.transaction.CreateTransactionResponse;
import com.mberktuncer.entity_management_app.model.api.response.transaction.RetrieveTransactionResponse;
import com.mberktuncer.entity_management_app.model.api.response.transaction.UpdateTransactionResponse;
import com.mberktuncer.entity_management_app.model.entity.Transaction;
import com.mberktuncer.entity_management_app.model.entity.account.UsdAccount;
import com.mberktuncer.entity_management_app.repository.TransactionRepository;
import com.mberktuncer.entity_management_app.service.concract.TransactionService;
import com.mberktuncer.entity_management_app.service.helper.AccountServiceHelper;
import com.mberktuncer.entity_management_app.service.helper.TransactionServiceHelper;
import com.mberktuncer.entity_management_app.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.mberktuncer.entity_management_app.constants.Constants.*;

@Service
@RequiredArgsConstructor
@Qualifier(QUALIFIER_USD)
public class UsdTransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final MapperUtil mapperUtil;
    private final TransactionServiceHelper transactionServiceHelper;
    private final AccountServiceHelper accountServiceHelper;

    @Override
    public List<RetrieveTransactionResponse> findAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> mapperUtil.mapSourceToDestinationType(transaction, RetrieveTransactionResponse.class))
                .toList();
    }

    @Override
    public List<RetrieveTransactionResponse> findAllByAccountId(String accountId) {
        var entity = accountServiceHelper.findEntityById(accountId, ACCOUNT_TYPE_USD);
        return transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getReceiverAccountNumber().equals(entity.getId()))
                .map(transaction -> mapperUtil.mapSourceToDestinationType(transaction, RetrieveTransactionResponse.class))
                .toList();
    }

    @Override
    public RetrieveTransactionResponse findById(String id) {
        var entity = transactionServiceHelper.findEntityById(id);
        return mapperUtil.mapSourceToDestinationType(entity, RetrieveTransactionResponse.class);
    }

    @Override
    public CreateTransactionResponse transferToSameAccountType(CreateTransactionRequest createTransactionRequest) {
        var receiverEntity = accountServiceHelper.findEntityById(createTransactionRequest.getReceiverAccountNumber(),
                ACCOUNT_TYPE_USD);
        var senderEntity = accountServiceHelper.findEntityById(createTransactionRequest.getSenderAccountNumber(),
                ACCOUNT_TYPE_USD);

        var receiverExistingBalance = receiverEntity.getBalance();
        var senderExistingBalance = senderEntity.getBalance();

        transactionServiceHelper.checkBalanceForTransaction(senderExistingBalance, createTransactionRequest.getTransferAmount());
        var receiverNewBalance = receiverExistingBalance.add(createTransactionRequest.getTransferAmount());
        var senderNewBalance = senderExistingBalance.subtract(createTransactionRequest.getTransferAmount());

        receiverEntity.setBalance(receiverNewBalance);
        senderEntity.setBalance(senderNewBalance);
        accountServiceHelper.updateAccount(receiverEntity);
        accountServiceHelper.updateAccount(senderEntity);


        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(now);
        transaction.setDescription(createTransactionRequest.getDescription());
        transaction.setTransactionType(TRANSACTION_TYPE_AA);
        transaction.setSenderAccountNumber(createTransactionRequest.getSenderAccountNumber());
        transaction.setReceiverAccountNumber(createTransactionRequest.getReceiverAccountNumber());
        transaction.setTransferAmount(createTransactionRequest.getTransferAmount());
        transactionRepository.save(transaction);

        CreateTransactionResponse response = mapperUtil.mapSourceToDestinationType(transaction, CreateTransactionResponse.class);
        response.setSenderNewBalance(senderNewBalance);
        response.setReceiverNewBalance(receiverNewBalance);

        return response;
    }

    @Override
    public CreateTransactionResponse transferToDifferentAccountType(CreateTransactionRequest createTransactionRequest) {

        var receiverEntity = accountServiceHelper.findEntityById(createTransactionRequest.getReceiverAccountNumber(),
                createTransactionRequest.getReceiverAccountType());
        var senderEntity = accountServiceHelper.findEntityById(createTransactionRequest.getSenderAccountNumber(),
                ACCOUNT_TYPE_USD);

        var senderExistingBalance = senderEntity.getBalance();
        transactionServiceHelper.checkBalanceForTransaction(senderExistingBalance, createTransactionRequest.getTransferAmount());

        BigDecimal exchangeRate = transactionServiceHelper.getExchangeRate(createTransactionRequest.getReceiverAccountType());
        var convertedAmount = createTransactionRequest.getTransferAmount().multiply(exchangeRate);

        var receiverExistingBalance = receiverEntity.getBalance();
        var receiverNewBalance = receiverExistingBalance.add(convertedAmount);
        var senderNewBalance = senderExistingBalance.subtract(createTransactionRequest.getTransferAmount());

        receiverEntity.setBalance(receiverNewBalance);
        senderEntity.setBalance(senderNewBalance);
        accountServiceHelper.updateAccount(receiverEntity);
        accountServiceHelper.updateAccount(senderEntity);

        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(now);
        transaction.setDescription(createTransactionRequest.getDescription());
        transaction.setTransactionType(TRANSACTION_TYPE_AA);
        transaction.setSenderAccountNumber(createTransactionRequest.getSenderAccountNumber());
        transaction.setReceiverAccountNumber(createTransactionRequest.getReceiverAccountNumber());
        transaction.setTransferAmount(createTransactionRequest.getTransferAmount());
        transaction.setAccountType(createTransactionRequest.getReceiverAccountType());
        transactionRepository.save(transaction);

        CreateTransactionResponse response = mapperUtil.mapSourceToDestinationType(transaction, CreateTransactionResponse.class);
        response.setSenderNewBalance(senderNewBalance);
        response.setReceiverNewBalance(receiverNewBalance);

        return response;
    }

    @Override
    public UpdateTransactionResponse addMoney(UpdateTransactionRequest updateTransactionRequest) {
        var usdAccount = (UsdAccount) accountServiceHelper.findEntityById(updateTransactionRequest.getReceiverAccountNumber(),ACCOUNT_TYPE_USD);
        var existingBalance = usdAccount.getBalance();
        var newBalance = existingBalance.add(updateTransactionRequest.getTransferAmount());
        transactionServiceHelper.saveFinalAmountToAccountByAccountType(usdAccount, ACCOUNT_TYPE_USD, newBalance);
        return transactionServiceHelper.mapToUpdateTransactionResponse(updateTransactionRequest, usdAccount, newBalance);
    }

    @Override
    public UpdateTransactionResponse withdrawMoney(UpdateTransactionRequest updateTransactionRequest) {
        var usdAccount = (UsdAccount) accountServiceHelper.findEntityById(updateTransactionRequest.getReceiverAccountNumber(),ACCOUNT_TYPE_USD);
        var existingBalance = usdAccount.getBalance();
        transactionServiceHelper.checkBalanceForTransaction(existingBalance, updateTransactionRequest.getTransferAmount());
        var newBalance = existingBalance.subtract(updateTransactionRequest.getTransferAmount());
        transactionServiceHelper.saveFinalAmountToAccountByAccountType(usdAccount, ACCOUNT_TYPE_USD, newBalance);
        return transactionServiceHelper.mapToUpdateTransactionResponse(updateTransactionRequest, usdAccount, newBalance);
    }
}
