package com.mberktuncer.entity_management_app.model.client.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpMethod;

import java.util.Map;

@AllArgsConstructor
@Getter
@Builder
public class RestRequestDto<T>{

    private String url;
    private HttpMethod httpMethod;
    private T requestBody;

    private String contentType;
    private Map<String, String> headerMap;

    public RestRequestDto(String url, HttpMethod httpMethod, String contentType, Map<String, String> headerMap) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.contentType = contentType;
        this.headerMap = headerMap;
    }
}
