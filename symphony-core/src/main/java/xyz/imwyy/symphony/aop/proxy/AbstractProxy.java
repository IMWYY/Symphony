package xyz.imwyy.symphony.aop.proxy;

import java.lang.reflect.Method;

/**
 * 代理类的抽象类 所有的切面代理必须继承该类
 * create by stephen on 2018/6/5
 */
@Deprecated
public abstract class AbstractProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getMethod();
        Object[] param = proxyChain.getArgs();

        if (intercept(cls, method, param)) {
            begin(cls, method, param);
            try {
                result = proxyChain.doProxyChain();
                after(cls, method, param, result);
            } catch (Throwable throwable) {
                error(cls, method, param, throwable);
                throw throwable;
            } finally {
                end();
            }
        } else {
            result = proxyChain.doProxyChain();
        }

        return result;
    }

    /**
     * 判断是否需要拦截方法
     */
    protected abstract boolean intercept(Class<?> cls, Method method, Object[] param);

    /**
     * 前置增强
     */
    protected abstract void begin(Class<?> cls, Method method, Object[] param);

    /**
     * 错误处理
     */
    protected abstract void error(Class<?> cls, Method method, Object[] param, Throwable throwable);

    /**
     * 后置增强
     */
    protected abstract void after(Class<?> cls, Method method, Object[] param, Object result);

    /**
     * finally 执行的代码块
     */
    protected abstract void end();

}
