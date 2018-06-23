package xyz.imwyy.symphony.aop.advisor;


/**
 * 切面逻辑和切入点的整合配置接口
 */
public interface Advisor extends PointCut {

    PointCut getPointCut();

    Advice getAdvice();
}
