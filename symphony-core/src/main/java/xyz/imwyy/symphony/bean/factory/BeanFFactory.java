package xyz.imwyy.symphony.bean.factory;


import java.util.HashMap;
import java.util.Map;

/**
 * bean的助手类
 * create by stephen on 2018/5/19
 */
public class BeanFFactory {

    /**
     * class到object实例的映射 利用ReflectionUtil加载
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();


    public static void setBean(Class<?> key, Object value) {
        BEAN_MAP.put(key, value);
    }
}
