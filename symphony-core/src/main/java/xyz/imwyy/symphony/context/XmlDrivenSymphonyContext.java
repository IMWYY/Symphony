package xyz.imwyy.symphony.context;

import xyz.imwyy.symphony.bean.factory.BeanFactory;
import xyz.imwyy.symphony.bean.reader.XmlDrivenBeanReader;

/**
 * create by stephen on 2018/6/22
 */
public class XmlDrivenSymphonyContext extends AbstractSymphonyContext {

    private String configLocation;

    public XmlDrivenSymphonyContext(String configLocation, BeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        refresh();
    }

    @Override
    protected void loadBeanDefinitions(BeanFactory beanFactory) throws Exception {
        XmlDrivenBeanReader xmlDrivenBeanReader = new XmlDrivenBeanReader(beanFactory, configLocation);
        xmlDrivenBeanReader.loadBeanDefinitions();
    }
}
