package com.fundguide.melona.board.community.repository.impeach;

import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityImpeachRepository extends JpaRepository<CommunityImpeachEntity, Long>, CommunityImpeachCustom {
}
