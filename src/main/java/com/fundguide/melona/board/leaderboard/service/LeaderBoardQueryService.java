package com.fundguide.melona.board.leaderboard.service;

import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.leaderboard.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.prefs.PreferencesFactory;

@Service @RequiredArgsConstructor @Transactional(readOnly = true)
public class LeaderBoardQueryService {
    private final LeaderBoardRepository leaderBoardRepository;


}
