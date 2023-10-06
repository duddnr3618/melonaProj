package com.fundguide.melona.board.community.controller;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.member.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    /* 게시글 리스트 페이지 */
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(page = 1) Pageable pageable) {

        return "board/community/list";
    }

    /* 게시글 작성폼 */
    @GetMapping("/wrtieForm")
    public String writeForm(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return "member/loginForm";
        }
        String userName = customUserDetails.getMemberEntity().getMemberName();  // 이메일  getName1();
        model.addAttribute("userName", userName);
      return "board/writeForm";
    }

    /* 게시글 수정폼 */
    @PostMapping("/writePro")
    public String writePro(@ModelAttribute CommunityDto communityDto) {
        System.out.println(" >>>>>>>> communityDto : " + communityDto);
        communityService.writePro(communityDto);
        return "index";
    }

}
