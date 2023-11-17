package org.hmanwon.domain.display.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class DisplayException extends DefaultException {

    public DisplayException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
