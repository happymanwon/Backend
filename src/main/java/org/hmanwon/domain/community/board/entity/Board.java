package org.hmanwon.domain.community.board.entity;

import java.awt.PageAttributes.MediaType;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hmanwon.domain.member.entity.Member;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("게시판 ID")
    private Long id;

    @Column(nullable = false)
    @Comment("게시판 제목")
    private String title;

    @Column(nullable = false)
    @Comment("게시판 글 내용")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Comment("회원 FK")
    private Member member;

    @OneToMany(mappedBy = "board",
        cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @Setter // 연간관계 메서드로 바꿔야 함
    private List<org.hmanwon.domain.community.comment.entity.Comment> commentList = new ArrayList<>();
}
