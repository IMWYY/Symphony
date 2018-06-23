package xyz.imwyy.symphony.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 前置增强
 * create by stephen on 2018/6/22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
    /**
     * 被代理的目标类的表达式 *可以匹配任意
     */
    String classExpression() default "";

    /**
     * 被代理的目标方法的表达式 默认为类的全部方法
     */
    String methodExpression() default "*";
}

