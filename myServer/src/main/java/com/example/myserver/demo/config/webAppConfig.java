package com.example.myserver.demo.config;

import com.example.myserver.demo.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class webAppConfig implements WebMvcConfigurer {

    private final List<String> privateResponse = new ArrayList<String>() {{
        add("/homeManager/**");
        add("/privateCloudDriver/**");
    }};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns(privateResponse);
    }
}
