package org.hmanwon.domain.like.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seoulGoodShop_id")
    private SeoulGoodShop seoulGoodShop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Like(Long id, SeoulGoodShop seoulGoodShop, Member member) {
        this.id = id;
        this.seoulGoodShop = seoulGoodShop;
        this.member = member;
    }
}
