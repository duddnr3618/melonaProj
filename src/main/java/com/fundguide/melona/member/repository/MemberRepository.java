package com.fundguide.melona.member.repository;


import com.fundguide.melona.member.dto.MemberLeastDTO;

import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface MemberRepository {
    void memberSave(MemberEntity memberEntity);

    MemberEntity findMember(String email , String memberNickname);

    MemberEntity findByEmail(String username);

    void updatePassword(Long memberId, String newPassword);

    void memberUpdate(MemberEntity memberEntity);

    void withdraw(Long id);

    /*---------------------------------------------------------------------------------------------*/
    Page<MemberLeastDTO> memberLimitStatePage(MemberLimitState state, Pageable pageable);

    /**
     * 필요값을 DTO로 변환 시켜 출력하는 메서드 readOnly
     *
     * @param pageable 페이저블 처리를 위한 값
     * @return Page-All
     */
    Page<MemberLeastDTO> findAllOfMemberLeastData(Pageable pageable);

    /**
     * 필터링된 멤버 Role DTO로 전달해 페이징 처리하는 메서드 readOnly
     * @param filter   필터처리를 위한 값 필요시 case를 더 설정할것
     * @param pageable 페이저블 처리를 위한 값
     * @return Page
     */
    Page<MemberLeastDTO> memberRoleStateFilterPage(String filter , Pageable pageable);

    Page<MemberLeastDTO> evaluatePendingByRule(String filter, Pageable pageable);

    /**이메일로 옵셔널 값을 반환하기 위한 메서드*/
    Optional<MemberEntity> findByMemberEamilOptional(String email);
}
