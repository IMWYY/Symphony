package xyz.imwyy.symphony.aop.proxy;

import xyz.imwyy.symphony.aop.AopContext;
import xyz.imwyy.symphony.aop.BeanInitProcessor;
import xyz.imwyy.symphony.aop.advisor.Advisor;
import xyz.imwyy.symphony.aop.advisor.BaseAdvisor;
import xyz.imwyy.symphony.aop.advisor.TransactionPointCut;
import xyz.imwyy.symphony.aop.proxy.CglibAopProxy;
import xyz.imwyy.symphony.bean.BeanFactoryAware;
import xyz.imwyy.symphony.bean.factory.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动完成aop代理的默认实现类 需要手动配置该类加载到bean factory中
 * create by stephen on 2018/6/22
 */
public class AutoProxyCreator implements BeanFactoryAware, BeanInitProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object processBeforeInitialization(Object bean, Class clz) throws Exception {
        return bean;
    }

    @Override
    public Object processAfterInitialization(Object bean, Class clz) throws Exception {
        List<Advisor> advisors = AopContext.getAdvisors();

        List<Advisor> classFiltered = new ArrayList<>();

        for (Advisor advisor : advisors) {
            if (advisor.matchClass(clz)) {
                classFiltered.add(advisor);
            }
        }

        // 添加事务处理
        Advisor transactionAdvisor = new BaseAdvisor(new TransactionPointCut(), null);
        if (transactionAdvisor.matchClass(clz)) {
            return new CglibAopProxy(transactionAdvisor, classFiltered, clz).getProxy();
        } else if (classFiltered.size() > 0){
            return new CglibAopProxy(classFiltered, clz).getProxy();
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
