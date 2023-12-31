package com.fundguide.melona.member.repository;

import com.fundguide.melona.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepositoryData extends JpaRepository<MemberEntity,Long> {

    Optional<MemberEntity> findByMemberEmail(String email);

    Optional<MemberEntity> findById(Long id);
    /*---------------------------------------------------------------------------------------------*/
}
