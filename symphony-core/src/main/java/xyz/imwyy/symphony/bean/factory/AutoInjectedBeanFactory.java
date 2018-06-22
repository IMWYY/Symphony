package xyz.imwyy.symphony.bean.factory;

import xyz.imwyy.symphony.bean.BeanDefinition;
import xyz.imwyy.symphony.bean.BeanReference;
import xyz.imwyy.symphony.bean.PropertyValue;
import xyz.imwyy.symphony.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by stephen on 2018/6/21
 */
public class AutoInjectedBeanFactory implements BeanFactory {

    private Map<String, BeanDefinition> beanRegistry = new ConcurrentHashMap<>();

    @Override
    public void putBean(String name, BeanDefinition beanDefinition) {
        beanRegistry.put(name, beanDefinition);
    }

    @Override
    public void preInstantiateSingletons() throws Exception {
        for (Map.Entry<String, BeanDefinition> entry : beanRegistry.entrySet()) {
            getBean(entry.getKey());
        }
    }

    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanRegistry.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            bean = createBeanInstance(beanDefinition);
            bean = initBeanWithAop(bean, beanDefinition);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    /**
     * 创建bean实例
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        Object object = ReflectionUtil.newInstance(beanDefinition.getBeanClass());
        injectBeanFields(object, beanDefinition);
        beanDefinition.setBean(object);
        return object;
    }

    private Object initBeanWithAop(Object bean, BeanDefinition beanDefinition) {
        return bean;
    }


    /**
     * 注入bean实例的属性
     */
    private void injectBeanFields(Object object, BeanDefinition beanDefinition) throws Exception {
        for (PropertyValue propertyValue : beanDefinition.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getId());
            }

            try {
                Method declaredMethod = object.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                ReflectionUtil.invokeMethod(object, declaredMethod, value);
            } catch (NoSuchMethodException e) {
                Field declaredField = object.getClass().getDeclaredField(propertyValue.getName());
                ReflectionUtil.setField(object, declaredField, value);
            }
        }
    }
}
