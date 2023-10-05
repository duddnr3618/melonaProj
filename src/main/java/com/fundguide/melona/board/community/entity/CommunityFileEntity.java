package com.fundguide.melona.board.community.entity;

import com.fundguide.melona.board.common.entity.BaseMemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "community_file")
public class CommunityFileEntity extends BaseMemberEntity {
    @Id
    @Column(name = "community_file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String originalFileName;
    private String fileUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_board_id")
    private CommunityEntity communityEntity;

    /* 파일첨부 수정시 */
    public void updateFile (String originalFileName,
                            String fileName,
                            String fileUrl) {
        this.fileName = fileName;
        this.originalFileName = originalFileName;
        this.fileUrl = fileUrl;
    }

}
