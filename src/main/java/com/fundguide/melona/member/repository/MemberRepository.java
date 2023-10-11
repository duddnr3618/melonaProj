package com.fundguide.melona.member.repository;


import com.fundguide.melona.member.dto.MemberDto;
import com.fundguide.melona.member.dto.MemberLeastDTO;

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

    Page<MemberLeastDTO> memberLimitStatePage(MemberLimitState state, Pageable pageable);

    Page<MemberLeastDTO> findAllOfMemberLeastData(Pageable pageable);

    Page<MemberLeastDTO> memberRoleStateFilterPage(String filter , Pageable pageable);

    void adminSave(MemberEntity memberEntity);
}
