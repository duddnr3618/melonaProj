package com.fundguide.melona.board.dto;

import lombok.Builder;

@Builder
public record CommonBoardSearchDTO(
        String searchKeyword
        , String searchOption
) {

}
