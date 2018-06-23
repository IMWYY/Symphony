package xyz.imwyy;

import org.junit.Assert;
import org.junit.Test;
import xyz.imwyy.symphony.context.AnnotationDrivenSymphonyContext;
import xyz.imwyy.symphony.context.SymphonyContext;
import xyz.imwyy.symphony.mvc.annotation.RequestType;
import xyz.imwyy.symphony.mvc.model.Request;

/**
 * create by stephen on 2018/6/23
 */
public class Main {

    @Test
    public void testBean() throws Exception {
        SymphonyContext context = new AnnotationDrivenSymphonyContext();
        Object o = context.getBean("player");
        Assert.assertTrue(o != null);

        o = context.getBean("loggerAop");
        Assert.assertTrue(o != null);

        o = context.getBean("autoProxyCreator");
        Assert.assertTrue(o != null);
    }

    @Test
    public void testRoute() {
        Request request = new Request("/", RequestType.GET);
        Request request2 = new Request("/hello", RequestType.GET);

        System.out.println(request.equals(request2));
        System.out.println(request.hashCode() == request2.hashCode());
    }
}
