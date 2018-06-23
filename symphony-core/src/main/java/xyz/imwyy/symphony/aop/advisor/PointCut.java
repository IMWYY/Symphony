package xyz.imwyy.symphony.aop.advisor;


import java.lang.reflect.Method;

/**
 * 切入点
 * create by stephen on 2018/6/22
 */
public interface PointCut {

    boolean matchClass(Class targetClass);

    boolean matchMethod(Method method, Class targetClass);

    CutType getCutType();
}
