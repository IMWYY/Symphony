package xyz.imwyy.symphony.ioc.factory;

import xyz.imwyy.symphony.ConfigContext;
import xyz.imwyy.symphony.mvc.annotation.Controller;
import xyz.imwyy.symphony.ioc.annotation.Service;
import xyz.imwyy.symphony.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作的助手类 获取被指定注解的类的结合
 * create by stephen on 2018/5/19
 */
public class ClassFactory {

    private static Set<Class<?>> CLASS_SET;

    public static void init() {
        String basePackage = ConfigContext.getAppBasePackage();
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

    /**
     * 获取某个类的所有子类
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        Set<Class<?>> resultSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                resultSet.add(cls);
            }
        }
        return resultSet;
    }

    /**
     * 获取带有某个注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotataion) {
        Set<Class<?>> resultSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotataion)) {
                resultSet.add(cls);
            }
        }
        return resultSet;
    }

}
