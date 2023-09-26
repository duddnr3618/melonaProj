package com.fundguide.melona.member.repository;


import com.fundguide.melona.management.dto.MemberRoleFilterDTO;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.role.MemberRoleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepository {
    void memberSave(MemberEntity memberEntity);

    List<MemberEntity> findMember(String email , Integer age);

    MemberEntity findByEmail(String username);

    Page<MemberEntity> memberLimitStatePage(MemberLimitState state, Pageable pageable);

    Page<MemberRoleFilterDTO> memberRoleStatePage(Pageable pageable);
    Page<MemberRoleFilterDTO> memberRoleStatePage(String filter , Pageable pageable);
}
