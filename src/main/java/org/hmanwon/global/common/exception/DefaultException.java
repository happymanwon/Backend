package org.hmanwon.global.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DefaultException extends RuntimeException {

    ExceptionCode errorCode;

    public DefaultException(ExceptionCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
}
