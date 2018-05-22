package xyz.imwyy.symphony;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.imwyy.symphony.bean.Data;
import xyz.imwyy.symphony.bean.Handler;
import xyz.imwyy.symphony.bean.Param;
import xyz.imwyy.symphony.bean.View;
import xyz.imwyy.symphony.helper.BeanHelper;
import xyz.imwyy.symphony.helper.ConfigHelper;
import xyz.imwyy.symphony.helper.ControllerHelper;
import xyz.imwyy.symphony.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
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
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
LOGGER.debug("DispatcherServlet - init");
        SymphonyLoader.init();
        ServletContext servletContext = config.getServletContext();
        // 处理jsp的context
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 处理静态资源
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        // 拿到handler
        Handler handler = ControllerHelper.getHandler(reqMethod, requestPath);
        if (handler == null) return;

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
        Object result = ReflectionUtil.invokeMethod(controllerBean, method, param);

        // 处理返回结果
        // 视图结果
        if (result instanceof View) {
            View view = (View)result;
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
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                }
            }
        // json结果
        } else if (result instanceof Data) {
            Data data = (Data)result;
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
}
