package com.fundguide.melona.board.community.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.community.dto.CommunityDto;
import com.fundguide.melona.board.like.entity.LikeEntity;
import com.fundguide.melona.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "community_board")
public class CommunityEntity extends BaseTimeEntity {

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
    @OneToMany(mappedBy = "communityEntity", cascade = CascadeType.REMOVE)
    private List<LikeEntity> boardLike;
    @Transient
    private boolean like_state;
    @Transient
    private int like_count;

    /* dto -> entity 변환 */
    public static CommunityEntity toSaveEntity(CommunityDto communityDto){
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setBoardTitle(communityDto.getBoardTitle());
        communityEntity.setBoardContents(communityDto.getBoardContents());
        communityEntity.setFilePath(communityDto.getFilePath());
        communityEntity.setBoardHits(0);
        //communityEntity.setMemberEntity(communityDto.getMemberEntity());
        return communityEntity;
    }

    public static CommunityEntity toUpdateEntity(CommunityDto communityDto){
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setId(communityDto.getId());
        communityEntity.setBoardTitle(communityDto.getBoardTitle());
        communityEntity.setBoardContents(communityDto.getBoardContents());
        communityEntity.setBoardHits(communityDto.getBoardHits());
        //communityEntity.setMemberEntity(communityDto.getMemberEmail());
        communityEntity.setFileName(communityDto.getFileName());
        communityEntity.setFilePath(communityDto.getFilePath());
        return communityEntity;
    }



}
