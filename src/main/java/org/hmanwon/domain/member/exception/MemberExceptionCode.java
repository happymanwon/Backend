package org.hmanwon.domain.member.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberExceptionCode implements ExceptionCode {

    NOT_FOUND_MEMBER(INTERNAL_SERVER_ERROR, "Member Error 01", "회원 정보가 존재하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
