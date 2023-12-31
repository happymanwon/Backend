package org.hmanwon.domain.zzan.purchase.entity;

import java.time.LocalDateTime;
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
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.zzan.purchase.type.PurchaseStatusType;
import org.hmanwon.domain.zzan.zzanItem.entity.ZzanItem;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_history_id")
    @Comment("구매 기록 ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Comment("구매자")
    private Member member;

    @Comment("짠처리 아이템 FK")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zzan_item_id")
    private ZzanItem zzanItem;

    @Comment("구매 가능한 QR 코드 id")
    @JoinColumn(name = "qr_image_id")
    @OneToOne
    @Setter
    private QrImage qrImage;

    @Comment("QR 사용 여부")
    @Enumerated(EnumType.STRING)
    @Setter
    private PurchaseStatusType status;

    @Comment("구매 날짜 및 시간")
    private LocalDateTime createdAt;

    @Comment("QR 사용 날짜 및 시간")
    @Setter
    private LocalDateTime usedAt;

    @Comment("구매 가격")
    private Integer price;

}
