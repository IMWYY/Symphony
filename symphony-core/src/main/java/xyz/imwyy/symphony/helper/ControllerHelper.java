package xyz.imwyy.symphony.helper;

import xyz.imwyy.symphony.annotation.Route;
import xyz.imwyy.symphony.bean.Handler;
import xyz.imwyy.symphony.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * create by stephen on 2018/5/19
 */
public class ControllerHelper {

    private static Map<Request, Handler> ROUTE_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClasses = ClassHelper.getControllerClassSet();
        // 遍历所有的controller
        if (controllerClasses != null && controllerClasses.size() > 0) {
            for (Class<?> clazz : controllerClasses) {
                Method[] methods = clazz.getDeclaredMethods();
                // 检查所有的方法 找到有route注解的方法
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Route.class)) {
                            Route route = method.getAnnotation(Route.class);
                            // 获取url 格式为"请求方法:路径"
                            String url = route.value();
                            if (url.matches("\\w+:/\\w*")) {
                                String[] param = url.split(":");
                                if (param.length == 2) {
                                    Request request = new Request(param[0], param[1]);
                                    Handler handler = new Handler(clazz, method);

                                    ROUTE_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
