package com.fundguide.melona.board.normalBoard.repository.like;


import com.fundguide.melona.board.common.querydsl_repeatcode.BoardQueryDsl_RepeatCode;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.normalBoard.entity.NormalBoard_like;
import com.fundguide.melona.management.commonQueryDsl.CommonQueryDsl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import static com.fundguide.melona.board.normalBoard.entity.QNormalBoard_like.normalBoard_like;

@Repository
@RequiredArgsConstructor
public class NormalBoardLikeRepositoryImpl implements NormalBoardLikeCustom {

    private final JPAQueryFactory queryFactory;
    private final BoardQueryDsl_RepeatCode dslRepeatCode;
    private JPAQuery<Community_like> boardEntityJPAQuery = null;
    private final CommonQueryDsl commonQueryDsl = new CommonQueryDsl();
    private BooleanExpression booleanExpression = null;

    @Override
    public boolean checkAlreadyLike(NormalBoard_like like) {
        booleanExpression = likeConsistency(like);
        NormalBoard_like like1 = queryFactory.selectFrom(normalBoard_like).where(booleanExpression).fetchOne();
        return like1 != null;
    }

    /** {@inheritDoc} */
    @Override
    public NormalBoard_like searchAlreadyLike(NormalBoard_like like) {
        booleanExpression = likeConsistency(like);
        return queryFactory.selectFrom(normalBoard_like)
                .where(booleanExpression)
                .fetchOne();
    }

    /** {@inheritDoc} */
    @Override
    public void removeLike(NormalBoard_like like) {
        NormalBoard_like normalBoardLike = searchAlreadyLike(like);
        boolean check = normalBoardLike != null;

        try {
            if (check) {
                booleanExpression = likeConsistency(like);
                queryFactory.delete(normalBoard_like)
                        .where(booleanExpression)
                        .execute();
            }
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("존재하지 않는 좋아요 입니다.");
        }
    }

    /**테이블에 기록된것의 정합성을 맞추기 위한 메서드*/
    protected BooleanExpression likeConsistency(NormalBoard_like like) {
        return normalBoard_like.normalBoardEntity.id.eq(like.getNormalBoardEntity().getId())
                .and(normalBoard_like.memberEntity.id.eq(like.getMemberEntity().getId()));
    }
}
