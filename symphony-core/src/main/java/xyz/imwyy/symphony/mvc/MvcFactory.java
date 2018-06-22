package xyz.imwyy.symphony.mvc;

import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.handler.Handler;

/**
 * create by stephen on 2018/6/22
 */
public interface MvcFactory {

    public Handler getHandler(String reqPath, RequestType requestType);

}
