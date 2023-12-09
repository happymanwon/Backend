package org.hmanwon.domain.community.board.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardExceptionCode implements ExceptionCode {

    NOT_FOUND_BOARD(INTERNAL_SERVER_ERROR, "Board Error 01", "게시글이 존재하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
