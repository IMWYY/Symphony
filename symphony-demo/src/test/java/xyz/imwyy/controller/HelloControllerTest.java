package xyz.imwyy.controller;


import org.junit.Test;
import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.model.Request;


public class HelloControllerTest {

    @Test
    public void test() {
        Request request1 = new Request("/", RequestType.GET);
        Request request2 = new Request("/test", RequestType.GET);
        System.out.println(request1.equals(request2));
        System.out.println(request1.hashCode() == request2.hashCode());
    }

}
