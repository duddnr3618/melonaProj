package com.fundguide.melona.CommonConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileResourceHandlerConfig implements WebMvcConfigurer {

    @Value("${testAbsolutePath.dir}")
    private String testAbsolutePath;
    @Value("${testResoucrePath.dir}")
    private String testResoucrePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(testResoucrePath + "**")
                .addResourceLocations("file:///" + testAbsolutePath);
    }
}
