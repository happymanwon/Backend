package org.hmanwon.domain.zzan.purchase.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import org.hmanwon.domain.zzan.purchase.entity.PurchaseHistory;
import org.hmanwon.domain.zzan.purchase.util.QRCodeUtil;

@Builder
public record PurchaseResponse(
        Long purchaseId,
        Long zzanItemId,
        String shopName,
        String itemName,
        Integer price,
        String qrUrl,
        String status,
        LocalDateTime usedTime
) {
    public static PurchaseResponse fromEntity(PurchaseHistory ph) {
        return PurchaseResponse.builder()
                .purchaseId(ph.getId())
                .zzanItemId(ph.getZzanItem().getId())
                .itemName(ph.getZzanItem().getItemName())
                .shopName(ph.getZzanItem().getShopName())
                .price(ph.getPrice())
                .status(ph.getStatus().getType())
                .qrUrl(QRCodeUtil.getQrImageURL(ph.getQrImage()))
                .usedTime(ph.getUsedAt())
                .build();
    }
}
