package xyz.imwyy.symphony.mvc;

import xyz.imwyy.symphony.context.AnnotationDrivenSymphonyContext;
import xyz.imwyy.symphony.context.SymphonyContext;
import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.handler.Handler;
import xyz.imwyy.symphony.ConfigContext;
import xyz.imwyy.symphony.mvc.model.Data;
import xyz.imwyy.symphony.mvc.model.Param;
import xyz.imwyy.symphony.mvc.model.View;
import xyz.imwyy.symphony.util.EncodeUtil;
import xyz.imwyy.symphony.util.JsonUtil;
import xyz.imwyy.symphony.util.ReflectionUtil;
import xyz.imwyy.symphony.util.StreamUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

/**
 * 分发请求的servlet类
 * create by stephen on 2018/5/20
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static SymphonyContext symphonyContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            symphonyContext = new AnnotationDrivenSymphonyContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestType requestType = RequestType.valueOf(req.getMethod().toUpperCase());
        String requestPath = req.getServletPath();

        // 拿到handler
        Handler handler = symphonyContext.getHandler(requestPath, requestType);

        if (handler == null) {
            resp.sendError(404);
            return;
        }

        // 拿到controller的实例
        Class<?> controllerClass = handler.getControllerClass();
        Method handlerMethod = handler.getActionMethod();
        Object controllerBean = null;
        try {
            controllerBean = symphonyContext.getBean(ReflectionUtil.getClassId(controllerClass));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object[] paramInstance = createMethodParam(handlerMethod, req, resp);
        // 调用方法
        Object result = ReflectionUtil.invokeMethod(controllerBean, handlerMethod, paramInstance);

        // 处理返回结果
        if (result instanceof View) {
            handleViewResult((View) result, req, resp);
        } else if (result instanceof Data) {
            handleDataResult((Data) result, req, resp);
        }
    }

    /**
     * 构造method的调用参数 实现可以直接在参数里添加 HttpServletRequest和HttpServletResponse
     * @param method controller中的方法
     * @param req request
     * @return handler 参数
     */
    private Object[] createMethodParam(Method method, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Class<?>[] paramType = method.getParameterTypes();
        Object[] paramInstance = new Object[paramType.length];

        for (int i=0; i<paramType.length; ++i) {
            Class<?> cls = paramType[i];
            // 添加普通参数
            if (cls.equals(Param.class)) {
                Param param = new Param();
                Enumeration<String> paramNames = req.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String name = paramNames.nextElement();
                    param.put(name, req.getParameter(name));
                }

                String body = EncodeUtil.decodeURL(StreamUtil.getContent(req.getInputStream()));
                if (body != null && body.length() > 0) {
                    String[] params = body.split("&");
                    if (params.length > 0) {
                        for (String p : params) {
                            String[] array = p.split("=");
                            if (array.length == 2) {
                                param.put(array[0], array[1]);
                            }
                        }
                    }
                }

                paramInstance[i] = param;

            // 添加HttpServletRequest和HttpServletResponse
            } else if (cls.equals(HttpServletRequest.class)) {
                paramInstance[i] = req;
            } else if (cls.equals(HttpServletResponse.class)) {
                paramInstance[i] = resp;
            }
        }

        return paramInstance;
    }

    /**
     * 处理视图返回结果
     */
    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = view.getPath();
        if (path == null || path.length() == 0) return;

        // 如果是以 "/"开头则直接跳转
        if (path.startsWith("/")) {
            System.out.println("handleViewResult - if");
            resp.sendRedirect(req.getContextPath() + path);
            // 否则将数据转发到jsp页面
        } else {
            System.out.println("handleViewResult - else");
            Map<String, Object> model = view.getModel();
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                req.setAttribute(entry.getKey(), entry.getValue());
            }
            req.getRequestDispatcher(ConfigContext.getAppJspPath() + path).forward(req, resp);
        }
    }

    /**
     * 处理json返回结果
     */
    private void handleDataResult(Data data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object model = data.getModel();
        if (model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
