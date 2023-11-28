package org.hmanwon.domain.member.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.community.comment.entity.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.Comment("회원 ID")
    private Long id;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("회원 email")
    private String email;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("회원 닉네임")
    private String nickName;

    @Column(nullable = false)
    @org.hibernate.annotations.Comment("회원 포인트")
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
