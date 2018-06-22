package xyz.imwyy.symphony.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * create by stephen on 2018/6/21
 */
public class BeanDefinition {

    private Class beanClass;

    private Object bean;

    private String beanClassName;

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            if (beanClass != null) {
                this.beanClass = Class.forName(beanClassName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValues .add(propertyValue);
    }
}
