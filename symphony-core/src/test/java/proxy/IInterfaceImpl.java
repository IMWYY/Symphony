package proxy;

/**
 * create by stephen on 2018/6/3
 */
public class IInterfaceImpl implements IInterface {
    private String test;

    @Override
    public void test() {
        System.out.println("test method ");
    }
}
