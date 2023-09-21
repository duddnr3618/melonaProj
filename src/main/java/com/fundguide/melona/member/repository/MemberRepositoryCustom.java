package com.fundguide.melona.member.repository;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<MemberEntity> memberLimitStatePage(MemberLimitState state, Pageable pageable);
}
