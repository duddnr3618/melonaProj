package com.fundguide.melona.member.service;

import com.fundguide.melona.member.dto.MemberDto;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder BCryptPasswordEncoder;

    public void memberSave(MemberDto memberDto) {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberPassword(BCryptPasswordEncoder.encode(memberDto.getMemberPassword()));
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setMemberAddress(memberDto.getMemberAddress());
        memberEntity.setMemberNickname(memberDto.getMemberNickname());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberRole("ROLE_MEMBER");
        memberRepository.memberSave(memberEntity);
    }
}
