package com.example.myserver.demo.config;

import com.example.myserver.demo.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class webAppConfig implements WebMvcConfigurer {

    private final List<String> privateResponse = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
        {
        /**
         * paths which need intercept
         */
        add("/homeManager/**");
        add("/privateCloudDriver/**");
    }};
    private final List<String> excludeResponse = new ArrayList<String>() {{
        add("/login/log");
    }};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns(privateResponse);
    }

    //webSocket自动注册支持
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
