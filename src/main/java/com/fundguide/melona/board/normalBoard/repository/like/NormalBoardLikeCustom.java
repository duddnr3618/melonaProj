package com.fundguide.melona.board.normalBoard.repository.like;

import com.fundguide.melona.board.normalBoard.entity.NormalBoard_like;

public interface NormalBoardLikeCustom {
    
    /**해당 게시물에 좋아요가 되어있는지 확인*/
    boolean checkAlreadyLike(NormalBoard_like like);

    /**해당 게시물에 좋아요 조회후 엔티티 반환*/
    NormalBoard_like searchAlreadyLike(NormalBoard_like like);

    /**해당 게시물에 좋아요가 되어있는지 확인후 삭제*/
    void removeLike(NormalBoard_like like);
}
