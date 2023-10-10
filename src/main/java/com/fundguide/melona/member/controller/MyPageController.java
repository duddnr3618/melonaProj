package com.fundguide.melona.member.controller;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.service.NormalBoardQueryService;
import com.fundguide.melona.member.service.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.data.domain.Pageable;

@Controller
    @RequestMapping("/mypage")
    public class MyPageController {
        private final NormalBoardQueryService normalBoardQueryService;
        private final CommunityService communityService;
        public MyPageController(NormalBoardQueryService normalBoardQueryService ,CommunityService communityService) {
            this.normalBoardQueryService = normalBoardQueryService;
            this.communityService = communityService;
        }

        // ...

        @GetMapping("/normalboard")
        public String myPageNormalBoard(Model model) {
            // 최신순 정렬을 위한 Pageable 생성
            Pageable pageable = PageRequest.of(0, 15, Sort.by(Sort.Order.desc("createdTime")));

            // 내가 작성한 NormalBoard 게시글 조회
            Page<NormalBoardEntity> pagingNormalBoard = normalBoardQueryService.onlyViewPageNormalBoard(pageable);

            model.addAttribute("pagingBoard", pagingNormalBoard);

            return "mypage/normal_board"; //
        }
    /* 내가 작성한 CommunityBoard 게시글 목록 페이지 */
    @GetMapping("/myCommunityBoard")
    public String myCommunityBoard(Model model, @PageableDefault(page = 1) Pageable pageable,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return "member/loginForm"; // 로그인되어 있지 않다면 로그인 페이지로 리다이렉트
        }

        String userName = customUserDetails.getMemberEntity().getMemberName();
        model.addAttribute("userName", userName);

        Page<CommunityEntity> pagingCommunityBoard = communityService.getLatestCommunityBoard(pageable);
        model.addAttribute("pagingCommunityBoard", pagingCommunityBoard);

        return "mypage/myCommunityBoard";
    }

}


