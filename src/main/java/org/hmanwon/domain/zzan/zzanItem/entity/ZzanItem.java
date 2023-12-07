package org.hmanwon.domain.zzan.zzanItem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;
import org.hmanwon.domain.zzan.purchase.entity.PurchaseHistory;
import org.hmanwon.domain.zzan.zzanItem.type.SaleStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "zzan_item")
public class ZzanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "zzan_item_id")
    @Comment("판매 아이템 ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seoul_good_shop_id")
    @Comment("서울 가격 업소 FK")
    private SeoulGoodShop seoulGoodShop;

    @Comment("서울 가격 업소 이름")
    private String shopName;

    @Comment("판매 상품 이름")
    private String itemName;

    @Comment("업소 이미지")
    private String imageUrl;

    @Comment("판매 상품 정보")
    private String content;

    @Comment("판매 상품 마감 날짜")
    private LocalDate deadLine;

    @Comment("판매 상품 원래 가격")
    private Integer originalPrice;

    @Comment("판매 상품 할인율")
    private Double discountRate;

    @Comment("판매 상품 가격")
    private Integer salePrice;

    @Comment("판매 상품 수량")
    @Setter
    private Integer count;

    @Comment("판매 가능 상태")
    @Enumerated(EnumType.STRING)
    @Setter
    private SaleStatus status;

    @OneToMany(mappedBy = "zzanItem",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PurchaseHistory> purchaseHistoryList = new ArrayList<>();

}
