package org.hmanwon.domain.community.board.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.global.common.entity.BaseTimeEntity;
import org.hmanwon.infra.image.entity.Image;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    private Double longitude;
    private Double latitude;
    private Integer reportCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board",
        cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "board",
        cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<BoardHashtag> boardHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "board",
        cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void increaseReportCnt(){
        this.reportCnt++;
    }

    public void setImages(List<Image> imageList) {
        this.images = imageList;
    }

    public void setComments(List<Comment> CommentList) {
        this.comments = CommentList;
    }

    public static Board of(String content, Member member) {
        return Board
            .builder()
            .content(content)
            .member(member)
            .build();
    }
}
