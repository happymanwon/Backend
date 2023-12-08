package org.hmanwon.domain.zzan.purchase.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QrCodeExceptionCode implements ExceptionCode {

    NOT_FOUND_QRCODE_IMAGE(NOT_FOUND, "QR code Error 01", "QR code 이미지가 존재하지 않습니다."),
    FAILED_CREATE_QR(INTERNAL_SERVER_ERROR, "QR code Error 02", "QR 생성에 실패했습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;

}
