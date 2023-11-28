package org.hmanwon.domain.member.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class MemberException extends DefaultException {

    public MemberException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
