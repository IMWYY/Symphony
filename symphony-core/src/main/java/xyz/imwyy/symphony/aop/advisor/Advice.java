package xyz.imwyy.symphony.aop.advisor;

/**
 * create by stephen on 2018/6/22
 */
public interface Advice {

    /**
     * 调用切面逻辑的方法
     */
    void invoke(JoinPoint joinPoint);
}
