package com.fundguide.melona;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.service.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
public class MelonaApplication {

    public static void main(String[] args) throws IOException {

        ConfigurableApplicationContext context = SpringApplication.run(MelonaApplication.class, args);
        MemberRepositoryData memberRepositoryData = context.getBean(MemberRepositoryData.class);
        MemberService memberService = context.getBean(MemberService.class);
        String adminEmail = context.getEnvironment().getProperty("admin.email");

        Optional<MemberEntity> byMemberEmail = memberRepositoryData.findByMemberEmail(adminEmail);
        if (byMemberEmail.isEmpty()) {
            memberService.adminSave(adminEmail);
        }

        dirPathAutoCreate(context);
    }

    /** 프로퍼티에 할당한 ~AbsolutePath.dir 값을 찾아내 해당되는 폴더를 생성하는 메서드 */
    protected static void dirPathAutoCreate(ConfigurableApplicationContext context) {
        ConfigurableEnvironment env = context.getEnvironment();
        Stream<String> dirProperties = Stream.empty();

        for (PropertySource<?> propertySource : env.getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                dirProperties = Stream.concat(dirProperties,
                        Arrays.stream(((MapPropertySource) propertySource).getPropertyNames())
                                .filter(name -> name.endsWith("AbsolutePath.dir")));
            }
        }

        dirProperties.forEach(dirProp -> {
            String dir = Objects.requireNonNull(context.getEnvironment().getProperty(dirProp));
            Path targetPath;
            if (dir.startsWith("C:")) {
                targetPath = Paths.get(dir.substring(2));
            } else {
                targetPath = Paths.get(dir);
            }

            if (!Files.exists(targetPath)) {
                try {
                    Files.createDirectories(targetPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
