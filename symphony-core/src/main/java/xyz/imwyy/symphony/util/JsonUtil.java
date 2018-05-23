package xyz.imwyy.symphony.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json转换的工具类
 * create by stephen on 2018/5/20
 */
public class JsonUtil {

//    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
//            LOGGER.error("fail to convert Object to json", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        T ojb;
        try {
            ojb = OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
//            LOGGER.error("fail to convert json to Object", e);
            throw new RuntimeException(e);
        }
        return ojb;
    }

}
