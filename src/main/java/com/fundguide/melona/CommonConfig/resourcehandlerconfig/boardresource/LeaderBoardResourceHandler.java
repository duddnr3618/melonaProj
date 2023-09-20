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
public class LeaderBoardResourceHandler implements WebMvcConfigurer {
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
