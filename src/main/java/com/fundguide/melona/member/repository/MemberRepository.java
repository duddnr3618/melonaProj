package com.fundguide.melona.member.repository;


import com.fundguide.melona.management.dto.MemberRoleFilterDTO;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface MemberRepository {
    void memberSave(MemberEntity memberEntity);

    MemberEntity findMember(String email , String memberNickname);

    MemberEntity findByEmail(String username);

    void updatePassword(Long memberId, String newPassword);

    void memberUpdate(MemberEntity memberEntity);

    void withdraw(Long id);

    Page<MemberEntity> memberLimitStatePage(MemberLimitState state, Pageable pageable);

    Page<MemberRoleFilterDTO> memberRoleStatePage(Pageable pageable);

    Page<MemberRoleFilterDTO> memberRoleStatePage(String filter , Pageable pageable);
}
