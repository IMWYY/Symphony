package xyz.imwyy.symphony.mvc.web;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回的视图对象
 * create by stephen on 2018/5/20
 */
public class View {

    /**
     * 视图的路径
     */
    private String path;

    /**
     * 传递的参数键值对
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<>();
    }

    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
