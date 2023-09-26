package com.fundguide.melona.board.leaderboard.controller;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.board.leaderboard.service.LeaderBoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/leaderBoard")
@RequiredArgsConstructor
public class LeaderBoardController {
    private final LeaderBoardService leaderBoardService;

    //게시글 작성폼
    @GetMapping("/writeForm")
    public String writeForm () {

        return "board/leader/writeForm";
    }


    // 게시글 작성 처리
    @PostMapping("/writePro")
    public String writepro(@ModelAttribute @Valid LeaderBoardDto leaderBoardDto) {
        System.out.println(">>>>>>>>>> leaderBoardDto : " + leaderBoardDto );
        leaderBoardService.save(leaderBoardDto);
        return "redirect:/leaderBoard/list";
    }

     // 게시글 리스트
    @GetMapping("/list")
    public String findAll (Model model) {
        List<LeaderBoardDto> leaderBoardDtoList = leaderBoardService.findAll();
        model.addAttribute("leaderboardList" , leaderBoardDtoList);
        return "board/leader/list";
    }



    // 게시글 상세보기
    @GetMapping("/{id}")
    public String findById (@PathVariable Long id , Model model) {
        leaderBoardService.updateHits(id);
        LeaderBoardDto leaderBoardDto =  leaderBoardService.findById(id);
        model.addAttribute("leaderBoard" , leaderBoardDto);
        return "/board/leader/detail";
    }

    // 게시글 수정폼
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id ,  Model model) {
        LeaderBoardDto leaderBoardDto = leaderBoardService.findById(id);
        model.addAttribute("leaderBoardUpdate" , leaderBoardDto);
       return "board/leader/update";
    }


    // 게시글 수정 처리
    @PostMapping("/update")
    public String update (@ModelAttribute @Valid LeaderBoardDto leaderBoardDto , Model model) {
       LeaderBoardDto board =  leaderBoardService.update(leaderBoardDto);
       model.addAttribute("board" , board);
       return "board/leader/detail";
    }

    // 게시글 삭제처리
    @GetMapping("/delete/{id}")
    public String delete (@PathVariable("id") Long id){
        leaderBoardService.delete(id);
        System.out.println(">>>>>>>>> id : " + id);
        return "redirect:/leaderBoard/list";
    }

    // 페이징 처리
    @GetMapping("/paging")
    public String paging (@PageableDefault(page = 1) Pageable pageable , Model model) {
        Page<LeaderBoardDto> leaderBoardList = leaderBoardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < leaderBoardList.getTotalPages()) ? startPage + blockLimit - 1 : leaderBoardList.getTotalPages();
        System.out.println(">>>>>: 1");

        model.addAttribute("leaderBoardList", leaderBoardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        System.out.println(">>>>>: 2");
        return "board/leader/paging";
    }




}
