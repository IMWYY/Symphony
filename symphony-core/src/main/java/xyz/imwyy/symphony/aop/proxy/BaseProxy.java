package xyz.imwyy.symphony.aop.proxy;

import java.lang.reflect.Method;

/**
 * 代理类的抽象类 所有的切面代理必须继承该类
 * create by stephen on 2018/6/5
 */
public abstract class BaseProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getMethod();
        Object[] param = proxyChain.getArgs();

        begin(cls, method, param);
        try {
            result = proxyChain.doProxyChain();
            after(cls, method, param, result);
        } catch (Throwable throwable) {
            error(cls, method, param, throwable);
            throwable.printStackTrace();
        } finally {
            end();
        }

        return null;
    }

    /**
     * 前置增强
     */
    public abstract void begin(Class<?> cls, Method method, Object[] param);

    /**
     * 错误处理
     */
    public abstract void error(Class<?> cls, Method method, Object[] param, Throwable throwable);

    /**
     * 后置增强
     */
    public abstract void after(Class<?> cls, Method method, Object[] param, Object result);

    /**
     * finally 执行的代码块
     */
    public abstract void end();

}
