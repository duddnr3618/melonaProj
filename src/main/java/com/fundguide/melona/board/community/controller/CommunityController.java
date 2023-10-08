package com.fundguide.melona.board.community.controller;

import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.member.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    /* 게시글 리스트 페이지 */
    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Page <CommunityEntity> list = communityService.boardList(pageable);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4,1);
        int endPage = Math.min(nowPage + 5,list.getTotalPages()) ;
        if (customUserDetails == null) {
            return "redirect:/member/loginForm";
        } else {
            String memberName = customUserDetails.getMemberEntity().getMemberName();
            model.addAttribute("list", list);
            model.addAttribute("userInfo", memberName);
            model.addAttribute("nowPage",nowPage);
            model.addAttribute("startPage",startPage);
            model.addAttribute("endPage",endPage);

            return "board/community/list";
        }
    }

    /* 게시글 작성폼 */
    @GetMapping("/wrtieForm")
    public String writeForm(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return "member/loginForm";
        }
        String userName = customUserDetails.getMemberEntity().getMemberName(); 
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
