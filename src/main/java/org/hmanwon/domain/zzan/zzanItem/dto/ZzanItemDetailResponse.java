package org.hmanwon.domain.zzan.zzanItem.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.zzan.zzanItem.entity.ZzanItem;
import org.hmanwon.domain.zzan.zzanItem.type.SaleStatus;

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
        LocalDate deadLine,
        Boolean canSale,
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
                .deadLine(zzanItem.getDeadLine())
                .canSale(getCanSale(zzanItem.getStatus()))
                .shopInfo(SeoulGoodShopResponse.fromEntity(zzanItem.getSeoulGoodShop()))
                .build();
    }

    private static boolean getCanSale(SaleStatus status) {
        if (status.equals(SaleStatus.SALE)) return true;
        return false;
    }
}
