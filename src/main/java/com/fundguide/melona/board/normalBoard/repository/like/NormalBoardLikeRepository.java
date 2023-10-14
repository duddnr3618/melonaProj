package com.fundguide.melona.board.normalBoard.repository.like;

import com.fundguide.melona.board.normalBoard.entity.NormalBoard_like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalBoardLikeRepository extends JpaRepository<NormalBoard_like, Long>, NormalBoardLikeCustom {

}
