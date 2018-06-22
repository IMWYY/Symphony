package xyz.imwyy.symphony.bean.factory;

import xyz.imwyy.symphony.bean.BeanDefinition;

public interface BeanFactory {

    public void putBean(String name, BeanDefinition beanDefinition);

    public Object getBean(String name) throws Exception;

    public void preInstantiateSingletons() throws Exception;


}
