package com.fundguide.melona.management.controller;

import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
import com.fundguide.melona.board.normalBoard.service.NormalBoardCommandService;
import com.fundguide.melona.board.normalBoard.service.NormalBoardQueryService;
import com.fundguide.melona.management.dto.MemberRoleFilterDTO;
import com.fundguide.melona.management.service.ManagementService;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {
    private final MemberService memberService;
    private final NormalBoardCommandService normalBoardCommandService;
    private final NormalBoardQueryService normalBoardQueryService;
    private final ManagementService managementService;

    Sort sort_Member = Sort.by("memberJoinData").descending();
    Pageable pageable_Member = PageRequest.of(0, 20, sort_Member);
    Sort sort_Board = Sort.by("createdTime").descending();
    Pageable pageable_Board = PageRequest.of(0, 20, sort_Board);

    @GetMapping
    public String mainManagementView() {
        return "management/management";
    }

    @GetMapping("/board_filter_page")
    @ResponseBody
    public Page<?> getBoardCategoryFilterPagingResult(
            @RequestParam("category") String category
            , @RequestParam("filter") String filter) throws IllegalAccessException {
        return managementService.getBoardCategoryFilterPaging(category, filter, pageable_Board);
    }

    @GetMapping("/member_filter_page")
    @ResponseBody
    public Page<MemberEntity> getMemberLimitStatePagingResult(
            @RequestParam("filter") String filter) {

        if (filter.equals("all")) {
            return memberService.getMemberPage(pageable_Member);
        } else {
            return managementService.getMemberLimitStatePaging(filter, pageable_Member);
        }
    }

    @GetMapping("member_filter_page/detail")
    public NormalBoardDto getMemberFilterDetail(@RequestParam("id") Long id) {
        return normalBoardQueryService.onlyViewDetailNormalBoardDTO(id);
    }

    @GetMapping("/member_role_filter_page")
    @ResponseBody
    public Page<MemberRoleFilterDTO> getMemberRolePagingResult(
            @RequestParam("filter") String filter) {
        return managementService.getMemberRoleStatePaging(filter, pageable_Member);
    }
}
