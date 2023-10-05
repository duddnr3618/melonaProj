package com.fundguide.melona.board.common.dto;

import lombok.Builder;

@Builder
public record CommonBoardSearchDTO(
        String searchKeyword
        , String searchOption
) {

}
