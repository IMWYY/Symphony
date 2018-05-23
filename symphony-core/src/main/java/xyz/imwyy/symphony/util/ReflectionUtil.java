package xyz.imwyy.symphony.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 利用反射机制加载类的工具类
 * create by stephen on 2018/5/19
 */
public class ReflectionUtil {

//    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建类的实例对象
     * @param cls 类
     * @return 实例对象
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
//            LOGGER.error("fail to new instance", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用类的方法
     * @param object 实例对象
     * @param method 方法
     * @param args 参数
     */
    public static Object invokeMethod(Object object, Method method, Object... args) {
        Object result;
        method.setAccessible(true);
        try {
            result = method.invoke(object, args);
        } catch (Exception e) {
//            LOGGER.error("fail to invoke method", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置实例对象的属性
     * @param object 实例对象
     * @param field 属性
     * @param value 属性值
     */
    public static void setField(Object object, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
//            LOGGER.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
}