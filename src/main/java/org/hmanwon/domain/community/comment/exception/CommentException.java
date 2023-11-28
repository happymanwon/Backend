package org.hmanwon.domain.community.comment.exception;

import org.hmanwon.global.common.exception.DefaultException;
import org.hmanwon.global.common.exception.ExceptionCode;

public class CommentException extends DefaultException {

    public CommentException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
