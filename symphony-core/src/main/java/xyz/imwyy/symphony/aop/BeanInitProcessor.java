package xyz.imwyy.symphony.aop;

/**
 * 加载bean实现aop的接口 在初始化的时候分别调用这两个方法
 * create by stephen on 2018/6/22
 */
public interface BeanInitProcessor {

    public Object processBeforeInitialization(Object bean, Class clz) throws Exception;

    public Object processAfterInitialization(Object bean, Class clz) throws Exception;
}
