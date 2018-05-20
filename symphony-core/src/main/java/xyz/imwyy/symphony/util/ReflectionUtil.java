package xyz.imwyy.symphony.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 利用反射机制加载类
 * create by stephen on 2018/5/19
 */
public class ReflectionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("fail to new instance", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    public static Object invokeMethod(Object object, Method method, Object... args) {
        Object result;
        method.setAccessible(true);
        try {
            result = method.invoke(object, args);
        } catch (Exception e) {
            LOGGER.error("fail to invoke method", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static void setField(Object object, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
}