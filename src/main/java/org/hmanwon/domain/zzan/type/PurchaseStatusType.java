package org.hmanwon.domain.zzan.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseStatusType {

    IN_USE("사용했음"),
    TO_BE_USE("구매는 했고, 사용은 예정")
    ;

    private String type;
}
