package xyz.imwyy.symphony;

import xyz.imwyy.symphony.helper.*;
import xyz.imwyy.symphony.util.ClassUtil;

/**
 * 框架类的初始化加载类
 * create by stephen on 2018/5/19
 */
public class SymphonyLoader {

    /**
     * 初始化类的实例 调用静态块实现类的加载
     */
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanFactory.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
