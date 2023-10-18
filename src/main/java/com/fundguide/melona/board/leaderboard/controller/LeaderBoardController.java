package com.fundguide.melona.board.leaderboard.controller;

import com.fundguide.melona.board.common.dto.ImpeachDTO;
import com.fundguide.melona.board.leaderboard.dto.CommentLeaderBoardDto;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.service.CommentLeaderBoardService;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardService;
import com.fundguide.melona.member.role.MemberRoleState;
import com.fundguide.melona.member.service.CustomUserDetails;
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

@Controller
@RequestMapping("/leaderBoard")
@RequiredArgsConstructor
public class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;
    private final CommentLeaderBoardService commentLeaderBoardService;

    /* 게시글 리스트 페이지 */
    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                       String searchKeyword, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return "redirect:/member/loginForm";
        }
        Page<LeaderBoardEntity> list = null;
        if (searchKeyword == null) {
            list = leaderBoardService.boardList(pageable);
        } else {
            list = leaderBoardService.searchList(searchKeyword, pageable);

        }
        String userName = customUserDetails.getMemberEntity().getMemberName();
        MemberRoleState userRole= customUserDetails.getMemberEntity().getMemberRole();
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("userName", userName);
        model.addAttribute("userRole", userRole);
        return "board/leader/list";

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
            String url = "/leaderBoard/writePro";
            model.addAttribute("userName", userName);
            model.addAttribute("memberId", memberId);
            model.addAttribute("url", url);
            return "board/writeForm";
        }
    }

    /* 게시글 작성처리 */
    @PostMapping("/writePro")
    public String writePro(@ModelAttribute LeaderBoardDto leaderBoardDto, Model model, MultipartFile file,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        if(customUserDetails == null){

            return "redirect:/member/loginForm";
        }
        System.out.println(">>>>>>>>>> " + leaderBoardDto);
        leaderBoardService.writePro(leaderBoardDto, file);
        model.addAttribute("message", "글작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/leaderBoard/list");
        System.out.println("작성후? { " + model.getAttribute("searchUrl") + " }");
        return "board/message";
    }

    /* 게시글 상세보기 */
    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        leaderBoardService.updateHits(id);
        LeaderBoardDto leaderBoardDto = leaderBoardService.boardDetail(id);
        List<CommentLeaderBoardDto> commentLeaderBoardDtoList = commentLeaderBoardService.findAll(id);

        // 댓글 목록 가져오기
        model.addAttribute("board", leaderBoardDto);
        model.addAttribute("commentList", commentLeaderBoardDtoList);
        if(customUserDetails == null) {
            return "board/leader/detail";
        }else {
            String userName = customUserDetails.getMemberEntity().getMemberName();
            Long userId = customUserDetails.getMemberEntity().getId();
            model.addAttribute("userId" , userId);
            model.addAttribute("userName" , userName);
            return "board/leader/detail";
        }

    }

    /* 게시글 수정 폼 */
    @GetMapping("/modifyForm/{id}")
    public String modifyForm(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        if(customUserDetails== null){
            return "redirect:/member/loginForm";
        }
        LeaderBoardDto leaderBoardDto = leaderBoardService.boardDetail(id);
        model.addAttribute("board", leaderBoardDto);
        String userName = customUserDetails.getMemberEntity().getMemberName();
        Long userId = customUserDetails.getMemberEntity().getId();
        String url = "/leaderBoard/modifyPro";
        model.addAttribute("url", url);
        model.addAttribute("userName" , userName);
        model.addAttribute("userId" , userId);
        System.out.println("1>>>>>>>>>>>>>> : " + leaderBoardDto );

        return "board/leader/modify";
    }

    /* 게시글 수정 처리 */
    @PostMapping("/modifyPro")
    public String modifyPro(@ModelAttribute LeaderBoardDto leaderBoardDto, Model model, MultipartFile file,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam Long id) {
        if(customUserDetails == null){
            return "redirect:/member/loginForm";
        }
        LeaderBoardDto leaderBoard = leaderBoardService.boardDetail(id);
        if(leaderBoard.getId() == customUserDetails.getMemberEntity().getId() ){
            return "redirect:/leaderBoard/" + id ;
        }
        System.out.println(">>>>>>>>>> 3 : "  + leaderBoardDto);
        LeaderBoardDto board = leaderBoardService.update(leaderBoardDto, file);
        model.addAttribute("board", board);
        return "redirect:/leaderBoard/list";
    }

    /* 게시글 삭제 */
    @GetMapping("/delete")
    public String delete(Long id , @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return "redirect:/leaderBoard/list";
        }
        leaderBoardService.delete(id);

        return "redirect:/leaderBoard/list";
    }

    /*---------------------------------------------------------------------------------------------*/
    /** TODO 오류 있음 수정할것 (중복된값 들어감)
     * 신고 컨트롤 메서드*/

    @PostMapping("/impeach")
    public ResponseEntity<String> impeach(Principal principal, @RequestBody ImpeachDTO impeachDTO) {
        System.out.println(" { 리더 보드 신고 컨트롤 진입" + " }");
        return leaderBoardService.impeach(principal, impeachDTO);
    }

    /**좋아요 컨트롤 메서드*/

    @PutMapping("/like/{boardId}")
    public ResponseEntity<String> likeAdd(Principal principal, @PathVariable(name = "boardId") Long boardId) {
        return leaderBoardService.likeAdd(principal, boardId);
    }


    /**좋아요 취소 컨트롤 메서드*/

    @DeleteMapping("/like/remove/{boardId}")
    public ResponseEntity<String> likeRemove(Principal principal, @PathVariable(name = "boardId") Long boardId) {
        System.out.println(" { 좋아요 삭제 진입" + " }");
        return leaderBoardService.likeRemove(principal, boardId);
    }
}
