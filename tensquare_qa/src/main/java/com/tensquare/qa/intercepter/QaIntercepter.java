package com.tensquare.qa.intercepter;


import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

@Component
public class QaIntercepter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        final String autoHandler = request.getHeader("Authorization");
        if(autoHandler != null && autoHandler.startsWith("Bearer ")) {
            String token = autoHandler.substring(7);
            Claims claims = jwtUtils.parseJWT(token);
            if (claims != null) {
                if ("admin".equals(claims.get("roles"))) {
                    request.setAttribute("admin_claims", claims);
                }
                if ("user".equals(claims.get("roles"))) {
                    request.setAttribute("user_claims", claims);
                }
            }
        }
        return true;
    }
}
