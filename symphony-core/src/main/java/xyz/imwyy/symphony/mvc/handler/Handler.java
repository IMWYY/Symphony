package xyz.imwyy.symphony.mvc.handler;

import java.lang.reflect.Method;

/**
 * 请求的处理类，保存请求的controller类和请求调用的具体方法
 * create by stephen on 2018/5/19
 */
public class Handler {

    private Class<?> controllerClass;
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
