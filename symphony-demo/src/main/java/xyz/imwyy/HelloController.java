package xyz.imwyy;

import xyz.imwyy.symphony.bean.annotation.Inject;
import xyz.imwyy.symphony.mvc.annotation.Controller;
import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.annotation.Route;
import xyz.imwyy.symphony.mvc.model.Data;
import xyz.imwyy.symphony.mvc.model.Param;
import xyz.imwyy.symphony.mvc.model.View;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by stephen on 2018/5/17
 */
@Controller("/main")
public class HelloController {

    @Inject
    private TimeService timeService;

    @Route(value = "/hello", type = RequestType.GET)
    public View hello() {
        View view = new View("hello.jsp");
        view.addModel("currentTime", timeService.getCurrentTime());
        return view;
    }

    @Route(value = "/test", type = RequestType.POST)
    public Data test(Param param) {
        return new Data(timeService.getCurrentTime());
    }

    @Route(value = "/bye", type = RequestType.GET)
    public Data bye(HttpServletResponse response) {
        return new Data(timeService.getCurrentTime());
    }
}
