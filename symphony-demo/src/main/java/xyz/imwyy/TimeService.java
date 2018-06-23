package xyz.imwyy;

import xyz.imwyy.symphony.bean.annotation.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by stephen on 2018/6/23
 */
@Service
public class TimeService {

    public String getCurrentTime() {
        System.out.println("TimeService - getCurrentTime");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

}
