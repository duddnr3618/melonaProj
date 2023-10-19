package com.fundguide.melona.board.community.repository.impeach;

import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;

public interface CommunityImpeachCustom {
    
    /**해당 게시물에 이미 신고했는지 확인*/
    boolean checkAlreadyImpeach(CommunityImpeachEntity impeach);
}
