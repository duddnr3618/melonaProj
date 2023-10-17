package com.fundguide.melona.news;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
// NaverResultVO는 네이버 검색 API 응답의 결과를 매핑하기 위한 클래스
public class NaverResultVO3 {
    // 검색 결과가 마지막으로 빌드된 날짜를 출력.
    private String lastBuildDate;
    // 검색 결과의 총 개수.
    private int total;
    // 검색 결과 중 현재 페이지의 시작 인덱스.
    private int start;
    // 현재 페이지에 표시된 검색 결과의 개수.
    private int display;
    // 검색 결과의 실제 아이템 목록.
    // NewsVO 객체의 list로, 각 item은 하나의 뉴스로 나타냄.
    private List<NewsVO3> items;

}