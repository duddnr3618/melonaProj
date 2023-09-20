package com.fundguide.melona.board.common.dto;

import lombok.Builder;

@Builder
public record BoardSearchDTO(
        String searchKeyword
        , String searchOption
) {

}
