package com.tensquare.friend.config;

import com.tensquare.friend.intercepter.JwtIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtIntercepter jwtIntercepter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtIntercepter)
                .addPathPatterns("/**");
    }
}
