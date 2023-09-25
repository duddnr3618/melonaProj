package com.fundguide.melona.management.service.filter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterCategoryHandler {

    default Page<?> handleFilterCategory(String filter, Pageable pageable) {

        switch (filter) {
            case "waring" -> {
                return waringPage();
            }
            case "block" -> {
                return blockPage();
            }
            default -> {
                if (!filter.equals("all")) {
                    throw new RuntimeException("Warring : Invalid Filter Value");
                }
                return allPage();
            }
        }
    }

    Page<?> waringPage();
    Page<?> blockPage();
    Page<?> allPage();
}