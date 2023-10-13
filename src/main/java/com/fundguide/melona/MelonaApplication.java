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
		SpringApplication.run(MelonaApplication.class, args);




	}
	
}
