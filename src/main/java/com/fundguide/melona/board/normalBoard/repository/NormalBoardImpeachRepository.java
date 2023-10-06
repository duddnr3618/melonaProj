package com.fundguide.melona.board.normalBoard.repository;

import com.fundguide.melona.board.normalBoard.entity.NormalBoardImpeachEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NormalBoardImpeachRepository extends JpaRepository<NormalBoardImpeachEntity, Long> {
}
