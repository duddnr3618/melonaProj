package com.fundguide.melona.board.community.repository;

import com.fundguide.melona.board.community.entity.CommentEntity;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByCommunityEntityOrderByIdDesc(CommunityEntity communityEntity);

}
