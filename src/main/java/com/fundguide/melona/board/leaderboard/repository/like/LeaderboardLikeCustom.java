package com.fundguide.melona.board.leaderboard.repository.like;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoard_like;
import com.fundguide.melona.board.normalBoard.entity.NormalBoard_like;

public interface LeaderboardLikeCustom {
    
    /**해당 게시물에 좋아요가 되어있는지 확인*/
    boolean checkAlreadyLike(LeaderBoard_like like);

    /**해당 게시물에 좋아요 조회후 엔티티 반환*/
    LeaderBoard_like searchAlreadyLike(LeaderBoard_like like);

    /**해당 게시물에 좋아요가 되어있는지 확인후 삭제*/
    void removeLike(LeaderBoard_like like);
}
