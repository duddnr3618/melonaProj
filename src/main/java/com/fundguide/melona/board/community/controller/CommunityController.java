package com.fundguide.melona.board.community.controller;

import com.fundguide.melona.board.common.dto.ImpeachDTO;
import com.fundguide.melona.board.community.dto.CommentDto;
import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.community.repository.like.CommunityLikeRepository;
import com.fundguide.melona.board.community.service.CommentService;
import com.fundguide.melona.board.community.service.CommunityService;
import com.fundguide.melona.member.repository.MemberRepository;
import com.fundguide.melona.member.repository.MemberRepositoryData;
import com.fundguide.melona.member.service.CustomUserDetails;
import com.fundguide.melona.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.prefs.PreferencesFactory;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;
    private final CommentService commentService;
    /* 게시글 리스트 페이지 */
    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                       String searchKeyword) {


        Page<CommunityEntity> list = null;
        if (searchKeyword == null) {
            list = communityService.boardList(pageable);
        } else {
            list = communityService.searchList(searchKeyword, pageable);

        }
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
            model.addAttribute("list", list);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "board/community/list";

    }

    /* 게시글 작성폼 */
    @GetMapping("/wrtieForm")
    public String writeForm(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return "redirect:/member/loginForm";
        }else {
            String userName = customUserDetails.getMemberEntity().getMemberName();
            Long memberId = customUserDetails.getMemberEntity().getId();
            System.out.println(">>>>>>>> : " + customUserDetails.getMemberEntity().getId());
            String url = "/community/writePro";
            model.addAttribute("userName", userName);
            model.addAttribute("memberId", memberId);
            model.addAttribute("url" ,url);
            return "board/writeForm";
        }
    }

    /* 게시글 작성처리 */
    @PostMapping("/writePro")
    public String writePro(@ModelAttribute CommunityDto communityDto, Model model, MultipartFile file,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        if(customUserDetails == null){

            return "redirect:/member/loginForm";
        }
        System.out.println(">>>>>>>>>> " + communityDto);
        communityService.writePro(communityDto, file);
        model.addAttribute("message", "글작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/community/list");
        System.out.println("작성후? { " + model.getAttribute("searchUrl") + " }");
        return "board/message";
    }

    /* 게시글 상세보기 */
    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        communityService.updateHits(id);
        CommunityDto communityDto = communityService.boardDetail(id);
        List<CommentDto> commentDtoList = commentService.findAll(id);

            // 댓글 목록 가져오기
            model.addAttribute("board", communityDto);
            model.addAttribute("commentList", commentDtoList);
        if(customUserDetails == null) {
            return "board/community/detail";
        }else {
            Long userId = customUserDetails.getMemberEntity().getId();
            String userName = customUserDetails.getMemberEntity().getMemberName();
            model.addAttribute("userId" , userId);
            model.addAttribute("userName", userName);
            System.out.println(" >>>>>>>>>> !! : " + userName);
            return "board/community/detail";
        }

    }

    /* 게시글 수정 폼 */
    @GetMapping("/modifyForm/{id}")
    public String modifyForm(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        if(customUserDetails== null){
            return "redirect:/member/loginForm";
        }
        CommunityDto communityDto = communityService.boardDetail(id);
        model.addAttribute("board", communityDto);
        String userName = customUserDetails.getMemberEntity().getMemberName();
        Long userId = customUserDetails.getMemberEntity().getId();
        String url = "/community/modifyPro";
        model.addAttribute("url", url);
        model.addAttribute("userName" , userName);
        model.addAttribute("userId" , userId);
        System.out.println("1>>>>>>>>>>>>>> : " + communityDto );


        return "board/community/modify";
    }

    /* 게시글 수정 처리 */
    @PostMapping("/modifyPro")
    public String modifyPro(@ModelAttribute CommunityDto communityDto, Model model, MultipartFile file,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam Long id) {
        if(customUserDetails == null){
            return "redirect:/member/loginForm";
        }
        CommunityDto communityboard = communityService.boardDetail(id);
        if(communityboard.getId() == customUserDetails.getMemberEntity().getId() ){
            return "redirect:/community/" + id ;
        }
        System.out.println(">>>>>>>>>> 3 : "  + communityDto);
        CommunityDto board = communityService.update(communityDto, file);
        model.addAttribute("board", board);
        return "redirect:/community/list";
    }

    /* 게시글 삭제 */
    @GetMapping("/delete")
    public String delete(Long id , @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return "redirect:/member/loginForm";
        }
        communityService.delete(id);

        return "redirect:/community/list";
    }

    /*---------------------------------------------------------------------------------------------*/
    /** TODO 오류 있음 수정할것 (중복된값 들어감)
     * 신고 컨트롤 메서드*/
    @PostMapping("/impeach")
    public ResponseEntity<String> impeach(Principal principal, @RequestBody ImpeachDTO impeachDTO) {
        return communityService.impeach(principal, impeachDTO);
    }

    /**좋아요 컨트롤 메서드*/
    @PutMapping("/like/{boardId}")
    public ResponseEntity<String> likeAdd(Principal principal, @PathVariable(name = "boardId") Long boardId) {
        return communityService.likeAdd(principal, boardId);
    }
    
    /**좋아요 취소 컨트롤 메서드*/
    @DeleteMapping("/like/remove/{boardId}")
    public ResponseEntity<String> likeRemove(Principal principal, @PathVariable(name = "boardId") Long boardId) {
        System.out.println(" { 좋아요 삭제 진입" + " }");
        return communityService.likeRemove(principal, boardId);
    }
}
