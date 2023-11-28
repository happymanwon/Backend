package org.hmanwon.domain.member.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.hmanwon.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    @Min(0)
    private Long point;

    @OneToMany(mappedBy = "member",
        cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member",
        cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void setBoardList(List<Board> boardList) {
        this.boardList = boardList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
