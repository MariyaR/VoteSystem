package ru.javawebinar.votesystem.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Long>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Long> menu) {

        String JsonMenu = null;
        try {
            JsonMenu = objectMapper.writeValueAsString(menu);
        } catch (final JsonProcessingException e) {
            //logger.error("JSON writing error", e);
        }

        return JsonMenu;
    }

    @Override
    public Map<String, Long> convertToEntityAttribute(String JsonMenu) {

        Map<String, Long> menu = null;
        TypeReference<HashMap<String, Long>> typeRef
                = new TypeReference<HashMap<String, Long>>() {};
        try {
            menu = objectMapper.readValue(JsonMenu, typeRef);
        } catch (final IOException e) {
            //logger.error("JSON reading error", e);
        }

        return menu;
    }
}
