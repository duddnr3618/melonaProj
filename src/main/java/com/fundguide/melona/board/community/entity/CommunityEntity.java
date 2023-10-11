package com.fundguide.melona.board.community.entity;

import com.fundguide.melona.board.common.entity.BaseTimeEntity;
import com.fundguide.melona.board.common.role.BoardUsing;
import com.fundguide.melona.board.community.dto.CommunityDto;
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
    @OneToMany(mappedBy = "communityEntity", cascade = CascadeType.ALL)
    private Set<Community_like> boardLike;

    /* 댓글과 연관관계 */
    @OneToMany(mappedBy = "communityEntity", cascade = CascadeType.REMOVE)
    private List<CommentEntity> commentEntitiyList = new ArrayList<>();

    /*----------------------------------------------------------------------------------*/
    /*신고**/
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<CommunityImpeachEntity> impeach = new HashSet<>();

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    @ColumnDefault("'0'")
    @Builder.Default
    private BoardUsing boardUsing = BoardUsing.USING;
    /*----------------------------------------------------------------------------------*/

    /* dto -> entity 변환 */
    public static CommunityEntity toSaveEntity(CommunityDto communityDto) {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setBoardTitle(communityDto.getBoardTitle());
        communityEntity.setBoardContents(communityDto.getBoardContents());
        communityEntity.setFilePath(communityDto.getFilePath());
        communityEntity.setBoardHits(0);
        return communityEntity;
    }

    public static CommunityEntity toUpdateEntity(CommunityDto communityDto) {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setId(communityDto.getId());
        communityEntity.setBoardTitle(communityDto.getBoardTitle());
        communityEntity.setBoardContents(communityDto.getBoardContents());
        communityEntity.setBoardHits(communityDto.getBoardHits());
        communityEntity.setFileName(communityDto.getFileName());
        communityEntity.setFilePath(communityDto.getFilePath());
        return communityEntity;
    }

    /*************************************************************************************************/
    public static CommunityEntity toSaveEntityBuild(CommunityDto communityDto) {
        return CommunityEntity.builder()
                .boardTitle(communityDto.getBoardTitle())
                .boardContents(communityDto.getBoardContents())
                .filePath(communityDto.getFilePath())
                .boardHits(communityDto.getBoardHits())
                .build();
    }

    public static CommunityEntity toUpdateEntityBuild(CommunityDto communityDto) {
        return CommunityEntity.builder()
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
