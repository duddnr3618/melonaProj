package com.fundguide.melona.commonconfig.resourcehandlerconfig.boardresource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class QnAResourceHandlerConfig {
    @Configuration
    private class NormalBoardResourceHandler implements WebMvcConfigurer {
        @Value("${qnaAbsolutePath.dir}")
        private String normalAbsolutePath;
        @Value("${qnaResourcePath.dir}")
        private String normalResourcePath;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler(normalResourcePath + "**")
                    .addResourceLocations("file:///" + normalAbsolutePath);
        }
    }
}
