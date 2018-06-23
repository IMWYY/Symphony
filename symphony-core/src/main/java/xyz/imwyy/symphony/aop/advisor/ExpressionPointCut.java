package xyz.imwyy.symphony.aop.advisor;

import java.lang.reflect.Method;

/**
 * create by stephen on 2018/6/22
 */
public class ExpressionPointCut implements PointCut {

    private String classExpression;

    private String methodExpression;

    private CutType cutType;

    public ExpressionPointCut(String classExpression, String methodExpression, CutType cutType) {
        this.classExpression = classExpression;
        this.methodExpression = methodExpression;
        this.cutType = cutType;
    }

    @Override
    public CutType getCutType() {
        return cutType;
    }

    @Override
    public boolean matchClass(Class targetClass) {
        // TODO: 2018/6/22 增强对类是否符合的判断 目前仅支持*的任意匹配
        String handledExpression = classExpression.replace("*", "");
        return targetClass.getName().startsWith(handledExpression);
    }

    @Override
    public boolean matchMethod(Method method, Class targetClass) {
        // TODO: 2018/6/22 增强对方法是否符合的判断
        if ("*".equals(methodExpression)) return true;
        return method.getName().equals(methodExpression);
    }
}
