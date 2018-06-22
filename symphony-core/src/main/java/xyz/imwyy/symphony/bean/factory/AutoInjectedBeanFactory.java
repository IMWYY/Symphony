package xyz.imwyy.symphony.bean.factory;

import xyz.imwyy.symphony.aop.BeanInitProcessor;
import xyz.imwyy.symphony.bean.BeanDefinition;
import xyz.imwyy.symphony.bean.BeanReference;
import xyz.imwyy.symphony.bean.PropertyValue;
import xyz.imwyy.symphony.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by stephen on 2018/6/21
 */
public class AutoInjectedBeanFactory implements BeanFactory {

    private Map<String, BeanDefinition> beanRegistry = new ConcurrentHashMap<>();

    private List<BeanInitProcessor> beanProcessors = new ArrayList<>();

    /**
     * 初始化工厂时 将默认的代理处理类添加进bean factory
     */
    public AutoInjectedBeanFactory() {
        BeanDefinition definition = new BeanDefinition();
        definition.setBeanClass(BeanDefinition.class);
        definition.setBeanClassName(BeanDefinition.class.getName());
        this.beanRegistry.put("autoProxyCreator", definition);
    }

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

    @Override
    public void addBeanInitProcessor(BeanInitProcessor beanProcessor) {
        this.beanProcessors.add(beanProcessor);
    }

    @Override
    public List<Object> getBeanByType(Class cls) throws Exception {
        List<Object> result = new ArrayList<>();
        for (Map.Entry<String, BeanDefinition> entry: beanRegistry.entrySet()) {
            BeanDefinition beanDefinition = entry.getValue();
            if (cls.isAssignableFrom(beanDefinition.getBeanClass())) {
                result.add(getBean(entry.getKey()));
            }
        }
        return result;
    }

    /**
     * 创建bean实例
     */
    private Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        Object object = ReflectionUtil.newInstance(beanDefinition.getBeanClass());
        injectBeanFields(object, beanDefinition);
        beanDefinition.setBean(object);
        return object;
    }

    /**
     * 初始化bean 并利用BeanProcessor来处理每个实例
     */
    private Object initBeanWithAop(Object bean, BeanDefinition beanDefinition) throws Exception {
        for (BeanInitProcessor processor : beanProcessors) {
            bean = processor.processBeforeInitialization(bean, beanDefinition.getBeanClass());
        }
        for (BeanInitProcessor processor : beanProcessors) {
            bean = processor.processAfterInitialization(bean, beanDefinition.getBeanClass());
        }
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
