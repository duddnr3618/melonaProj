package com.fundguide.melona.board.like.repository;

import com.fundguide.melona.board.like.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

}
