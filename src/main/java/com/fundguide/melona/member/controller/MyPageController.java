package com.fundguide.melona.member.controller;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardService;
import com.fundguide.melona.board.like.repository.LikeRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


    @Controller
    @RequestMapping("/mypage")
    public class MyPageController {
        private final CommunityRepository communityRepository;
        private final LikeRepository likeRepository;

        @Autowired
        public MyPageController(CommunityRepository communityRepository, LikeRepository likeRepository) {
            this.communityRepository = communityRepository;
            this.likeRepository = likeRepository;
        }

        @GetMapping("/myPosts")
        public String myPosts(@RequestParam(defaultValue = "createdTime") String orderBy,
                              Model model, Pageable pageable) {
            Page<CommunityEntity> communityPage;

            if ("mostLiked".equals(orderBy)) {
                List<Long> mostLikedCommunityIds = likeRepository.findMostLikedCommunityIds();
                communityPage = communityRepository.findAllByIdInOrderByCreatedTimeDesc(mostLikedCommunityIds, pageable);
            } else {
                communityPage = communityRepository.findAllByIdInOrderByCreatedTimeDesc(pageable);
            }

            model.addAttribute("communityPage", communityPage);
            return "mypage/myPosts";
        }
    }

