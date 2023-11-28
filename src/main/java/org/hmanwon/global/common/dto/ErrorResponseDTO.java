package org.hmanwon.global.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorResponseDTO {

    private final HttpStatus status;
    private final String code;
    private final String msg;

    public static ErrorResponseDTO error(ExceptionCode exceptionCode) {
        return new ErrorResponseDTO(
            exceptionCode.getStatus(),
            exceptionCode.getCode(),
            exceptionCode.getMsg()
        );
    }
}
