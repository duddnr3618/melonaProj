package com.fundguide.melona.member.repository;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepository {
    void memberSave(MemberEntity memberEntity);

    List<MemberEntity> findMember(String email , Integer age);

    MemberEntity findByEmail(String username);

    Page<MemberEntity> memberLimitStatePage(MemberLimitState state, Pageable pageable);
}
