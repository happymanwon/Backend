package org.hmanwon.domain.display.init.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum InitDisplayExceptionCode implements ExceptionCode {

    NOT_FOUND_FILE(INTERNAL_SERVER_ERROR, "Init Display Error 01", "파일이 존재하지 않습니다."),
    JSON_ERROR(INTERNAL_SERVER_ERROR, "Init Display Error 02", "json 변환 중 에러가 발생하였습니다."),
    FAILED_SAVE(INTERNAL_SERVER_ERROR, "Init Display Error 03", "저장 중 에러가 발생하였습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
