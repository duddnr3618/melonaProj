package com.fundguide.melona.member.controller;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardService;
import com.fundguide.melona.member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


    @Controller
    @RequiredArgsConstructor
    @RequestMapping("/mypage")
    public class MyPageController {
        private final CommunityService communityService;
        private final LeaderBoardService leaderBoardService;
        @GetMapping("/community-posts")
        public String myCommunityPosts(Model model, @AuthenticationPrincipal MemberEntity memberEntity,
                                       @RequestParam(name = "orderBy", defaultValue = "latest") String orderBy) {
            Long memberId = memberEntity.getId();
            List<CommunityDto> posts;

            if ("likes".equals(orderBy)) {
                posts = communityService.getMyPagePostsOrderByLikes(memberId);
            } else {
                posts = communityService.getMyPagePostsOrderByCreatedAt(memberId);
            }

            model.addAttribute("posts", posts);
            return "mypage/community-posts";

        }
        @GetMapping("/leaderboard-posts")
        public String myLeaderBoardPosts(Model model, @AuthenticationPrincipal MemberEntity memberEntity,
                                         @RequestParam(name = "orderBy", defaultValue = "hits") String orderBy) {
            Long memberId = memberEntity.getId();
            List<LeaderBoardDto> posts;

            if ("hits".equals(orderBy)) {
                posts = leaderBoardService.getMyPagePostsOrderByHits(memberId);
            } else {
                posts = leaderBoardService.getMyPagePostsOrderByCreatedAt(memberId);
            }

            model.addAttribute("leaderboardPosts", posts);
            return "mypage/leaderboard-posts";
        }


    }

