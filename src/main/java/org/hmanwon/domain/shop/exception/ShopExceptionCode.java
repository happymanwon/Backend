package org.hmanwon.domain.shop.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ShopExceptionCode implements ExceptionCode {

    NOT_FOUND_FILE(INTERNAL_SERVER_ERROR, "Display Error 01", "파일이 존재하지 않습니다."),
    LOCAL_CODE_BAD_REQUEST(BAD_REQUEST, "Display Error 02", "지역 코드가 올바르지 않습니다."),
    CATEGORY_BAD_REQUEST(BAD_REQUEST, "Display Error 03", "카테고리가 올바르지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
