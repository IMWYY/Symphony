package xyz.imwyy.symphony.bean;

import xyz.imwyy.symphony.bean.factory.BeanFactory;

/**
 * create by stephen on 2018/6/22
 */
public interface BeanFactoryAware {

    public void setBeanFactory(BeanFactory beanFactory);
}
