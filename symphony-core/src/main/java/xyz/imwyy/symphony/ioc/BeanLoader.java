package xyz.imwyy.symphony.ioc;

import xyz.imwyy.symphony.ioc.factory.BeanFactory;
import xyz.imwyy.symphony.ioc.factory.ClassFactory;

/**
 * create by stephen on 2018/6/6
 */
public class BeanLoader {

    /**
     * 初始化类的实例 调用静态块实现类的加载
     */
    public static void init() {
        ClassFactory.init();
        BeanFactory.init();
    }
}
