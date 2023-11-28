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
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board",
        cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Comment> commentList =
        new ArrayList<>();

    public void setCommentList(
        List<Comment> commentList) {
        this.commentList = commentList;
    }

    public static Board of(String title, String content, Member member) {
        return Board
            .builder()
            .title(title)
            .content(content)
            .member(member)
            .build();
    }
}
