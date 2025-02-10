package com.mberktuncer.entity_management_app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapperConcreteUtil implements MapperUtil{

    private final ObjectMapper objectMapper;

    @Override
    public <T> T mapSourceToDestinationType(Object source, Class<T> destinationType) {
        T destinationObject;
        try {
            destinationObject = objectMapper.convertValue(source,destinationType);
        }catch (IllegalArgumentException e){
            log.error("Mapping failure exception:",e);
            throw new RuntimeException(e);
        }
        return destinationObject;
    }

}
