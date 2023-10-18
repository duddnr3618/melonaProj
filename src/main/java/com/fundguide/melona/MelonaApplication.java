package com.fundguide.melona;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
public class MelonaApplication {

	public static void main(String[] args) throws IOException {

		ConfigurableApplicationContext context = SpringApplication.run(MelonaApplication.class, args);
		MemberRepositoryData memberRepositoryData = context.getBean(MemberRepositoryData.class);
		MemberService memberService = context.getBean(MemberService.class);
		String adminEmail = context.getEnvironment().getProperty("admin.email");

		Optional<MemberEntity> byMemberEmail = memberRepositoryData.findByMemberEmail(adminEmail);
		if(byMemberEmail.isEmpty()){
			memberService.adminSave(adminEmail);
		}

		String[] dirArray = {
				contextGetObj("normalAbsolutePath.dir", context),
				contextGetObj("leaderAbsolutePath.dir", context),
				contextGetObj("communityAbsolutePath.dir", context),
		};

		for (String targetDir : dirArray) {
			Path targetPath = Paths.get(targetDir);

			if (!Files.exists(targetPath)) {
				Files.createDirectories(targetPath);
			}
		}
	}

	protected static String contextGetObj (String property, ConfigurableApplicationContext context) {
		return Objects.requireNonNull(context.getEnvironment().getProperty(property)).substring(2);
	}
}
