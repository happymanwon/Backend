package org.hmanwon.domain.community.comment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.community.board.entity.Board;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Comment(Long id, String content, Member member, Board board) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.board = board;
    }

    public void updateContent(String content){
        this.content = content;
    }
}
