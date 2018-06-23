package xyz.imwyy.symphony.bean.reader;

import xyz.imwyy.symphony.bean.factory.BeanFactory;

/**
 * @see xyz.imwyy.symphony.bean.reader.BeanDefinitionReader 的模版类
 * create by stephen on 2018/6/21
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private BeanFactory beanFactory;

    public AbstractBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
