package org.hmanwon.domain.zzan.zzanItem.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class ZzanException extends DefaultException {

    public ZzanException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
