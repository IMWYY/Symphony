package xyz.imwyy.symphony.context;

import xyz.imwyy.symphony.aop.BeanInitProcessor;
import xyz.imwyy.symphony.bean.factory.BeanFactory;
import xyz.imwyy.symphony.mvc.MvcContext;
import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.handler.Handler;

import java.util.List;

/**
 * create by stephen on 2018/6/21
 */
public abstract class AbstractSymphonyContext implements SymphonyContext {

    private BeanFactory beanFactory;
    private MvcContext mvcContext;

    public AbstractSymphonyContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        initMvcContext();
    }

    private void initMvcContext() {
        mvcContext = new MvcContext();
    }

    public void refresh() throws Exception {
        loadBeanDefinitions(beanFactory);
        registerBeanInitProcessor();
        onRefresh();
    }

    /**
     * 在初始化其他的bean之前首先初始化 bean processor
     * 这样才能实现bean的aop
     */
    private void registerBeanInitProcessor() throws Exception {
        List<Object> beans = beanFactory.getBeanBySuper(BeanInitProcessor.class);
        for (Object o : beans) {
            beanFactory.addBeanInitProcessor((BeanInitProcessor)o);
        }
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

    @Override
    public Handler getHandler(String reqPath, RequestType requestType) {
        return mvcContext.getHandler(reqPath, requestType);
    }
}
