package com.tensquare.user.config;

import com.tensquare.user.intercepter.JwtInterceper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceper jwtInterceper;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceper).
                addPathPatterns("/**").
                excludePathPatterns("/**/login");
    }
}
