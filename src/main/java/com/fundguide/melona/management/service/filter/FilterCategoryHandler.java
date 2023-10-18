package com.fundguide.melona.management.service.filter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**각 보드 관리를 위한 인터페이스. <br>
 * 신고수가 n이상인 보드를 waring에 출력 <br>
 * 비활성화된 보드를 block에 출력 <br>
 * 모든 게시판 목록을 all에 출력 <br>*/
public interface FilterCategoryHandler {
    default Page<?> handleFilterCategory(String filter, Pageable pageable) {
        switch (filter) {
            case "waring" -> {
                return waringPage(pageable);
            }
            case "block" -> {
                return blockPage(pageable);
            }
            default -> {
                if (!filter.equals("all")) {
                    throw new RuntimeException("경고 : 분류된 필터값이 아닙니다");
                }
                return allPage(pageable);
            }
        }
    }

    /**경고 필터 처리한 페이지를 전달할것*/
    Page<?> waringPage(Pageable pageable);
    /**블락 필터 처리한 페이지를 전달할것*/
    Page<?> blockPage(Pageable pageable);
    /**모든 페이지를 전달할것*/
    Page<?> allPage(Pageable pageable);
}
