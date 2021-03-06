package xyz.imwyy.symphony.context;

import xyz.imwyy.symphony.bean.factory.AutoInjectedBeanFactory;
import xyz.imwyy.symphony.bean.factory.BeanFactory;
import xyz.imwyy.symphony.bean.reader.AnnotationDrivenBeanReader;
import xyz.imwyy.symphony.bean.reader.XmlDrivenBeanReader;

/**
 * create by stephen on 2018/6/22
 */
public class XmlDrivenSymphonyContext extends AbstractSymphonyContext {

    private String configLocation;

    public XmlDrivenSymphonyContext(String configLocation) throws Exception {
        super(new AutoInjectedBeanFactory());
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(BeanFactory beanFactory) throws Exception {
        // 加载注解的bean
        AnnotationDrivenBeanReader annotationDrivenBeanReader = new AnnotationDrivenBeanReader(beanFactory);
        annotationDrivenBeanReader.loadBeanDefinitions();

        // 加载从xml中获取的bean
        XmlDrivenBeanReader xmlDrivenBeanReader = new XmlDrivenBeanReader(beanFactory, configLocation);
        xmlDrivenBeanReader.loadBeanDefinitions();
    }
}
