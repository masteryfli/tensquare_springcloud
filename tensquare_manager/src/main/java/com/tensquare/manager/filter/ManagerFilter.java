package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String filterType() {
        return  "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器了");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        //排除自带方法
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            return null;
        }

        //排除登录请求
        String url = httpServletRequest.getRequestURI().toString();
        if (url.indexOf("/admin/login") > 0) {
            return null;
        }
        //获取头信息
        String authorization = httpServletRequest.getHeader("Authorization");
        if(StringUtils.isNotBlank(authorization) && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            Claims claims = jwtUtils.parseJWT(token);
            if (claims != null) {
                if("admin".equals(claims.get("roles"))) {
                    requestContext.addZuulRequestHeader("Authorization", authorization);
                    System.out.println("token验证通过，添加头信息" + authorization);
                    return null;
                }
            }
        }
        requestContext.setSendZuulResponse(false);                      //终止执行
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody("权限不足");

        requestContext.getResponse().setContentType("text/html; charset=UTF-8");
        return null;
    }
}
