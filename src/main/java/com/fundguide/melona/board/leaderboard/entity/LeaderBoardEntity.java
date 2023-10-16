package com.fundguide.melona.board.leaderboard.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommentEntity;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.leaderboard.dto.LeaderBoardDto;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leader_board")
public class LeaderBoardEntity extends BaseTimeEntity {

    @Id
    @Column(name = "leader_board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String boardTitle;
    private String boardContents;
    private int boardHits;

    private String fileName;
    private String filePath;

    /* 회원과의 연관관계 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    /* 좋아요 연관관계 */
    @OneToMany(mappedBy = "leaderBoardEntity", cascade = CascadeType.ALL)
    private Set<LeaderBoard_like> boardLike;

    /* 댓글과 연관관계 */
    @OneToMany(mappedBy = "leaderBoardEntity", cascade = CascadeType.REMOVE)
    private List<CommentLeaderBoardEntity> commentLeaderBoardEntityList = new ArrayList<>();

    /*----------------------------------------------------------------------------------*/
    /*신고**/
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<LeaderBoardImpeachEntity> impeach = new HashSet<>();

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    @ColumnDefault("'0'")
    @Builder.Default
    private BoardUsing boardUsing = BoardUsing.USING;
    /*----------------------------------------------------------------------------------*/

    /* dto -> entity 변환 */
    public static LeaderBoardEntity toSaveLeaderBoardEntity(LeaderBoardDto leaderBoardDto) {
        LeaderBoardEntity leaderBoardEntity = new LeaderBoardEntity();
        leaderBoardEntity.setBoardTitle(leaderBoardDto.getBoardTitle());
        leaderBoardEntity.setBoardContents(leaderBoardDto.getBoardContents());
        leaderBoardEntity.setFilePath(leaderBoardDto.getFilePath());
        leaderBoardEntity.setBoardHits(0);
        return leaderBoardEntity;
    }

    public  static ModelMapper modelMapper = new ModelMapper();
    public static LeaderBoardEntity toUpdateLeaderBoardEntity(LeaderBoardDto leaderBoardDto){
        return modelMapper.map(leaderBoardDto, LeaderBoardEntity.class);
    }
}
