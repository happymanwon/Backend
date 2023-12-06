package org.hmanwon.domain.zzan.dto;

import lombok.Builder;
import org.hmanwon.domain.zzan.entity.ZzanItem;

@Builder
public record ZzanItemResponse(
        Long zzanItemId,
        String shopName,
        String itemName,
        String imageUrl,
        Integer price,
        Integer count
) {
    public static ZzanItemResponse fromEntity(ZzanItem zzanItem) {
        return ZzanItemResponse.builder()
                .zzanItemId(zzanItem.getId())
                .shopName(zzanItem.getShopName())
                .itemName(zzanItem.getItemName())
                .count(zzanItem.getCount())
                .imageUrl(zzanItem.getImageUrl())
                .price(zzanItem.getPrice())
                .build();
    }
}
