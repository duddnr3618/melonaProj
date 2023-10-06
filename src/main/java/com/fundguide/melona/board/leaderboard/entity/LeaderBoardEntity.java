package com.fundguide.melona.board.leaderboard.entity;

import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leader_board")
public class LeaderBoardEntity extends BaseMemberEntity {

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
    private Long boardHits = 0L; // 조회수

// getter와 setter 메서드 추가

    public Long getBoardHits() {
        return boardHits;
    }

    public void setBoardHits(Long boardHits) {
        this.boardHits = boardHits;
    }



    // dto -> entity변환
    public static LeaderBoardEntity toSaveEntity (LeaderBoardDto leaderBoardDto) {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity();
        leaderBoardEntity.setBoardWriter(leaderBoardDto.getBoardWriter());
        leaderBoardEntity.setBoardTitle(leaderBoardDto.getBoardTitle());
        leaderBoardEntity.setBoardContents(leaderBoardDto.getBoardContents());
        leaderBoardEntity.setBoardHits(0L);
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
