package org.hmanwon.domain.zzan.purchase.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class PurchaseException extends DefaultException {

    public PurchaseException(ExceptionCode errorCode) {
        super(errorCode);
    }
}
