package org.hmanwon.domain.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Comment("가게 메뉴 id")
    private Long id;

    @Column(nullable = false)
    @Comment("Menu 이름")
    private String menuName;

    @Column(nullable = false)
    @Comment("Menu 가격")
    private Integer menuPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seoul_good_shop_id")
    @Comment("서울 착한 업소 FK")
    private SeoulGoodShop seoulGoodShop;
}
