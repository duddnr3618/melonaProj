package com.fundguide.melona.member.service;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Page<MemberEntity> getMemberPage(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }
}
