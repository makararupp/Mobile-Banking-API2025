package com.mk.mbanking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ResourceHandlersConfig implements WebMvcConfigurer {

    @Value("${file.server-path}")
    private String fileServerPath;
    @Value("${file.client-path}")
    private String fileClientPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         registry.addResourceHandler(fileClientPath)
                 .addResourceLocations("file:"+fileServerPath);
    }
}
