package com.fundguide.melona.board.normalBoard.service;

import com.fundguide.melona.board.common.dto.BoardSearchDTO;
import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service @RequiredArgsConstructor @Transactional(readOnly = true)
public class NormalBoardQueryService {
    private final NormalBoardRepository normalBoardRepository;

    /**
     * 노말 보드 모든 페이징 결과값 반환
     */
    public Page<NormalBoardEntity> onlyViewPageNormalBoard(Pageable pageable) {
        return normalBoardRepository.onlyViewNormalBoard(pageable);
    }

    /**
     * 검색값으로 검색하는 메서드
     *
     * @return 검색 페이징 결과값 반환
     */
    public Page<NormalBoardEntity> onlyViewPageNormalBoard(Pageable pageable, Model model, BoardSearchDTO searchDTO) {
        model.addAttribute("searchKeyword", searchDTO.searchKeyword());
        model.addAttribute("searchOption", searchDTO.searchOption());
        return normalBoardRepository.searchViewBoard(pageable, searchDTO);
    }

    /**
     * 노말 보드 디테일값 반환
     */
    public NormalBoardEntity onlyViewDetailNormalBoard(Long boardId) {
        return normalBoardRepository.findAllById(boardId);
    }


    public Page<NormalBoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageLimit = 10;
        Page<NormalBoardEntity> normalBoardEntities = normalBoardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        return normalBoardEntities.map(o -> new NormalBoardDto(
                o.getId(), o.getBoardTitle(), o.getBoardContents(), o.getBoardHits(),
                o.getCreatedTime(), o.getUpdatedTime(), o.getMemberEntity().getId(),
                o.getMemberEntity().getMemberName(), o.getFileName(), o.getFilePath()
        ));
    }

}