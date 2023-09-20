package com.fundguide.melona.CommonConfig.resourcehandlerconfig.boardresource;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@NoArgsConstructor
@PropertySource("classpath:application-board.properties")
public class QnAResourceHandlerConfig implements WebMvcConfigurer {
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
