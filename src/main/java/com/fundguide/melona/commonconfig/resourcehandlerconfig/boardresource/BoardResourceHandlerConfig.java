package com.fundguide.melona.commonconfig.resourcehandlerconfig.boardresource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BoardResourceHandlerConfig {
    @Configuration
    private class NormalBoardResourceHandler implements WebMvcConfigurer {
        @Value("${normalAbsolutePath.dir}")
        private String normalAbsolutePath;
        @Value("${normalResourcePath.dir}")
        private String normalResourcePath;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler(normalResourcePath + "**")
                    .addResourceLocations("file:///" + normalAbsolutePath);
        }
    }

    @Configuration
    private class LeaderBoardResourceHandler implements  WebMvcConfigurer {
        @Value("${leaderAbsolutePath.dir}")
        private String leaderAbsolutePath;
        @Value("${leaderResourcePath.dir}")
        private String leaderResourcePath;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler(leaderResourcePath + "**")
                    .addResourceLocations("file:///" + leaderAbsolutePath);
        }
    }
}
