package xyz.imwyy.symphony.helper;

import xyz.imwyy.symphony.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * create by stephen on 2018/5/19
 */
public class BeanHelper {

    /**
     * class到object实例的映射 利用ReflectionUtil加载
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz: classSet) {
            Object object = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, object);
        }
    }


    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class " + cls);
        }
        return (T)BEAN_MAP.get(cls);
    }
}
