package xyz.imwyy.symphony;

import xyz.imwyy.symphony.aop.AopLoader;
import xyz.imwyy.symphony.mvc.MvcContext;

/**
 * 框架类的初始化加载类
 * create by stephen on 2018/5/19
 */
public class SymphonyContext {

    /**
     * 初始化类的实例 调用静态块实现类的加载
     */
    public static void init() {
        AopLoader.init();
        MvcContext.init();
    }
}
