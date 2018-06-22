package xyz.imwyy.symphony.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面的注解 目前只支持在类基础上注解
 * create by stephen on 2018/6/5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 代理的目标类
     */
    Class<?> targetClass();

    /**
     * 代理的目标包 包内的所有类都被代理
     */
    String targetPackage() default "";


}
