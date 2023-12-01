package org.hmanwon.domain.like.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LikeExceptionCode implements ExceptionCode {

    NOT_FOUND_LIKE(INTERNAL_SERVER_ERROR, "Like Error 01", "좋아요 정보가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
