package com.mberktuncer.entity_management_app.util;

public interface MapperUtil {
    <T> T mapSourceToDestinationType(Object source,Class<T> destinationType);
}
