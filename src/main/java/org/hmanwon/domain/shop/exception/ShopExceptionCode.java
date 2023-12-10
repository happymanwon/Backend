package org.hmanwon.domain.shop.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ShopExceptionCode implements ExceptionCode {

    NOT_FOUND_FILE(INTERNAL_SERVER_ERROR, "Shop Error 01", "파일이 존재하지 않습니다."),
    LOCAL_CODE_BAD_REQUEST(BAD_REQUEST, "Shop Error 02", "지역 코드가 올바르지 않습니다."),
    CATEGORY_BAD_REQUEST(BAD_REQUEST, "Shop Error 03", "카테고리가 올바르지 않습니다."),
    NOT_FOUND_SHOP(NOT_FOUND, "Shop Error 04", "가게 정보가 존재하지 않습니다."),
    DUPLICATED_REQUEST(CONFLICT, "Shop Error 05", "중복된 요청입니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
