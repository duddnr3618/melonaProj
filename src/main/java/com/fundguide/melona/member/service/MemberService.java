package com.fundguide.melona.member.service;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.dto.MemberDto;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.repository.MemberRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {

    private MemberRepositoryJpa memberRepositoryJpa;
    private MemberRepositoryData memberRepositoryData;
    private final PasswordEncoder BCryptPasswordEncoder;


    public Page<MemberEntity> getMemberPage(Pageable pageable) {
        return memberRepositoryData.findAll(pageable);
    }

}
