package xyz.imwyy.symphony;

import xyz.imwyy.symphony.annotation.RequestType;
import xyz.imwyy.symphony.bean.Data;
import xyz.imwyy.symphony.bean.Handler;
import xyz.imwyy.symphony.bean.Param;
import xyz.imwyy.symphony.bean.View;
import xyz.imwyy.symphony.helper.BeanHelper;
import xyz.imwyy.symphony.helper.ConfigHelper;
import xyz.imwyy.symphony.helper.ControllerHelper;
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

//    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        SymphonyLoader.init();
//        ServletContext servletContext = config.getServletContext();
//        // 处理jsp的context
//        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
//        // 处理静态资源
//        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
//        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestType requestType = RequestType.valueOf(req.getMethod().toUpperCase());
        String requestPath = req.getServletPath();

        System.out.println("DispatcherServlet - service - " + requestType + " - " + requestPath);

        // 拿到handler
        Handler handler = ControllerHelper.getHandler(requestPath, requestType);

        if (handler == null) {
            resp.sendError(404);
            return;
        }

        // 拿到controller的实例
        Class<?> controllerClass = handler.getControllerClass();
        Object controllerBean = BeanHelper.getBean(controllerClass);

        // 构造方法的参数
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

        // 调用方法
        Method method = handler.getActionMethod();
        Object result;
        if (param.isEmpty()) {
            result = ReflectionUtil.invokeMethod(controllerBean, method);
        } else {
            result = ReflectionUtil.invokeMethod(controllerBean, method, param);
        }

        // 处理返回结果
        if (result instanceof View) {
            handleViewResult((View) result, req, resp);
        } else if (result instanceof Data) {
            handleDataResult((Data) result, req, resp);
        }
    }

    /**
     * 处理视图返回结果
     */
    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = view.getPath();
        if (path == null || path.length() == 0) return;

        // 如果是以 "/"开头则直接跳转
        if (path.startsWith("/")) {
            resp.sendRedirect(req.getContextPath() + path);
            // 否则将数据转发到jsp页面
        } else {
            Map<String, Object> model = view.getModel();
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                req.setAttribute(entry.getKey(), entry.getValue());
            }
            req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
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
