package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * create by stephen on 2018/6/4
 */
public class CglibDynamicProxy implements MethodInterceptor {

    private static CglibDynamicProxy cglibDynamicProxy;

    public static synchronized CglibDynamicProxy  getInstance() {
        return cglibDynamicProxy == null ?
                cglibDynamicProxy = new CglibDynamicProxy()
                : cglibDynamicProxy;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }


    public void before() {
        System.out.println();
        System.out.println("Cglib Dynamic Proxy - before ");
    }

    public void after() {
        System.out.println("Cglib Dynamic Proxy - after ");
        System.out.println();
    }
}
