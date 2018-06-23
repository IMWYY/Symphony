package proxy;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        IInterfaceImpl dynamicProxy = CglibDynamicProxy.getInstance().getProxy(IInterfaceImpl.class);
        dynamicProxy.test();

        Field[] beanFields = dynamicProxy.getClass().getDeclaredFields();
        for (Field field : beanFields) {
            System.out.println(field.getName() + " -- " + field.getType());
        }
    }
}
