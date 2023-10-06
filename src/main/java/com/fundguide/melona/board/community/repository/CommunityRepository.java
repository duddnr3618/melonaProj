package com.fundguide.melona.board.community.repository;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity,Long> {

}
