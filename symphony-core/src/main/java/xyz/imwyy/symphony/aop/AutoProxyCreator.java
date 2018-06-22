package xyz.imwyy.symphony.aop;

import xyz.imwyy.symphony.bean.BeanFactoryAware;
import xyz.imwyy.symphony.bean.factory.BeanFactory;

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
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
