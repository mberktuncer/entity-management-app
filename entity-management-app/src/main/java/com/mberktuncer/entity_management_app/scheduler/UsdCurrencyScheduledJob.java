package com.mberktuncer.entity_management_app.scheduler;

import com.mberktuncer.entity_management_app.client.UsdEntityClient;
import com.mberktuncer.entity_management_app.model.client.response.RetrieveUsdClientResponse;
import com.mberktuncer.entity_management_app.model.entity.currency.UsdData;
import com.mberktuncer.entity_management_app.repository.currency.UsdCurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsdCurrencyScheduledJob {

    private final UsdEntityClient usdEntityClient;
    private final UsdCurrencyRepository usdCurrencyRepository;
    private final String ALLOWED_CURRENCY_CODES = "USD";

    @Scheduled(cron = "0 0/10 * * * ?")
    public void fetchAndSaveCurrencyData() {
        log.info("UsdCurrencyScheduledJob started");
        try {
            List<RetrieveUsdClientResponse> currencyDataList = usdEntityClient.getUsdList();

            List<RetrieveUsdClientResponse> filteredUsdCurrencyDataList = currencyDataList
                    .stream()
                    .filter(data -> ALLOWED_CURRENCY_CODES.contains(data.getCode()))
                    .toList();

            for (RetrieveUsdClientResponse response : filteredUsdCurrencyDataList) {
                UsdData usdCurrencyData = new UsdData();
                usdCurrencyData.setCode(response.getCode());
                usdCurrencyData.setPurchase(response.getPurchase());
                usdCurrencyData.setSale(response.getSale());
                usdCurrencyData.setChange(response.getChange());
                usdCurrencyData.setDescription(response.getDescription());
                usdCurrencyRepository.save(usdCurrencyData);
            }
        } catch (Exception e) {
            log.error("Error while fetching and saving currency data", e);
        }

        log.info("UsdCurrencyScheduledJob finished");
    }

}
