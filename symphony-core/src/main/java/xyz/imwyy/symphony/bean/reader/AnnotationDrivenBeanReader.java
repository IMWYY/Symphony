package xyz.imwyy.symphony.bean.reader;

import xyz.imwyy.symphony.bean.BeanDefinition;
import xyz.imwyy.symphony.bean.BeanReference;
import xyz.imwyy.symphony.bean.PropertyValue;
import xyz.imwyy.symphony.bean.annotation.Inject;
import xyz.imwyy.symphony.bean.factory.BeanFactory;
import xyz.imwyy.symphony.bean.factory.ClassFactory;
import xyz.imwyy.symphony.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * create by stephen on 2018/6/22
 */
public class AnnotationDrivenBeanReader extends AbstractBeanDefinitionReader{

    public AnnotationDrivenBeanReader(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void loadBeanDefinitions() {
        Set<Class<?>> classSet = ClassFactory.getBeanClassSet();
        for (Class<?> clazz: classSet) {
            BeanDefinition definition = new BeanDefinition();
            definition.setBeanClass(clazz);
            definition.setBeanClassName(clazz.getName());

            processBeanProperty(definition);

            getBeanFactory().putBean(ReflectionUtil.getClassId(clazz), definition);
        }
    }

    /**
     * 找到带有inject注解的属性
     */
    private void processBeanProperty(BeanDefinition definition) {
        Class<?> beanClass = definition.getBeanClass();
        Field[] beanFields = beanClass.getDeclaredFields();
        if (beanFields != null && beanFields.length > 0) {
            for (Field field : beanFields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Class<?> fieldClass = field.getType();
                    String id = ReflectionUtil.getClassId(fieldClass);
                    BeanReference reference = new BeanReference(id);
                    definition.addPropertyValue(new PropertyValue(field.getName(), reference));
                }
            }
        }
    }
}
