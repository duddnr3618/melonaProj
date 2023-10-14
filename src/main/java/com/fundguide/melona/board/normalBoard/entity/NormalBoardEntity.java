package com.fundguide.melona.board.normalBoard.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.normalBoard.dto.NormalBoardDto;
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
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "normal_board")
public class NormalBoardEntity extends BaseTimeEntity {

  @Id
  @Column(name = "normal_board_id")
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
  private List<CommentNormalBoardEntity> commentLeaderBoardEntityList = new ArrayList<>();

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
  public static NormalBoardEntity toSaveNormalBoardEntity(NormalBoardDto normalBoardDto) {
    NormalBoardEntity normalBoardEntity = new NormalBoardEntity();
    normalBoardEntity.setBoardTitle(normalBoardDto.getBoardTitle());
    normalBoardEntity.setBoardContents(normalBoardDto.getBoardContents());
    normalBoardEntity.setFilePath(normalBoardDto.getFilePath());
    normalBoardEntity.setBoardHits(0);
    return normalBoardEntity;
  }

  public  static ModelMapper modelMapper = new ModelMapper();
  public static NormalBoardEntity toUpdateNormalBoardEntity(NormalBoardDto normalBoardDto){
    return modelMapper.map(normalBoardDto, NormalBoardEntity.class);
  }
}
