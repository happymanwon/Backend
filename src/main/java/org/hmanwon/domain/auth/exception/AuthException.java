package org.hmanwon.domain.auth.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class AuthException extends DefaultException {

    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
