package xyz.imwyy.symphony.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import xyz.imwyy.symphony.aop.advisor.Advisor;
import xyz.imwyy.symphony.aop.advisor.CutType;
import xyz.imwyy.symphony.aop.advisor.JoinPoint;
import xyz.imwyy.symphony.util.DbUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 使用cglib生成代理类
 * create by stephen on 2018/6/22
 */
public class CglibAopProxy implements AopProxy, MethodInterceptor {

    private List<Advisor> classFilteredAdvisors;

    private Class targetClass;

    private Advisor transactionAdvisor;

    public CglibAopProxy(Advisor transactionAdvisor, List<Advisor> classFilteredAdvisors, Class targetClass) {
        this.transactionAdvisor = transactionAdvisor;
        this.classFilteredAdvisors = classFilteredAdvisors;
        this.targetClass = targetClass;
    }

    public CglibAopProxy(List<Advisor> classFiltered, Class clz) {
        this(null, classFiltered, clz);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        JoinPoint joinPoint = new JoinPoint(obj, method, args);

        Object result;

        // 如果该方法被事务注解 处理事务
        if (transactionAdvisor != null && transactionAdvisor.matchMethod(method, targetClass)) {
            DbUtil.beginTransaction();
            try {
                result = enhancedMethod(obj, method, args, proxy, joinPoint);
                DbUtil.commitTransaction();
            } catch (Throwable throwable) {
                DbUtil.rollBackTransaction();
                throw throwable;
            }
        } else {
            result = enhancedMethod(obj, method, args, proxy, joinPoint);
        }

        return result;
    }

    private Object enhancedMethod(Object obj, Method method, Object[] args, MethodProxy proxy, JoinPoint joinPoint) throws Throwable {
        // 前置增强
        for (Advisor advisor : classFilteredAdvisors) {
            if ((advisor.getCutType().equals(CutType.BEFORE) || advisor.getCutType().equals(CutType.AROUND))
                    && advisor.getPointCut().matchMethod(method, targetClass)) {
                advisor.getAdvice().invoke(joinPoint);
            }
        }

        Object result = proxy.invokeSuper(obj, args);

        // 后置增强
        for (Advisor advisor : classFilteredAdvisors) {
            if ((advisor.getCutType().equals(CutType.AFTER) || advisor.getCutType().equals(CutType.AROUND))
                    && advisor.getPointCut().matchMethod(method, targetClass)) {
                advisor.getAdvice().invoke(joinPoint);
            }
        }
        return result;
    }

    @Override
    public Object getProxy() {
        return Enhancer.create(targetClass, this);
    }
}
