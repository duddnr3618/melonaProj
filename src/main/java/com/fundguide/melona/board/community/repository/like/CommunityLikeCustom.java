package com.fundguide.melona.board.community.repository.like;

import com.fundguide.melona.board.community.entity.Community_like;

public interface CommunityLikeCustom {
    
    /**해당 게시물에 좋아요가 되어있는지 확인*/
    boolean checkAlreadyLike(Community_like like);

    /**해당 게시물에 좋아요 조회후 엔티티 반환*/
    Community_like searchAlreadyLike(Community_like like);

    /**해당 게시물에 좋아요가 되어있는지 확인후 삭제*/
    void removeLike(Community_like like);
}
