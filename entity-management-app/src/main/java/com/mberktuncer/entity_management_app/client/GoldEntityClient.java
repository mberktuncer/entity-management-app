package com.mberktuncer.entity_management_app.client;

import com.mberktuncer.entity_management_app.exception.ClientException;
import com.mberktuncer.entity_management_app.model.client.response.RetrieveGoldClientResponse;
import com.mberktuncer.entity_management_app.model.client.common.RestRequestDto;
import com.mberktuncer.entity_management_app.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoldEntityClient {

    private final RestUtil restUtil;

    public List<RetrieveGoldClientResponse> getGoldCurrencyList(){
        try {
            return sendRequestForRetrieveGoldCurrency();
        }
        catch (RestClientException restClientException){
            log.error("[GoldEntityClient][getGoldCurrencyList][RestClientException]:",restClientException);
            throw new ClientException(
                    String.format(
                            "Altinkaynak dış servisinde hata alindi: %s",
                            restClientException.getMessage()
                    )
            );
        }
        catch (Exception e){
            log.error("[GoldEntityClient][getGoldCurrencyList][Exception]:",e);
            throw new ClientException(
                    String.format(
                            "Altinkaynak dış servisinde hata alindi"

                    )
            );
        }

    }

    private List<RetrieveGoldClientResponse> sendRequestForRetrieveGoldCurrency(){
        RestRequestDto<Object> restRequestDto = RestRequestDto.builder()
                .url("https://rest.altinkaynak.com/Gold.json")
                .headerMap(new HashMap<>())
                .httpMethod(HttpMethod.GET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .build();
        var response = restUtil.sendRequest(restRequestDto,new ParameterizedTypeReference<List<RetrieveGoldClientResponse>>(){});
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }
       throw new ClientException(
               String.format("Error while send request to %s",
                       "https://rest.altinkaynak.com/Gold.json"
                       )
       );
    }
}
