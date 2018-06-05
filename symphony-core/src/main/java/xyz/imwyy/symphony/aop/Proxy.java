package xyz.imwyy.symphony.aop;

/**
 * 代理的接口 支持链式代理
 * create by stephen on 2018/6/5
 */
public interface Proxy {

    Object doProxy(ProxyChain proxyChain);
}
