package xyz.imwyy.symphony.helper;

import xyz.imwyy.symphony.annotation.Controller;
import xyz.imwyy.symphony.annotation.Service;
import xyz.imwyy.symphony.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作的助手类 获取被指定注解的类的结合
 * create by stephen on 2018/5/19
 */
public class ClassHelper {

    private static Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取所有service类集合
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Service.class)) {
                result.add(clazz);
            }
        }
        return result;
    }

    /**
     * 获取所有controller类集合
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                result.add(clazz);
            }
        }
        return result;
    }

    /**
     * 获取所有controller和service类集合
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)) {
                result.add(clazz);
            }
        }
        return result;
    }


}
