package org.hmanwon.domain.zzan.purchase.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseStatusType {

    IN_USE("사용 완료"),
    TO_BE_USE("구매 완료, 사용 예정")
    ;

    private String type;
}
