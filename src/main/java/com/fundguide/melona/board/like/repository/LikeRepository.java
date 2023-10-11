package com.fundguide.melona.board.like.repository;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.like.entity.LikeEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository

@Transactional(readOnly = true)
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {


}
