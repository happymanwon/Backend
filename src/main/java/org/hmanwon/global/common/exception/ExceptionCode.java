package org.hmanwon.global.common.exception;

import org.springframework.http.HttpStatus;


public interface ExceptionCode {
    HttpStatus getStatus();
    String getCode();
    String getMsg();
}
