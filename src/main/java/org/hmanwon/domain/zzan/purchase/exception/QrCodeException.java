package org.hmanwon.domain.zzan.purchase.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class QrCodeException extends DefaultException {

    public QrCodeException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
