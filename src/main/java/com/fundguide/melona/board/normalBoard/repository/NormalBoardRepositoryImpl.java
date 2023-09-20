package com.fundguide.melona.board.normalBoard.repository;


import com.fundguide.melona.board.dto.CommonBoardSearchDTO;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.concurrent.locks.Condition;

/*import static com.fundguide.melona.board.normalBoard.entity;*/
import static com.fundguide.melona.board.normalBoard.entity.QNormalBoardEntity.normalBoardEntity;

@Repository
@RequiredArgsConstructor
public class NormalBoardRepositoryImpl implements NormalBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private JPAQuery<NormalBoardEntity> boardEntityJPAQuery = null;
    private BooleanExpression booleanExpression = null;

   @Override
    public Page<NormalBoardEntity> searchViewBoard(Pageable pageable, CommonBoardSearchDTO searchDTO) {
     /*   String likeKeyword = "%" + searchDTO.searchKeyword().toLowerCase() + "%";
        if ("all".equals(searchDTO.searchOption()) || searchDTO.searchOption() == null) {
            booleanExpression =
                    normalBoardEntity.boardWriter.like(likeKeyword)
                            .or(normalBoardEntity.boardTitle.like(likeKeyword))

            ;
        } else if ("content".equals(searchDTO.searchOption())) {
            booleanExpression = normalBoardEntity.content.like(likeKeyword);
        } else if ("writeruser".equals(searchDTO.searchOption())) {
            booleanExpression = normalBoardEntity.writerUserId.like(likeKeyword);
        } else if ("") {
            booleanExpression = normalBoardEntity.
        }*/

        return null;
    }
}
