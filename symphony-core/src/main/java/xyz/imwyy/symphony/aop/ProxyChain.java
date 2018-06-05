package xyz.imwyy.symphony.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 链式代理
 * create by stephen on 2018/6/5
 */
public class ProxyChain {
    private Class<?> targetClass;
    private Object targetObject;
    private Method method;
    private Object[] args;
    private MethodProxy methodProxy;

    private List<Proxy> proxyList = new ArrayList<>();
    private int proxyIndex;


    public ProxyChain(Class<?> targetClass,
                      Object targetObject,
                      Method method,
                      Object[] args,
                      MethodProxy methodProxy,
                      List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.method = method;
        this.args = args;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }

    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxyIndex < proxyList.size()) {
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObject, args);
        }
        return result;
    }

    public Class<?> getTargetClass() {
        return targetClass;
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
