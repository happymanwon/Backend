package org.hmanwon.infra.image.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class ImageException extends DefaultException {

    public ImageException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
