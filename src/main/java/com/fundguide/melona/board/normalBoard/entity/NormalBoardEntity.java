package com.fundguide.melona.board.normalBoard.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.community.entity.CommentEntity;
import com.fundguide.melona.board.community.entity.CommunityEntity;
import com.fundguide.melona.board.community.entity.CommunityImpeachEntity;
import com.fundguide.melona.board.community.entity.Community_like;
import com.fundguide.melona.board.leaderboard.entity.LeaderBoardEntity;
import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "normal_board")
public class NormalBoardEntity extends BaseTimeEntity {

  @Id
  @Column(name = "community_board_id")
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
  @OneToMany(mappedBy = "normalBoardEntity", cascade = CascadeType.ALL)
  private Set<NormalBoard_like> boardLike;

  /* 댓글과 연관관계 */
  @OneToMany(mappedBy = "normalBoardEntity", cascade = CascadeType.REMOVE)
  private List<NormalBoardCommentEntity> commentEntitiyList = new ArrayList<>();

  /*----------------------------------------------------------------------------------*/
  /*신고**/
  @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
          cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
  private Set<NormalBoardImpeachEntity> impeach = new HashSet<>();

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false)
  @ColumnDefault("'0'")
  @Builder.Default
  private BoardUsing boardUsing = BoardUsing.USING;
  /*----------------------------------------------------------------------------------*/

  /* dto -> entity 변환 */
  public static NormalBoardEntity toSaveEntity(CommunityDto communityDto) {
    NormalBoardEntity normalBoardEntity = new NormalBoardEntity();
    normalBoardEntity.setBoardTitle(communityDto.getBoardTitle());
    normalBoardEntity.setBoardContents(communityDto.getBoardContents());
    normalBoardEntity.setFilePath(communityDto.getFilePath());
    normalBoardEntity.setBoardHits(0);
    return normalBoardEntity;
  }

  public static NormalBoardEntity toUpdateEntity(CommunityDto communityDto) {
    NormalBoardEntity normalBoardEntity = new NormalBoardEntity();
    normalBoardEntity.setId(communityDto.getId());
    normalBoardEntity.setBoardTitle(communityDto.getBoardTitle());
    normalBoardEntity.setBoardContents(communityDto.getBoardContents());
    normalBoardEntity.setBoardHits(communityDto.getBoardHits());
    normalBoardEntity.setFileName(communityDto.getFileName());
    normalBoardEntity.setFilePath(communityDto.getFilePath());
    return normalBoardEntity;
  }

  /*************************************************************************************************/
  public static NormalBoardEntity toSaveEntityBuild(CommunityDto communityDto) {
    return NormalBoardEntity.builder()
            .boardTitle(communityDto.getBoardTitle())
            .boardContents(communityDto.getBoardContents())
            .filePath(communityDto.getFilePath())
            .boardHits(communityDto.getBoardHits())
            .build();
  }

  public static NormalBoardEntity toUpdateEntityBuild(CommunityDto communityDto) {
    return NormalBoardEntity.builder()
            .id(communityDto.getId())
            .boardTitle(communityDto.getBoardTitle())
            .boardContents(communityDto.getBoardContents())
            .boardHits(communityDto.getBoardHits())
            .fileName(communityDto.getFileName())
            .filePath(communityDto.getFilePath())
            .build();
  }
  /*---------------------------------------------------------------------------*/
}
