package xyz.imwyy.symphony.bean.factory;

import xyz.imwyy.symphony.aop.BeanInitProcessor;
import xyz.imwyy.symphony.bean.BeanDefinition;

import java.util.List;

public interface BeanFactory {

    public void putBean(String name, BeanDefinition beanDefinition);

    public Object getBean(String name) throws Exception;

    public void preInstantiateSingletons() throws Exception;

    public void addBeanInitProcessor(BeanInitProcessor beanProcessor);

    public List<Object> getBeanByType(Class cls) throws Exception;
}
