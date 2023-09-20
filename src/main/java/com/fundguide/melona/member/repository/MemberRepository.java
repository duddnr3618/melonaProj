package com.fundguide.melona.member.repository;


import com.fundguide.melona.member.entity.MemberEntity;

import java.util.List;

public interface MemberRepository {
    void memberSave(MemberEntity memberEntity);

    List<MemberEntity> findMember(String email , Integer age);

    MemberEntity findByEmail(String username);
}
