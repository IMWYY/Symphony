package xyz.imwyy.symphony.context;

import xyz.imwyy.symphony.bean.factory.BeanFactory;

/**
 * create by stephen on 2018/6/21
 */
public abstract class AbstractSymphonyContext implements SymphonyContext {

    private BeanFactory beanFactory;

    public AbstractSymphonyContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void refresh() throws Exception {
        loadBeanDefinitions(beanFactory);
        onRefresh();
    }

    /**
     * 将bean定义装在进beanfactory 具体如何装载需要实现
     */
    protected abstract void loadBeanDefinitions(BeanFactory beanFactory) throws Exception;

    /**
     * 初始化所有的bean实例
     */
    private void onRefresh() throws Exception {
        beanFactory.preInstantiateSingletons();
    }

    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }


}
