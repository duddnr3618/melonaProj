package com.fundguide.melona.member.repository;


import com.fundguide.melona.member.entity.MemberEntity;

import java.util.List;

public interface MemberRepository {
    void memberSave(MemberEntity memberEntity);

    MemberEntity findMember(String email , String memberNickname);

    MemberEntity findByEmail(String username);

    void updatePassword(Long memberId, String newPassword);

    void memberUpdate(MemberEntity memberEntity);
}
