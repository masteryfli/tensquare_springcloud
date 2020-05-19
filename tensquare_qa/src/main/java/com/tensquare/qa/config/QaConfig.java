package com.tensquare.qa.config;

import com.tensquare.qa.intercepter.QaIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class QaConfig extends WebMvcConfigurationSupport {

    @Autowired
    private QaIntercepter qaIntercepter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(qaIntercepter)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login");
    }
}
