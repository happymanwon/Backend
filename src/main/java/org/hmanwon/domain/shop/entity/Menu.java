package org.hmanwon.domain.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.hmanwon.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Integer menuPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seoul_good_shop_id")
    private SeoulGoodShop seoulGoodShop;
}
