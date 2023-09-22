package com.fundguide.melona.board.leaderboard.entity;

import com.fundguide.melona.board.common.entity.BaseBoardEntity;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leader_board")
public class LeaderBoardEntity extends BaseBoardEntity {

    @Id
    @Column(name = "boardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "boardWriter")
    private String boardWriter;
    @Column(name = "boardTitle")
    private String boardTitle;
    @Column(name = "boardContents")
    private String boardContents;
    @Column(name = "boardHits")
    private long boardHits; //조회수


    // dto -> entity변환
    public static LeaderBoardEntity toSaveEntity (LeaderBoardDto leaderBoardDto) {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity();
        leaderBoardEntity.setBoardWriter(leaderBoardDto.getBoardWriter());
        leaderBoardEntity.setBoardTitle(leaderBoardDto.getBoardTitle());
        leaderBoardEntity.setBoardContents(leaderBoardDto.getBoardContents());
        leaderBoardEntity.setBoardHits(0);
        return leaderBoardEntity;
    }


    public static LeaderBoardEntity toUpdateEntity(LeaderBoardDto leaderBoardDto) {

        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity();
        leaderBoardEntity.setId(leaderBoardDto.getId());
        leaderBoardEntity.setBoardWriter(leaderBoardDto.getBoardWriter());
        leaderBoardEntity.setBoardTitle(leaderBoardDto.getBoardTitle());
        leaderBoardEntity.setBoardContents(leaderBoardDto.getBoardContents());
        leaderBoardEntity.setBoardHits(leaderBoardDto.getBoardHits());
        return leaderBoardEntity;
    }
}
