package proxy;

/**
 * create by stephen on 2018/6/3
 */
public class StaticProxy implements IInterface {

    @Override
    public void test() {
        before();
        System.out.println("test method");
        after();
    }

    public void before() {
        System.out.println();
        System.out.println("static proxy - before ");
    }

    public void after() {
        System.out.println("static proxy - after ");
        System.out.println();
    }
}
