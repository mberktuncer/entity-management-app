package com.mberktuncer.entity_management_app.util;

import com.mberktuncer.entity_management_app.model.client.response.RetrieveGoldClientResponse;
import com.mberktuncer.entity_management_app.model.client.common.RestRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.mberktuncer.entity_management_app.constants.Constants.REST_GOLD_CURRENCY_URL;

@Component
@RequiredArgsConstructor
@RestController
public class RestUtil {

    private final RestTemplate restTemplate;

    public <T> ResponseEntity<T> sendRequest(RestRequestDto<Object> request, Class<T> responseType){
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType(request.getContentType());
        httpHeaders.setContentType(type);
        httpHeaders.setAccept(List.of(MediaType.ALL));

        Map<String, String> headerMap = request.getHeaderMap();
        if (headerMap != null){
            for (var key : headerMap.keySet()){
                httpHeaders.set(key, headerMap.get(key));
            }
        }

        HttpEntity<?> formEntity = new HttpEntity<>(request.getRequestBody(), httpHeaders);
        return restTemplate.exchange(request.getUrl(), request.getHttpMethod(), formEntity, responseType);
    }

    public <T> ResponseEntity<List<T>> sendRequest(RestRequestDto<Object> request, ParameterizedTypeReference<List<T>> responseType){
        HttpHeaders httpHeaders = new HttpHeaders();

        MediaType type = MediaType.parseMediaType(request.getContentType());
        httpHeaders.setContentType(type);
        httpHeaders.setAccept(List.of(MediaType.ALL));

        Map<String, String> headerMap = request.getHeaderMap();
        if (headerMap != null){
            for (var key : headerMap.keySet()){
                httpHeaders.set(key, headerMap.get(key));
            }
        }

        HttpEntity<?> formEntity = new HttpEntity<>(request.getRequestBody(), httpHeaders);
        return restTemplate.exchange(request.getUrl(), request.getHttpMethod(), formEntity, responseType);
    }



    @RequestMapping("/api/test")
    public ResponseEntity<List<RetrieveGoldClientResponse>> sendRequest(){
        RestRequestDto<Object> restRequestDto = RestRequestDto.builder()
                .url(REST_GOLD_CURRENCY_URL)
                .headerMap(new HashMap<>())
                .httpMethod(HttpMethod.GET)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .build();
        var response = sendRequest(restRequestDto,new ParameterizedTypeReference<List<RetrieveGoldClientResponse>>(){});
        if(response.getStatusCode().is2xxSuccessful()){
            return response;
        }
        return null;
    }
}
