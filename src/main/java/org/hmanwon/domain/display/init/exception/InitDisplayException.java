package org.hmanwon.domain.display.init.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class InitDisplayException extends DefaultException {

    public InitDisplayException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
