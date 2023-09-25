package com.fundguide.melona.management.controller;

import com.fundguide.melona.board.normalBoard.service.NormalBoardCommandService;
import com.fundguide.melona.board.normalBoard.service.NormalBoardQueryService;
import com.fundguide.melona.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.parser.Entity;

@Controller @RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {
    private final MemberService memberService;
    private final NormalBoardCommandService normalBoardCommandService;
    private final NormalBoardQueryService normalBoardQueryService;

    @GetMapping
    public String mainManagementView() {
        return "management/management";
    }

    @GetMapping("/board_filter_page{category}{filter}")
    public Page<Entity> getBoardCategoryFilterPagingResult(
            @PathVariable("category") String category
            , @PathVariable("filter") String filter
            , Model model
    ) {
        System.out.println("보드 필터에서 넘어옴 -> 패스값 { " + category + " }");
        System.out.println("보드 필터에서 넘어옴 -> 패스값 { " + filter + " }");
        return null;
    }
}
