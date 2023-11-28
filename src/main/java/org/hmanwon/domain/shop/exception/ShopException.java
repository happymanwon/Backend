package org.hmanwon.domain.shop.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class ShopException extends DefaultException {

    public ShopException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
