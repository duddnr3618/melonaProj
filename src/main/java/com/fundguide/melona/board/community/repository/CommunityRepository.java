package com.fundguide.melona.board.community.repository;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.like.entity.LikeEntity;
import com.fundguide.melona.board.like.repository.LikeRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Long>, CommunityRepositoryCustom {
    Page<CommunityEntity> findByBoardTitleContaining(String searchKeyword, Pageable pageable);

    /* 조회수 증가 */
    @Modifying
    @Query(value = "update CommunityEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);



}

