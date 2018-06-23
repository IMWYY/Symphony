package xyz.imwyy;

import xyz.imwyy.symphony.aop.annotation.Around;
import xyz.imwyy.symphony.aop.annotation.Aspect;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by stephen on 2018/6/23
 */
@Aspect
public class LoggerAop {

    @Around(classExpression = "xyz.imwyy.TimeService", methodExpression = "*")
    public void logTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        System.out.println("loggor AOP: " + currentTime);
    }
}
