package xyz.imwyy.symphony.ioc.factory;

import xyz.imwyy.symphony.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * bean的助手类
 * create by stephen on 2018/5/19
 */
public class BeanFactory {

    /**
     * class到object实例的映射 利用ReflectionUtil加载
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    public static void init() {
        Set<Class<?>> classSet = ClassFactory.getBeanClassSet();
        for (Class<?> clazz: classSet) {
            // 这里所有的对象全部是单例模式 只初始化一个对象
            Object object = ReflectionUtil.newInstance(clazz);
            BEAN_MAP.put(clazz, object);
        }
    }


    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取指定类的实例对象
     * @param cls 类
     * @param <T> 实例对象
     * @return 实例对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class " + cls);
        }
        return (T)BEAN_MAP.get(cls);
    }

    public static void setBean(Class<?> key, Object value) {
        BEAN_MAP.put(key, value);
    }
}
