package xyz.imwyy.symphony.aop.advisor;

import xyz.imwyy.symphony.transaction.TransactionalBean;
import xyz.imwyy.symphony.transaction.TransactionalMethod;

import java.lang.reflect.Method;

/**
 * 基于注解的事务pointcut
 * create by stephen on 2018/6/23
 */
public class TransactionPointCut implements PointCut {

    @Override
    public boolean matchClass(Class targetClass) {
        return targetClass.isAnnotationPresent(TransactionalBean.class);
    }

    @Override
    public boolean matchMethod(Method method, Class targetClass) {
        return method.isAnnotationPresent(TransactionalMethod.class);
    }

    @Override
    public CutType getCutType() {
        return CutType.AROUND;
    }
}
