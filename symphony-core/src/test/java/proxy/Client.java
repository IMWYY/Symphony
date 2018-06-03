package proxy;

import org.junit.Test;

/**
 * create by stephen on 2018/6/3
 */
public class Client {

    @Test
    public void staticProxy() {
        IInterface staticProxy = new StaticProxy();
        staticProxy.test();
    }

    @Test
    public void JDKDynamicProxy() {
        IInterface dynamicProxy = new JDKDynamicProxy(new IInterfaceImpl()).getProxy();
        dynamicProxy.test();
    }

    @Test
    public void CglibDynamicProxy() {
        IInterface dynamicProxy = CglibDynamicProxy.getInstance().getProxy(IInterfaceImpl.class);
        dynamicProxy.test();
    }

}
