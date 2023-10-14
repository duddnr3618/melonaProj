package com.fundguide.melona;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.service.MemberService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.Optional;

@SpringBootApplication
public class MelonaApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(MelonaApplication.class, args);
		MemberRepositoryData memberRepositoryData = context.getBean(MemberRepositoryData.class);
		MemberService memberService = context.getBean(MemberService.class);
		String adminEmail = context.getEnvironment().getProperty("admin.email");

		Optional<MemberEntity> byMemberEmail = memberRepositoryData.findByMemberEmail(adminEmail);

		if(byMemberEmail.isPresent()){
		}else {
			memberService.adminSave(adminEmail);
		}

		//	String dir=context.getEnvironment().getProperty("boardAbsolutePath.dir");
		//	String result = dir.substring(2);   // 디렉토리 자른 경로
/*

		File file = new File(result);
		if(!file.isDirectory()){
			file.mkdir();
		}
*/


	}
	
}
