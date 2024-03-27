package com.ak.ratecompare.exchangerate.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        JsonUtil.applicationContext = applicationContext;
    }

    public static <T> T loadObjectFromJsonFile(String path, Class<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = applicationContext.getResource("classpath:" + path);
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, type);
        }
    }
    
    public static <T> List<T> loadListOfObjectsFromJson(String path, Class<T> type) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = applicationContext.getResource("classpath:" + path);
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        }
    }
}
