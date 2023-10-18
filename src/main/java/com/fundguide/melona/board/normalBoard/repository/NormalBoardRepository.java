package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NormalBoardRepository extends JpaRepository<NormalBoardEntity, Long>, NormalBoardRepositoryCustom {
    Page<NormalBoardEntity> findByBoardTitleContaining(String searchKeyword, Pageable pageable);
    @Modifying
    @Query(value = "update NormalBoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);

    Page<NormalBoardEntity> findByMemberEntity(MemberEntity memberEntity, Pageable pageable);
}
