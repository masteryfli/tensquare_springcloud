package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";                                               // 前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;                                                  //优先级，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;                                             //是否执行改过滤器
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("Zool 过滤器");
        //向Headet中添加签发令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取Header
        HttpServletRequest httpServletRequest = requestContext.getRequest();
        String authorization = httpServletRequest.getHeader("Authorization");
        if(authorization != null && !"".equals(authorization)) {
            requestContext.addZuulRequestHeader("Authorization", authorization);
        }
            return null;
    }
}
