package org.hmanwon.domain.zzan.dto;

import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.Builder;
import org.hibernate.annotations.Comment;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.zzan.entity.ZzanItem;

@Builder
public record ZzanItemDetailResponse(
        Long zzanItemId,
        String shopName,
        String itemName,
        String content,
        Integer originalPrice,
        Double discountRate,
        Integer salePrice,
        Integer count,
        SeoulGoodShopResponse shopInfo
) {
    public static ZzanItemDetailResponse fromEntity(ZzanItem zzanItem) {
        return ZzanItemDetailResponse.builder()
                .zzanItemId(zzanItem.getId())
                .shopName(zzanItem.getShopName())
                .itemName(zzanItem.getItemName())
                .content(zzanItem.getContent())
                .originalPrice(zzanItem.getOriginalPrice())
                .discountRate(zzanItem.getDiscountRate())
                .salePrice(zzanItem.getSalePrice())
                .count(zzanItem.getCount())
                .shopInfo(SeoulGoodShopResponse.fromEntity(zzanItem.getSeoulGoodShop()))
                .build();
    }
}
