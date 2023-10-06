package com.fundguide.melona.board.community.controller;

import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.member.entity.MemberEntity;
import com.fundguide.melona.member.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
        public class CommunityController {
            private final CommunityService communityService;
            @GetMapping("/list")
            public String list (Model model , @PageableDefault(page=1)Pageable pageable) {

                return "board/community/list";
            }

            @GetMapping("/wrtieForm")
            public String writeForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
                MemberEntity memberEntity = customUserDetails.getMemberEntity();
                System.out.println("로그인한 사용자의 정보 : " + memberEntity);
                return "board/writeForm";
    }

}
