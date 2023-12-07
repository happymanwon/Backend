package org.hmanwon.domain.zzan.zzanItem.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ZzanExceptionCode implements ExceptionCode {

    NOT_FOUND_ITEM(NOT_FOUND, "Zzan Error 01", "짠처리 아이템이 존재하지 않습니다."),
    LOCAL_CODE_BAD_REQUEST(BAD_REQUEST, "Zzan Error 02", "지역 코드가 올바르지 않습니다."),
    CATEGORY_BAD_REQUEST(BAD_REQUEST, "Zzan Error 03", "카테고리가 올바르지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
