package com.fundguide.melona.CommonConfig.resourcehandlerconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileResourceHandlerConfig implements WebMvcConfigurer {

    @Value("${commonAbsolutePath.dir}")
    private String commonAbsolutePath;
    @Value("${commonResourcePath.dir}")
    private String commonResourcePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(commonResourcePath + "**")
                .addResourceLocations("file:///" + commonAbsolutePath);
    }
}
