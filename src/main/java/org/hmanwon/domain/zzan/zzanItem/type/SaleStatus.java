package org.hmanwon.domain.zzan.zzanItem.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SaleStatus {
    SALE("판매 중"),
    SOLD_OUT("품절"),
    SALE_END("판매 종료")
    ;

    private String type;
}
