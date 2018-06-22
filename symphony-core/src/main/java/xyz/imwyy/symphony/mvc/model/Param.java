package xyz.imwyy.symphony.mvc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求的参数键值对的实体类
 * create by stephen on 2018/5/19
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param() {
        this.paramMap = new HashMap<>();
    }

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Object get(String name) {
        if (paramMap == null) throw new RuntimeException("paramMap is not initialized");
        return paramMap.get(name);
    }

    public void put(String key, String object) {
        this.paramMap.put(key, object);
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public boolean isEmpty() {
        return paramMap == null || paramMap.size() == 0;
    }
}
