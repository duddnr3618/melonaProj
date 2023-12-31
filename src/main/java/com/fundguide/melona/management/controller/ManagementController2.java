package com.fundguide.melona.management.controller;

import com.fundguide.melona.management.service.ManagementService;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController2 {
    private final MemberService memberService;
    private final ManagementService managementService;

    Sort sort_Member = Sort.by("memberJoinData").descending();
    Pageable pageable_Member = PageRequest.of(0, 20, sort_Member);
    Sort sort_Board = Sort.by("createdTime").descending();
    Pageable pageable_Board = PageRequest.of(0, 20, sort_Board);

    /** 관리자 메인 페이지 */
    @GetMapping
    public String mainManagementView() {
        return "management/server_rendering_version/management2";
    }

    /** 각 보드의 필터마다 결과를 보여주는 컨트롤 메서드 (신고 관련) */
    @GetMapping("/board_filter_page")
    public String getBoardCategoryFilterPagingResult(@RequestParam("category") String category
            , @RequestParam("filter") String filter
            , Model model) throws IllegalAccessException {
        Page<?> paging = managementService.getBoardCategoryFilterPaging(category, filter, pageable_Board);
        model.addAttribute("boardPaging", paging);
        model.addAttribute("filter", filter);

        String detailHtmlLink;
        switch (category) {
            case "normal" -> detailHtmlLink = "/normalBoard/";
            case "leader" -> detailHtmlLink = "/leaderBoard/";
            case "community" -> detailHtmlLink = "/community/";
            default -> throw new IllegalAccessException("지정되지 않은 게시판 분류입니다.");
        }
        model.addAttribute("detailBoardLink", detailHtmlLink);
        return "management/server_rendering_version/board_rendering";
    }

    /** 게시물 비활성화를 위한 컨트롤 메서드 */
    @PutMapping("/board_disabled")
    public ResponseEntity<String> disabledBoard(@RequestParam("category") String category
            , @RequestParam("id") Long id) throws IllegalAccessException {
        return managementService.modifyDisableBoard(category, id);
    }

    /** 설정된 게시물의 경고수에 따른 멤버 필터링을 보여주는 컨트롤 메서드 */
    @GetMapping("/member_filter_page")
    public String getFilteredResultsByRule(@RequestParam("filter") String filter
            , Model model) {
        Page<MemberEntity> paging = managementService.getMemberEvaluatePendingByRule(filter, pageable_Member);
        model.addAttribute("filter", filter);
        model.addAttribute("memberPaging", paging);
        return "management/server_rendering_version/member_rendering";
    }

    @PutMapping("/member_filter_setLimit/{memberId}/{day}")
    public ResponseEntity<String> setMemberAsLimit(
            @PathVariable("memberId") Long memberId,
            @PathVariable("day") int day
            ) {
        return managementService.setMemberAsLimit(memberId, day);
    }

    @PutMapping("/member_filter_setDisable/{memberId}")
    public ResponseEntity<String> setMemberDisable(@PathVariable Long memberId) {
        return managementService.setMemberDisable(memberId);
    }

    @GetMapping("/member_role_filter_page")
    public String getMemberRolePagingResult(
            @RequestParam("filter") String filter, Model model) {
        Page<MemberEntity> paging = managementService.getMemberAuthorityByRule(filter, pageable_Member);
        model.addAttribute("filter", filter);
        model.addAttribute("memberPaging", paging);
        return "management/server_rendering_version/member_role_rendering";
    }

    @PutMapping("/member_role_setLeader/{memberId}")
    public ResponseEntity<String> setMemberAsLeader(@PathVariable Long memberId) {
        return managementService.setMemberAsLeader(memberId);
    }
}
