package xyz.imwyy.symphony.transaction;

import xyz.imwyy.symphony.aop.Proxy;
import xyz.imwyy.symphony.aop.ProxyChain;
import xyz.imwyy.symphony.util.DbUtil;

import java.lang.reflect.Method;

/**
 * 事务控制的代理类
 * create by stephen on 2018/6/5
 */
public class TransactionProxy implements Proxy{

    private static final ThreadLocal<Boolean> TRANSACTION_FLAG = ThreadLocal.withInitial(() -> false);

    /**
     * 如果方法被TransactionalMethod修饰才会触发代理
     */
    @Override
    public Object doProxy(ProxyChain proxyChain) {
        Object result = null;
        boolean flag = TRANSACTION_FLAG.get();
        Method method = proxyChain.getMethod();
        if (!flag && method.isAnnotationPresent(TransactionalMethod.class)) {
            TRANSACTION_FLAG.set(true);
            DbUtil.beginTransaction();
            try {
                result = proxyChain.doProxyChain();
                DbUtil.commitTransaction();
            } catch (Throwable throwable) {
                DbUtil.rollBackTransaction();
                throwable.printStackTrace();
            } finally {
                TRANSACTION_FLAG.remove();
            }
        } else {
            try {
                result = proxyChain.doProxyChain();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }


}
