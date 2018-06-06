package xyz.imwyy.controller;

import xyz.imwyy.symphony.mvc.annotation.Controller;
import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.annotation.Route;
import xyz.imwyy.symphony.mvc.web.Data;
import xyz.imwyy.symphony.mvc.web.View;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by stephen on 2018/5/17
 */
@Controller
public class HelloController {

    @Route(value = "/hello", type = RequestType.GET)
    public View hello() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        View view = new View("hello.jsp");
        view.addModel("currentTime", currentTime);
        return view;
    }

    @Route(value = "/test", type = RequestType.POST)
    public Data test(HttpServletResponse response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        return new Data(currentTime);
    }
}
