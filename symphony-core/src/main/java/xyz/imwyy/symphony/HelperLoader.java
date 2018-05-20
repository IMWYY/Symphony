package xyz.imwyy.symphony;

import xyz.imwyy.symphony.helper.BeanHelper;
import xyz.imwyy.symphony.helper.ClassHelper;
import xyz.imwyy.symphony.helper.ControllerHelper;
import xyz.imwyy.symphony.helper.IocHelper;
import xyz.imwyy.symphony.util.ClassUtil;

/**
 * create by stephen on 2018/5/19
 */
public class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }

    }
}
