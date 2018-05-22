package xyz.imwyy.controller;

import xyz.imwyy.symphony.annotation.Controller;
import xyz.imwyy.symphony.annotation.Route;
import xyz.imwyy.symphony.bean.Param;
import xyz.imwyy.symphony.bean.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by stephen on 2018/5/17
 */
@Controller
public class HelloController {

    @Route("get:/hello")
    public View hello(Param param) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        View view = new View("/hello.jsp");
        view.addModel("currentTime", currentTime);
        return view;
    }
}
