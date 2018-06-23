package xyz.imwyy.symphony.mvc.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import xyz.imwyy.symphony.mvc.annotation.RequestType;

/**
 * 请求信息的封装类 请求方法和请求路径
 * create by stephen on 2018/5/19
 */
public class Request {

    /**
     * 请求方法 get post等
     */
    private RequestType requestType;
    /**
     * 请求路径
     */
    private String requestPath;

    public Request(String requestMethod, RequestType requestType) {
        this.requestType = requestType;
        this.requestPath = requestMethod;
    }

    public RequestType getRequestMethod() {
        return requestType;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
