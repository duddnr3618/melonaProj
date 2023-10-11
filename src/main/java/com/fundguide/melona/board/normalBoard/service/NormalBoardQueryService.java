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

import java.security.Key;
import java.util.Map;
import java.util.Objects;

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

    public NormalBoardDto onlyViewDetailNormalBoardDTO(Long id) {
        return normalBoardRepository.detailNormalBoard(id);
    }

    public Page<NormalBoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageLimit = 10;
        Page<NormalBoardEntity> normalBoardEntities = normalBoardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("normalBoardEntities.getContent() = " + normalBoardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("normalBoardEntities.getTotalElements() = " + normalBoardEntities.getTotalElements()); // 전체 글 갯수
        System.out.println("normalBoardEntities.getNumber() = " + normalBoardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("normalBoardEntities.getTotalPages() = " + normalBoardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("normalBoardEntities.getSize() = " + normalBoardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("normalBoardEntities.hasPrevious() = " + normalBoardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("normalBoardEntities.isFirst() = " + normalBoardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("normalBoardEntities.isLast() = " + normalBoardEntities.isLast()); // 마지막 페이지 여부

        Page<NormalBoardDto> normalBoardDtos = normalBoardEntities.map(normalBoardEntity -> new NormalBoardDto(
                normalBoardEntity.getId(), normalBoardEntity.getBoardWriter(), normalBoardEntity.getBoardTitle(), normalBoardEntity.getBoardHits(), normalBoardEntity.getCreatedTime()
        ));
        return normalBoardDtos;
    }

}