package xyz.imwyy.symphony.aop.advisor;

import xyz.imwyy.symphony.util.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * create by stephen on 2018/6/22
 */
public class ExpressionAdvice implements Advice {

    private Class<?> aspectClass;
    private Object objectInstance;

    private Method aspectMethod;

    public ExpressionAdvice(Class<?> aspectClass, Method aspectMethod) {
        this.aspectClass = aspectClass;
        this.aspectMethod = aspectMethod;
        this.objectInstance = ReflectionUtil.newInstance(aspectClass);
    }

    @Override
    public void invoke(JoinPoint joinPoint) {
        //调用切面逻辑方法 目前仅支持无参数 或者 joinpoint参数
        Class<?>[] paramType = aspectMethod.getParameterTypes();
        if (paramType.length == 1) {
            ReflectionUtil.invokeMethod(objectInstance, aspectMethod, joinPoint);
        } else {
            ReflectionUtil.invokeMethod(objectInstance, aspectMethod);
        }
    }
}
