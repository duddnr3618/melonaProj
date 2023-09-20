package com.fundguide.melona.board.normalBoard.controller;

import com.fundguide.melona.board.dto.CommonBoardSearchDTO;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.service.NormalBoardQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequiredArgsConstructor
@Log4j2
@RequestMapping("/normalboard")
public class NormalBoardController {
    private final NormalBoardQueryService queryService;
    private Page<NormalBoardEntity> pagingNormalBoard = null;


    /**노말보드 메인 리스트*/
    @GetMapping("")
    public String boardList(Model model) {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 15, sort);
        pagingNormalBoard = queryService.onlyViewNormalBoard(pageable);
        model.addAttribute("pagingBoard", pagingNormalBoard);
        return "normalboard";
    }

    @GetMapping("/search")
    public void boardSearchList(Model model, @ModelAttribute CommonBoardSearchDTO boardSearchDTO) {

    }
}
