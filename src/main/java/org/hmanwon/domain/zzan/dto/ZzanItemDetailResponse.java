package org.hmanwon.domain.zzan.dto;

import lombok.Builder;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.zzan.entity.ZzanItem;

@Builder
public record ZzanItemDetailResponse(
        Long zzanItemId,
        String shopName,
        String itemName,
        String content,
        Integer price,
        Integer count,
        SeoulGoodShopResponse shopInfo
) {
    public static ZzanItemDetailResponse fromEntity(ZzanItem zzanItem) {
        return ZzanItemDetailResponse.builder()
                .zzanItemId(zzanItem.getId())
                .shopName(zzanItem.getShopName())
                .itemName(zzanItem.getItemName())
                .content(zzanItem.getContent())
                .price(zzanItem.getPrice())
                .count(zzanItem.getCount())
                .shopInfo(SeoulGoodShopResponse.fromEntity(zzanItem.getSeoulGoodShop()))
                .build();
    }
}
