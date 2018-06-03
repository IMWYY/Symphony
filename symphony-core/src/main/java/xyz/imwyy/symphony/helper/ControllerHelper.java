package xyz.imwyy.symphony.helper;

import xyz.imwyy.symphony.annotation.RequestType;
import xyz.imwyy.symphony.annotation.Route;
import xyz.imwyy.symphony.bean.Handler;
import xyz.imwyy.symphony.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器类的助手类 处理所有被注解的路由
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
                            String url = route.value();
                            RequestType type = route.type();
                            if (url.startsWith("/")) {
                                Request request = new Request(url, type);
                                Handler handler = new Handler(clazz, method);

                                ROUTE_MAP.put(request, handler);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler getHandler(String reqPath, RequestType requestType) {
        Request request = new Request(reqPath, requestType);
        return ROUTE_MAP.get(request);
    }
}
