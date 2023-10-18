package com.fundguide.melona.mypage.Service;

import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.repository.CommunityRepository;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import com.fundguide.melona.board.normalBoard.entity.NormalBoardEntity;
import com.fundguide.melona.board.normalBoard.repository.NormalBoardRepository;
import com.fundguide.melona.member.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MypageService {


    @Autowired
    private NormalBoardRepository normalBoardRepository;
    @Autowired
    private LeaderBoardRepository leaderBoardRepository; // 필드 주입 추가
    @Autowired
    private CommunityRepository communityRepository; // 필드 주입 추가

    public Page<NormalBoardEntity> getNormalPosts(MemberEntity memberEntity, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        return normalBoardRepository.findByMemberEntity(memberEntity, pageable);
    }

    public Page<LeaderBoardEntity> getLeaderPosts(MemberEntity memberEntity, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        return leaderBoardRepository.findByMemberEntity(memberEntity, pageable); // 올바른 메서드 호출로 수정
    }

    public Page<CommunityEntity> getCommunityPosts(MemberEntity memberEntity, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        return communityRepository.findByMemberEntity(memberEntity, pageable);
    }

}

