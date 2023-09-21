package com.fundguide.melona.board.normalBoard.service;

import com.fundguide.melona.board.common.dto.BoardSearchDTO;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service @RequiredArgsConstructor @Transactional(readOnly = true)
public class NormalBoardQueryService {
    private final NormalBoardRepository normalBoardRepository;

    /**노말 보드 모든 페이징 결과값 반환*/
    public Page<NormalBoardEntity> onlyViewPageNormalBoard(Pageable pageable) {
        return normalBoardRepository.onlyViewNormalBoard(pageable);
    }

    /**검색값으로 검색하는 메서드
     * @return 검색 페이징 결과값 반환*/
    public Page<NormalBoardEntity> onlyViewPageNormalBoard(Pageable pageable, Model model, BoardSearchDTO searchDTO) {
        model.addAttribute("searchKeyword", searchDTO.searchKeyword());
        model.addAttribute("searchOption", searchDTO.searchOption());
        model.addAttribute("searchPageValue", searchDTO.searchPageValue());
        return normalBoardRepository.searchViewBoard(pageable, searchDTO);
    }

    /**노말 보드 디테일값 반환*/
    public NormalBoardEntity onlyViewDetailNormalBoard(Long boardId) {
        return normalBoardRepository.findAllByBoardId(boardId);
    }


    private void addSearchDataModel() {

    }
}
