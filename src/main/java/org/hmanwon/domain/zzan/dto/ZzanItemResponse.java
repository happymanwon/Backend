package org.hmanwon.domain.zzan.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.hmanwon.domain.zzan.entity.ZzanItem;
import org.hmanwon.domain.zzan.type.SaleStatus;

@Builder
public record ZzanItemResponse(
        Long zzanItemId,
        String shopName,
        String itemName,
        String imageUrl,
        Integer originalPrice,
        Double discountRate,
        Integer salePrice,
        Integer price,
        LocalDate deadLine,
        Boolean canSale,
        Integer count
) {
    public static ZzanItemResponse fromEntity(ZzanItem zzanItem) {
        return ZzanItemResponse.builder()
                .zzanItemId(zzanItem.getId())
                .shopName(zzanItem.getShopName())
                .itemName(zzanItem.getItemName())
                .count(zzanItem.getCount())
                .imageUrl(zzanItem.getImageUrl())
                .originalPrice(zzanItem.getOriginalPrice())
                .discountRate(zzanItem.getDiscountRate())
                .salePrice(zzanItem.getSalePrice())
                .deadLine(zzanItem.getDeadLine())
                .canSale(getCanSale(zzanItem.getStatus()))
                .build();
    }

    private static boolean getCanSale(SaleStatus status) {
        if (status.equals(SaleStatus.SALE)) return true;
        return false;
    }
}
