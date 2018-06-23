package xyz.imwyy.symphony.aop.advisor;

import java.lang.reflect.Method;

/**
 * create by stephen on 2018/6/22
 */
public class JoinPoint {
    private Object targetObject;

    private Method method;

    private Object[] args;

    public JoinPoint(Object targetObject, Method method, Object[] args) {
        this.targetObject = targetObject;
        this.method = method;
        this.args = args;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }
}
