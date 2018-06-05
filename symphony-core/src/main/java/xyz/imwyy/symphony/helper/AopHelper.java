package xyz.imwyy.symphony.helper;

import xyz.imwyy.symphony.aop.Aspect;
import xyz.imwyy.symphony.aop.BaseProxy;
import xyz.imwyy.symphony.aop.Proxy;
import xyz.imwyy.symphony.aop.ProxyFactory;

import java.util.*;

/**
 * 加载代理类
 * create by stephen on 2018/6/5
 */
public class AopHelper {

    static {
        try {
            Map<Class<?>, List<Proxy>> targetObjectMap = createProxyObjectMap();
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetObjectMap.entrySet()) {
                Class<?> cls = entry.getKey();
                List<Proxy> proxies = entry.getValue();
                Object proxyObject = ProxyFactory.createProxy(cls, proxies);
                // 装载进bean
                BeanFactory.setBean(cls, proxyObject);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取被代理类和切面的map
     * key-被代理类 value-切面类的集合
     */
    private static Map<Class<?>, List<Proxy>> createProxyObjectMap() throws IllegalAccessException, InstantiationException {
        Map<Class<?>, List<Proxy>> resultMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(BaseProxy.class);

        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect annotation = proxyClass.getAnnotation(Aspect.class);
                Class<?> targetClass = annotation.value();
                Proxy proxy = (Proxy) proxyClass.newInstance();
                // 将所有的proxy实例化加入map
                if (resultMap.containsKey(targetClass)) {
                    if (!resultMap.get(targetClass).contains(proxy)) {
                        resultMap.get(targetClass).add(proxy);
                    }
                } else {
                    List<Proxy> list = new ArrayList<>();
                    list.add(proxy);
                    resultMap.put(targetClass, list);
                }
            }
        }

        return resultMap;
    }
}
