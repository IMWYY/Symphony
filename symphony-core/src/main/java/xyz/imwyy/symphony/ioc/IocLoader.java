package xyz.imwyy.symphony.ioc;

import xyz.imwyy.symphony.ioc.annotation.Inject;
import xyz.imwyy.symphony.ioc.factory.BeanFactory;
import xyz.imwyy.symphony.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 实现inject属性的注入
 * create by stephen on 2018/5/19
 */
public class IocLoader {

    /**
     *  遍历所有的类 找到带有inject注解的属性 并且初始化
     */
    public static void init() {
        Map<Class<?>, Object> beanMap = BeanFactory.getBeanMap();
        if (beanMap != null && beanMap.size() > 0) {
            // 遍历所有的类
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                Class<?> beanClass = entry.getKey();
                Object instance = entry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (beanFields != null && beanFields.length > 0) {
                    // 找到带有inject注解的属性 并且初始化
                    for (Field field : beanFields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Object beadFieldInstance = beanMap.get(field.getType());
                            if (beadFieldInstance != null) {
                                ReflectionUtil.setField(instance, field, beadFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
