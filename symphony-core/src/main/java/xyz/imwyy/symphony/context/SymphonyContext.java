package xyz.imwyy.symphony.context;


import xyz.imwyy.symphony.mvc.MvcFactory;


public interface SymphonyContext extends MvcFactory {

    public Object getBean(String name) throws Exception;

}
