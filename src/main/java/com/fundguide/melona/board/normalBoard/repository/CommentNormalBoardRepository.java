package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.normalBoard.entity.CommentNormalBoardEntity;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentNormalBoardRepository extends JpaRepository<CommentNormalBoardEntity, Long> {
    List<CommentNormalBoardEntity> findAllByNormalBoardEntityOrderByIdDesc(NormalBoardEntity normalBoardEntity);

}
