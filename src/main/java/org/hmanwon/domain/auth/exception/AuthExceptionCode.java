package org.hmanwon.domain.auth.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionCode implements ExceptionCode {

    UNAUTHORIZED_TOKEN(UNAUTHORIZED, "Auth Error 01", "토큰이 유효하지 않습니다."),
    KAKAO_NETWORK_ERROR(INTERNAL_SERVER_ERROR, "Auth Erorr 02", "카카오 서버와의 통신 문제가 발생하였습니다."),
    INVALID_TOKEN(BAD_REQUEST, "Auth Erorr 03", "토큰이 빈문자열 혹은 NULL값입니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
