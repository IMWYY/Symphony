package xyz.imwyy.symphony.aop;

import xyz.imwyy.symphony.aop.advisor.*;
import xyz.imwyy.symphony.aop.annotation.After;
import xyz.imwyy.symphony.aop.annotation.Around;
import xyz.imwyy.symphony.aop.annotation.Aspect;
import xyz.imwyy.symphony.aop.annotation.Before;
import xyz.imwyy.symphony.util.ClassFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * create by stephen on 2018/6/22
 */
public class AopContext {

    private static List<Advisor> advisors = new ArrayList<>();

    static  {
        Set<Class<?>> aspectClasses = ClassFactory.getAnnotatedClassSet(Aspect.class);
        // 遍历所有的aspectClasses
        for (Class<?> clazz : aspectClasses) {
            Method[] methods = clazz.getDeclaredMethods();
            // 检查所有的方法
            for (Method method : methods) {
                String classExpression = null, methodExpression = null;
                CutType cutType = CutType.BEFORE;
                if (method.isAnnotationPresent(Before.class)){
                    Before beforeAdvice = method.getAnnotation(Before.class);
                    classExpression = beforeAdvice.classExpression();
                    methodExpression = beforeAdvice.methodExpression();
                    cutType = CutType.BEFORE;
                } else if (method.isAnnotationPresent(After.class)){
                    After advice = method.getAnnotation(After.class);
                    classExpression = advice.classExpression();
                    methodExpression = advice.methodExpression();
                    cutType = CutType.AFTER;
                } else if (method.isAnnotationPresent(Around.class)){
                    Around advice = method.getAnnotation(Around.class);
                    classExpression = advice.classExpression();
                    methodExpression = advice.methodExpression();
                    cutType = CutType.AROUND;
                }

                if (null != classExpression) {
                    PointCut pointCut = new ExpressionPointCut(classExpression, methodExpression, cutType);
                    Advice advice = new ExpressionAdvice(clazz, method);
                    Advisor advisor = new BaseAdvisor(pointCut, advice);
                    advisors.add(advisor);
                }
            }
        }
    }

    public static List<Advisor> getAdvisors() {
        return advisors;
    }
}
