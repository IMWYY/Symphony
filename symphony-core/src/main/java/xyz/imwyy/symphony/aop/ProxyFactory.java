package xyz.imwyy.symphony.aop;

import net.sf.cglib.proxy.Enhancer;

import net.sf.cglib.proxy.MethodInterceptor;
import java.util.List;

/**
 * 代理类生成的工厂类
 * create by stephen on 2018/6/5
 */
public class ProxyFactory {

    @SuppressWarnings("unchecked")
    public static  <T> T createProxy(Class<T> cls, List<Proxy> proxyList) {
        return (T) Enhancer.create(cls, (MethodInterceptor) (obj, method, args, proxy) ->
                new ProxyChain(cls, obj, method, args, proxy, proxyList));
    }
}
