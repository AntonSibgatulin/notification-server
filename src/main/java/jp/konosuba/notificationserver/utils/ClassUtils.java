package jp.konosuba.notificationserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClassUtils {
    public static <S> String fromObjectToJson(S s){
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
