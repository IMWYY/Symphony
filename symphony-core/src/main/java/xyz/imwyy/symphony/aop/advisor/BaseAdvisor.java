package xyz.imwyy.symphony.aop.advisor;

import java.lang.reflect.Method;

/**
 * create by stephen on 2018/6/22
 */
public class BaseAdvisor implements Advisor {

    private PointCut pointCut;

    private Advice advice;

    public BaseAdvisor(PointCut pointCut, Advice advice) {
        this.pointCut = pointCut;
        this.advice = advice;
    }

    @Override
    public PointCut getPointCut() {
        return pointCut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public boolean matchClass(Class targetClass) {
        return pointCut.matchClass(targetClass);
    }

    @Override
    public boolean matchMethod(Method method, Class targetClass) {
        return pointCut.matchMethod(method, targetClass);
    }

    @Override
    public CutType getCutType() {
        return pointCut.getCutType();
    }
}
