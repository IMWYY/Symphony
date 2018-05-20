package xyz.imwyy.symphony.bean;

import java.util.Map;

/**
 * create by stephen on 2018/5/19
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Object get(String name) {
        if (paramMap == null) throw new RuntimeException("paramMap is not initialized");
        return paramMap.get(name);
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
