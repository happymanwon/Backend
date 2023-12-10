package org.hmanwon.domain.community.board.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardExceptionCode implements ExceptionCode {

    NOT_FOUND_BOARD(NOT_FOUND, "Board Error 01", "게시글이 존재하지 않습니다."),
    NOT_FOUND_HASHTAG(INTERNAL_SERVER_ERROR, "Board Error 02", "해시 태그가 존재하지 않습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
