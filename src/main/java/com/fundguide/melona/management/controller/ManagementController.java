package com.fundguide.melona.management.controller;

import com.fundguide.melona.board.normalBoard.service.NormalBoardCommandService;
import com.fundguide.melona.board.normalBoard.service.NormalBoardQueryService;
import com.fundguide.melona.management.service.ManagementService;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.role.MemberLimitState;
import com.fundguide.melona.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.zip.DataFormatException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {
    private final MemberService memberService;
    private final NormalBoardCommandService normalBoardCommandService;
    private final NormalBoardQueryService normalBoardQueryService;
    private final ManagementService managementService;

    @GetMapping
    public String mainManagementView() {
        return "management/management";
    }

    @GetMapping("/board_filter_page")
    public Page<Entity> getBoardCategoryFilterPagingResult(
            @RequestParam("category") String category
            , @RequestParam("filter") String filter
            , Model model
    ) {
        return null;
    }

    @GetMapping("/member_filter_page")
    @ResponseBody
    public Page<MemberEntity> getMemberLimitStatePagingResult(
            @RequestParam("filter") String filter
            , Model model
            ) throws DataFormatException {

        Sort sort = Sort.by("memberJoinData").descending();
        Pageable pageable = PageRequest.of(0,20, sort);

        if (filter.equals("all")) {
            return memberService.getMemberPage(pageable);
        } else {
            return managementService.getMemberLimitStatePaging(pageable, filter);
        }
    }

    @GetMapping("/member_role_filter_page{filter}")
    public Page<MemberEntity> getMemberRolePagingResult(
            @PathVariable("filter") String role
    ) {
        return null;
    }
}
