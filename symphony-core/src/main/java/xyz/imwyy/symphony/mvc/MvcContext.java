package xyz.imwyy.symphony.mvc;

import xyz.imwyy.symphony.mvc.annotation.Controller;
import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.annotation.Route;
import xyz.imwyy.symphony.mvc.handler.Handler;
import xyz.imwyy.symphony.mvc.model.Request;
import xyz.imwyy.symphony.util.ClassFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器类的助手类 处理所有被注解的路由
 * create by stephen on 2018/5/19
 */
public class MvcContext implements MvcFactory {

    private Map<Request, Handler> ROUTE_MAP = new HashMap<>();

    public MvcContext() {
        init();
    }

    private void init() {
        Set<Class<?>> controllerClasses = ClassFactory.getAnnotatedClassSet(Controller.class);
        // 遍历所有的controller
        if (controllerClasses != null && controllerClasses.size() > 0) {
            for (Class<?> clazz : controllerClasses) {
                Method[] methods = clazz.getDeclaredMethods();

                // 处理controller的注解
                Controller controller = clazz.getAnnotation(Controller.class);
                String baseUrl = controller.value();
                if (baseUrl.isEmpty()) continue;
                if (!baseUrl.startsWith("/")) baseUrl = "/" + baseUrl;
                if (baseUrl.endsWith("/") && baseUrl.length() > 2) baseUrl = baseUrl.substring(0, baseUrl.length() - 1);

                // 检查所有的方法 找到有route注解的方法
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Route.class)) {
                            Route route = method.getAnnotation(Route.class);
                            String url = route.value();
                            RequestType type = route.type();

                            if (!url.startsWith("/")) {
                                url = "/" + url;
                            }
                            url = baseUrl + url; //加上controller的url

                            Request request = new Request(url, type);
                            Handler handler = new Handler(clazz, method);
                            ROUTE_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Handler getHandler(String reqPath, RequestType requestType) {
        Request request = new Request(reqPath, requestType);
        return ROUTE_MAP.get(request);
    }

}
