package com.fundguide.melona.member.controller;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardService;
import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.service.NormalBoardQueryService;
import com.fundguide.melona.member.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
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
        private final LeaderBoardService leaderBoardService;


        public MyPageController(NormalBoardQueryService normalBoardQueryService ,CommunityService communityService , LeaderBoardService leaderBoardService) {
            this.normalBoardQueryService = normalBoardQueryService;
            this.communityService = communityService;
            this.leaderBoardService = leaderBoardService;
        }

        // ...

    @GetMapping("mypage/normal_board")
    public String viewNormalBoard(Model model, Pageable pageable, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return "member/loginForm";
        }

        int pageLimit = 10; // 페이지당 보여질 게시물 수
        // 게시판 페이징 처리
        Page<NormalBoardDto> normalBoardPage = normalBoardQueryService.paging(pageable);

        // 게시글 목록을 모델에 추가
        model.addAttribute("normalBoardPage", normalBoardPage);

        return "mypage/normal_board";   // mypage/normal-Board에 대한 뷰를 리턴
    }
    /* 내가 작성한 CommunityBoard 게시글 목록 페이지 */
    @GetMapping("mypage/community-board")
    public String myCommunity(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails, Pageable pageable) {
        if (customUserDetails == null) {
            return "member/loginForm";
        }

        int pageLimit = 10; // 페이지당 보여질 게시물 수
        // 게시판 페이징 처리
        Page<CommunityDto> communityPage = communityService.paging(pageable);
        // 게시글 목록을 모델에 추가
        model.addAttribute("communityPage", communityPage);

        return "mypage/community-board"; // mypage/communityBoard에 대한 뷰를 리턴
    }
    @GetMapping("mypage/myLeaderBoard")
    public String myLeaderBoardPage(@PageableDefault(page = 0, size = 10, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<LeaderBoardDto> leaderBoardPage = leaderBoardService.paging(pageable);
        model.addAttribute("leaderBoardPage", leaderBoardPage);
        return "mypage/myLeaderBoard"; // 마이페이지 내에서 보여질 뷰 이름
    }
    @GetMapping("mypage/mywrittenBoard")
    public String myWrittenArticles() {
        return "mypage/mywrittenBoard";
    }

}


