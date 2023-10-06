package com.fundguide.melona;

import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MelonaApplicationTests {
	private final PasswordEncoder utilsPasswordEncoder;
	@Autowired
	MelonaApplicationTests(PasswordEncoder utilsPasswordEncoder) {
		this.utilsPasswordEncoder = utilsPasswordEncoder;
	}

	@Test
	void contextLoads() {
	}


	@Autowired
	EntityManager em;

	@Transactional
	@Commit
	@Test
	public void before() {
		String pwd = utilsPasswordEncoder.encode("qwerqwerqwer");
		MemberEntity memberEntity = MemberEntity.builder()
				.memberEmail("admin@gmail.com")
				.memberRole("ROLE_ADMIN")
				.memberPassword(pwd)
				.memberAddress("no")
				.memberName("ADMIN")
				.memberNickname("ADMIN")
				.memberAvailable("yes")
				.build();
		em.persist(memberEntity);
	}
}
