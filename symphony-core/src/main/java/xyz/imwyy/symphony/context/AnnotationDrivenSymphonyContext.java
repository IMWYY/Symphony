package xyz.imwyy.symphony.context;

import xyz.imwyy.symphony.bean.factory.AutoInjectedBeanFactory;
import xyz.imwyy.symphony.bean.factory.BeanFactory;
import xyz.imwyy.symphony.bean.reader.AnnotationDrivenBeanReader;

/**
 * create by stephen on 2018/6/22
 */
public class AnnotationDrivenSymphonyContext extends AbstractSymphonyContext {

    public AnnotationDrivenSymphonyContext(){
        super(new AutoInjectedBeanFactory());
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadBeanDefinitions(BeanFactory beanFactory) throws Exception {
        // 加载注解的bean
        AnnotationDrivenBeanReader annotationDrivenBeanReader = new AnnotationDrivenBeanReader(beanFactory);
        annotationDrivenBeanReader.loadBeanDefinitions();
    }
}

