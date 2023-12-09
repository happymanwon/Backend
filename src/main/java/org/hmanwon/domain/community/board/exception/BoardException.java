package org.hmanwon.domain.community.board.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class BoardException extends DefaultException {

    public BoardException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
