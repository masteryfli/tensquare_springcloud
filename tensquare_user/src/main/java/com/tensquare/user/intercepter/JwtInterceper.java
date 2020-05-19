package com.tensquare.user.intercepter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceper extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截");

        final String authHandler = request.getHeader("Authorization");
        if (authHandler != null && authHandler.startsWith("Bearer ")) {
            String token = authHandler.substring(7);
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
