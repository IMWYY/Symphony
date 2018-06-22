package xyz.imwyy.symphony.aop;

import xyz.imwyy.symphony.aop.annotation.Aspect;
import xyz.imwyy.symphony.aop.proxy.AbstractProxy;
import xyz.imwyy.symphony.aop.proxy.Proxy;
import xyz.imwyy.symphony.aop.proxy.ProxyFactory;
import xyz.imwyy.symphony.bean.factory.BeanFFactory;
import xyz.imwyy.symphony.util.ClassFactory;
import xyz.imwyy.symphony.transaction.TransactionProxy;
import xyz.imwyy.symphony.transaction.TransactionalBean;
import xyz.imwyy.symphony.util.ReflectionUtil;

import java.util.*;

/**
 * 加载代理类
 * create by stephen on 2018/6/5
 */
public class AopLoader {

    public static void init() {
        try {
            Map<Class<?>, List<Proxy>> targetObjectMap = createProxyObjectMap();
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetObjectMap.entrySet()) {
                Class<?> cls = entry.getKey();
                List<Proxy> proxies = entry.getValue();
                Object proxyObject = ProxyFactory.createProxy(cls, proxies);
                // 装载进bean
                BeanFFactory.setBean(cls, proxyObject);
            }
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取被代理类和切面的map
     * key-被代理类 value-切面类的集合
     */
    @SuppressWarnings("all")
    private static Map<Class<?>, List<Proxy>> createProxyObjectMap() throws IllegalAccessException, InstantiationException {
        Map<Class<?>, List<Proxy>> resultMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassFactory.getClassSetBySuper(AbstractProxy.class);

        addTransactionProxy(resultMap);     // 将事务注解添加到map

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

    /**
     * 添加事务处理的注解类 依靠AOP实现
     * @param map
     */
    @SuppressWarnings("all")
    public static void addTransactionProxy(Map<Class<?>, List<Proxy>> map) {
        Set<Class<?>> proxyClassSet = ClassFactory.getAnnotatedClassSet(TransactionalBean.class);
        Proxy proxy = (Proxy) ReflectionUtil.newInstance(TransactionProxy.class);

        for (Class<?> cls : proxyClassSet) {
            // 将所有的proxy实例化加入map
            if (map.containsKey(cls)) {
                if (!map.get(cls).contains(proxy)) {
                    map.get(cls).add(proxy);
                }
            } else {
                List<Proxy> list = new ArrayList<>();
                list.add(proxy);
                map.put(cls, list);
            }
        }
    }

}
