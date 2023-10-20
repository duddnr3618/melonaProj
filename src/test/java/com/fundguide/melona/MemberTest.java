package com.fundguide.melona;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberRepositoryData memberRepositoryData;
    @Autowired
    PasswordEncoder passwordEncoder;

    Random random = new Random();

    @Test
    void memberDummy() {
        MemberEntity memberEntity = null;
        MemberLimitState[] memberLimitStates = {MemberLimitState.NORMAL, MemberLimitState.PERMANENT, MemberLimitState.TRANSITORY, MemberLimitState.STRONG};
        for (long l = 0; l < 100; l++) {
            memberEntity = MemberEntity.builder()
                    .memberEmail("testEmail " + l)
                    .memberName("testName" + l)
                    .memberNickname("testNickname" + l)
                    .memberPassword(passwordEncoder.encode("1234"))
                    .memberRole(MemberRoleState.ROLE_USER)
                    .memberLimitState(memberLimitStates[random.nextInt(4)])
                    .build();
            memberRepositoryData.save(memberEntity);
        }
    }
}
