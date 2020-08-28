package ru.javawebinar.votesystem.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<Long, String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<Long, String> menu) {

        String JsonMenu = null;
        try {
            JsonMenu = objectMapper.writeValueAsString(menu);
        } catch (final JsonProcessingException e) {
            //logger.error("JSON writing error", e);
        }

        return JsonMenu;
    }

    @Override
    public Map<Long, String> convertToEntityAttribute(String JsonMenu) {

        Map<Long, String> menu = null;
        TypeReference<HashMap<Long, String>> typeRef
                = new TypeReference<HashMap<Long, String>>() {};
        try {
            menu = objectMapper.readValue(JsonMenu, typeRef);
        } catch (final IOException e) {
            //logger.error("JSON reading error", e);
        }

        return menu;
    }

}
