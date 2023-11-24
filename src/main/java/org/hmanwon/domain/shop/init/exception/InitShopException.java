package org.hmanwon.domain.shop.init.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class InitShopException extends DefaultException {

    public InitShopException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
