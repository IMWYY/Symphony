package xyz.imwyy.symphony.aop;

import xyz.imwyy.symphony.aop.annotation.Aspect;
import xyz.imwyy.symphony.aop.proxy.AbstractProxy;
import xyz.imwyy.symphony.aop.proxy.Proxy;
import xyz.imwyy.symphony.aop.proxy.ProxyFactory;
import xyz.imwyy.symphony.bean.BeanFactoryAware;
import xyz.imwyy.symphony.bean.factory.BeanFactory;
import xyz.imwyy.symphony.transaction.TransactionProxy;
import xyz.imwyy.symphony.transaction.TransactionalBean;
import xyz.imwyy.symphony.util.ReflectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动完成aop代理的默认实现类 需要手动配置该类加载到bean factory中
 * create by stephen on 2018/6/22
 */
public class AutoProxyCreator implements BeanFactoryAware, BeanInitProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object processBeforeInitialization(Object bean, Class clz) throws Exception {
        return bean;
    }

    @Override
    public Object processAfterInitialization(Object bean, Class clz) throws Exception {
        List<Object> allProxyInstance = beanFactory.getBeanBySuper(AbstractProxy.class);
        List<Proxy> proxies = new ArrayList<>();

        // 处理事务的注解
        if (clz.isAnnotationPresent(TransactionalBean.class)){
            proxies.add((Proxy) ReflectionUtil.newInstance(TransactionProxy.class));
        }

        for (Object o: allProxyInstance) {
            if (o.getClass().isAnnotationPresent(Aspect.class)) {
                Aspect annotation = o.getClass().getAnnotation(Aspect.class);
                Class<?> targetClass = annotation.targetClass();
                String targetPackage = annotation.targetPackage();

                // 被代理类与当前类相同 或者在该代理的包内
                if (targetClass.equals(clz) ||
                        (!targetPackage.isEmpty() && clz.getName().startsWith(targetPackage) )) {
                    proxies.add((Proxy) o);
                }
            }
        }

        if (proxies.size() > 0) {
            return ProxyFactory.createProxy(clz, proxies);
        } else {
            return bean;
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
