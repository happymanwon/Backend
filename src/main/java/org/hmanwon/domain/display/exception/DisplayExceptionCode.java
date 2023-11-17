package org.hmanwon.domain.display.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DisplayExceptionCode implements ExceptionCode {

    NOT_FOUND_FILE(INTERNAL_SERVER_ERROR, "Display Error 01", "파일이 존재하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
