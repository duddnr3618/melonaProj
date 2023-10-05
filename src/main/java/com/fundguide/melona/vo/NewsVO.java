package com.fundguide.melona.vo;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
// NewsVO는 네이버 검색 API의 뉴스 검색 결과의 개별 아이템을 표현하는 클래스
public class NewsVO {
    // 뉴스의 제목
    private String title;
    // 뉴스의 원본 링크(출처 웹사이트의 실제 URL)
    private String originallink;
    // 네이버에서 제공하는 뉴스의 링크
    private String link;
    // 뉴스의 요약 설명 또는 내용
    private String description;
    // 뉴스가 발행된 날짜와 시간
    private String pubDate;
}
