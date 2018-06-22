package xyz.imwyy.symphony.aop.proxy;

import net.sf.cglib.proxy.Enhancer;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import xyz.imwyy.symphony.aop.proxy.Proxy;
import xyz.imwyy.symphony.aop.proxy.ProxyChain;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理类生成的工厂类
 * create by stephen on 2018/6/5
 */
public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static  <T> T createProxy(Class<T> cls, List<Proxy> proxyList) {
        return (T) Enhancer.create(cls, new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return new ProxyChain(cls, obj, method, args, proxy, proxyList);
            }
        });
    }
}
