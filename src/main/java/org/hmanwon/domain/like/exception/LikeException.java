package org.hmanwon.domain.like.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class LikeException extends DefaultException {

    public LikeException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

}
