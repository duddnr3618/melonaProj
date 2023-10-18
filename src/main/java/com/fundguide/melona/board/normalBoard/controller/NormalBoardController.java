package com.fundguide.melona.board.normalBoard.controller;

import com.fundguide.melona.board.common.dto.ImpeachDTO;
import com.fundguide.melona.board.normalBoard.dto.CommentNormalBoardDto;
import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.service.CommentNormalBoardService;
import com.fundguide.melona.board.normalBoard.service.NormalBoardService;
import com.fundguide.melona.member.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

@Controller @RequiredArgsConstructor
@Log4j2
@RequestMapping("/normalBoard")
public class NormalBoardController {
    private final NormalBoardService normalBoardService;
    private final CommentNormalBoardService commentNormalBoardService;

    /* 게시글 리스트 페이지 */
    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable,
                       String searchKeyword) {
        Page<NormalBoardEntity> list = null;
        if (searchKeyword == null) {
            list = normalBoardService.boardList(pageable);
        } else {
            list = normalBoardService.searchList(searchKeyword, pageable);

        }
        System.out.println("1 >>>>>>>>>>> : " + list);
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/normal/list";

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
            String url = "/normalBoard/writePro";
            System.out.println("1 >>>>>>>>>>>> : " + url);
            model.addAttribute("userName", userName);
            model.addAttribute("memberId", memberId);
            model.addAttribute("url" , url);
            return "board/writeForm";
        }
    }

    /* 게시글 작성처리 */
    @PostMapping("/writePro")
    public String writePro(@ModelAttribute NormalBoardDto normalBoardDto, Model model, MultipartFile file,
                           @AuthenticationPrincipal CustomUserDetails customUserDetails) throws Exception {
        if(customUserDetails == null){

            return "redirect:/member/loginForm";
        }
        System.out.println("1 >>>>>>>>>> " + normalBoardDto);
        normalBoardService.writePro(normalBoardDto, file);
        model.addAttribute("message", "글작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/normalBoard/list");
        System.out.println("작성후? { " + model.getAttribute("searchUrl") + " }");
        return "board/message";
    }

    /* 게시글 상세보기 */
    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        normalBoardService.updateHits(id);
        NormalBoardDto normalBoardDto = normalBoardService.boardDetail(id);
        List<CommentNormalBoardDto> commentnormalBoardDtoList = commentNormalBoardService.findAll(id);

        // 댓글 목록 가져오기
        model.addAttribute("board", normalBoardDto);
        model.addAttribute("commentList", commentnormalBoardDtoList);
        if(customUserDetails == null) {
            return "board/normal/detail";
        }else {
            Long userId = customUserDetails.getMemberEntity().getId();
            String userName = customUserDetails.getMemberEntity().getMemberName();
            model.addAttribute("userId" , userId);
            model.addAttribute("userName" , userName);
            return "board/normal/detail";
        }

    }

    /* 게시글 수정 폼 */
    @GetMapping("/modifyForm/{id}")
    public String modifyForm(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails ) {
        if(customUserDetails== null){
            return "redirect:/member/loginForm";
        }

        NormalBoardDto normalBoardDto = normalBoardService.boardDetail(id);
        model.addAttribute("board", normalBoardDto);
        String userName = customUserDetails.getMemberEntity().getMemberName();
        Long userId = customUserDetails.getMemberEntity().getId();
        String url = "/normalBoard/modifyPro";
        model.addAttribute("url", url);
        model.addAttribute("userName" , userName);
        model.addAttribute("userId" , userId);
        System.out.println("1>>>>>>>>>>>>>> : " + normalBoardDto );

        return "board/normal/modify";
    }

    /* 게시글 수정 처리 */
    @PostMapping("/modifyPro")
    public String modifyPro(@ModelAttribute NormalBoardDto normalBoardDto, Model model, MultipartFile file,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam Long id) {
        System.out.printf("1 >>>>>>>>>> : " + normalBoardDto);
        if(customUserDetails == null){
            return "redirect:/member/loginForm";
        }
        NormalBoardDto normalBoard = normalBoardService.boardDetail(id);
        if(normalBoard.getId() == customUserDetails.getMemberEntity().getId()){
            System.out.println("### >>>>>>>> : " + normalBoard);
            return "redirect:/normalBoard/" + id ;
        }
        System.out.println(">>>>>>>>>> 3 : "  + normalBoardDto);
        normalBoardService.update(normalBoardDto, file);
        return "redirect:/normalBoard/list";
    }

    /* 게시글 삭제 */
    @GetMapping("/delete")
    public String delete(Long id , @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if(customUserDetails == null) {
            return "redirect:/normalBoard/list";
        }
        normalBoardService.delete(id);

        return "redirect:/normalBoard/list";
    }

    /*---------------------------------------------------------------------------------------------*/
    /** TODO 오류 있음 수정할것 (중복된값 들어감)
     * 신고 컨트롤 메서드*/

    @PostMapping("/impeach")
    public ResponseEntity<String> impeach(Principal principal, @RequestBody ImpeachDTO impeachDTO) {
        return normalBoardService.impeach(principal, impeachDTO);
    }

    /**좋아요 컨트롤 메서드*/

    @PutMapping("/like/{boardId}")
    public ResponseEntity<String> likeAdd(Principal principal, @PathVariable(name = "boardId") Long boardId) {
        return normalBoardService.likeAdd(principal, boardId);
    }


    /**좋아요 취소 컨트롤 메서드*/

    @DeleteMapping("/like/remove/{boardId}")
    public ResponseEntity<String> likeRemove(Principal principal, @PathVariable(name = "boardId") Long boardId) {
        System.out.println(" { 좋아요 삭제 진입" + " }");
        return normalBoardService.likeRemove(principal, boardId);
    }


}
