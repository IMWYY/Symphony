package xyz.imwyy.symphony.bean.factory;

import xyz.imwyy.symphony.ConfigContext;
import xyz.imwyy.symphony.bean.annotation.Component;
import xyz.imwyy.symphony.mvc.annotation.Controller;
import xyz.imwyy.symphony.bean.annotation.Service;
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

    static  {
        String basePackage = ConfigContext.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取所有被指定注解描述的类集合
     */
    public static Set<Class<?>> getAnnotatedClassSet(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                result.add(clazz);
            }
        }
        return result;
    }

    /**
     * 获取所有被注解的bean的集合，包括了controller service component
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> result = new HashSet<>();
        for (Class<?> clazz : CLASS_SET) {
            if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)
                    || clazz.isAnnotationPresent(Component.class)) {
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
}
