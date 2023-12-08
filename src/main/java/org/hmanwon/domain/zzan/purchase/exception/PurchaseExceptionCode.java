package org.hmanwon.domain.zzan.purchase.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PurchaseExceptionCode implements ExceptionCode {

    FAILED_PURCHASE(INTERNAL_SERVER_ERROR, "Purchase Error 01", "구매에 실패하였습니다."),
    NOT_POSSIBLE_PURCHASE_SOLD_OUT(NOT_FOUND, "Purchase Error 02", "주문하신 상품이 품절입니다."),
    NOT_POSSIBLE_PURCHASE_OUT_OF_DEADLINE(NOT_FOUND, "Purchase Error 03", "마감기한이 지나 주문할 수 없습니다."),
    LACK_OF_POINTS(NOT_FOUND, "Purchase Error 04", "포인트가 부족합니다."),
    NOT_FOUND_PURCHASE(NOT_FOUND, "Purchase Error 05", "찾으시는 구매 정보가 없습니다."),
    ALREADY_USE(NOT_FOUND, "Purchase Error 06", "이미 사용 완료된 상품입니다."),
    FAILED_PURCHASE_USE(INTERNAL_SERVER_ERROR, "Purchase Error 07", "사용 처리에 실패하였습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
