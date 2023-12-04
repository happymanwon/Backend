package org.hmanwon.domain.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeoulGoodShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Comment("서울 착한 업소 ID")
    private Long id;

    @Column(nullable = false)
    @Comment("업소 이름")
    private String name;

    @Column(nullable = false)
    @Comment("업소 제공 주소")
    private String address;

    @Column(nullable = false)
    @Comment("업소 도로명 주소")
    private String roadAddress;

    @Column(nullable = false)
    @Comment("주소 위치 기반 위도")
    private String latitude;

    @Column(nullable = false)
    @Comment("주소 위치 기반 위도")
    private String longitude;

    @Column(nullable = true)
    @Comment("업소 정보")
    private String info;

    @Column(nullable = false)
    @Comment("업소 전화번호")
    private String phone;

    @Column(nullable = true)
    @Comment("업소 자랑거리")
    private String pride;

    @Column(nullable = true)
    @Comment("업소 오는 길")
    private String way;

    @Column(nullable = true)
    @Comment("업소 썸네일")
    private String imageUrl;

    @Column(nullable = false)
    @Comment("업소 추천 수")
    private Integer rcmnCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_code")
    @Comment("지역 코드 FK")
    private LocalCode localCode;

    @Column(nullable = true)
    @Comment("업소 카테고리 번호")
    private Long category;

    @OneToMany(mappedBy = "seoulGoodShop",
        cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    @Setter
    private List<Menu> menuList = new ArrayList<>();

}
