package com.fundguide.melona.board.common.dto;

/**page에 대한 null값은 반드시 분기처리할것*/
public record BoardSearchDTO(String searchKeyword, String searchOption, int searchPageValue) { }