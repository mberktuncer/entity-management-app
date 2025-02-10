package com.mberktuncer.entity_management_app.scheduler;

import com.mberktuncer.entity_management_app.client.GoldEntityClient;
import com.mberktuncer.entity_management_app.model.client.response.RetrieveGoldClientResponse;
import com.mberktuncer.entity_management_app.model.entity.currency.GoldData;
import com.mberktuncer.entity_management_app.repository.currency.GoldCurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoldCurrencyScheduledJob {

    private final GoldEntityClient goldEntityClient;
    private final GoldCurrencyRepository currencyRepository;
    private final List<String> ALLOWED_CURRENCY_CODES = List.of("GA", "GAT22");

    @Scheduled(cron = "0 0/20 * * * ?")
    public void fetchAndSaveCurrencyData() {
        log.info("GoldCurrencyScheduledJob started");
        try {
            List<RetrieveGoldClientResponse> currencyDataList = goldEntityClient.getGoldCurrencyList();
            List<RetrieveGoldClientResponse> filteredGoldCurrencyDataList = currencyDataList
                    .stream()
                    .filter(data -> ALLOWED_CURRENCY_CODES.contains(data.getCode()))
                    .toList();
            for (RetrieveGoldClientResponse response : filteredGoldCurrencyDataList) {
                GoldData currencyData = new GoldData();
                currencyData.setCode(response.getCode());
                currencyData.setPurchase(response.getPurchase());
                currencyData.setSale(response.getSale());
                currencyData.setChange(response.getChange());
                currencyData.setDescription(response.getDescription());
                currencyRepository.save(currencyData);
            }
        } catch (Exception e) {
            log.error("Error while fetching and saving currency data", e);
        }
        log.info("GoldCurrencyScheduledJob finished");
    }
}
