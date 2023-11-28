package org.hmanwon.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum DefaultExceptionCode implements ExceptionCode {


    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
        "서버에 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "잘못된 요청 입니다."),

    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;


}
