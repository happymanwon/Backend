package org.hmanwon.infra.image.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hmanwon.global.common.exception.ExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ImageExceptionCode implements ExceptionCode {

    INVALID_IMAGE_READ(UNSUPPORTED_MEDIA_TYPE, "Image Error 01", "이미지를 정상적으로 읽을 수 없습니다."),
    IMAGE_NAME_BLANK(NOT_FOUND, "Image Error 02", "원본 이미지명이 존재하지 않습니다."),
    IMAGE_FORMAT(UNSUPPORTED_MEDIA_TYPE, "Image Error 03", "이미지 확장자가 존재하지 않습니다."),
    IMAGE_EXTENSION(UNSUPPORTED_MEDIA_TYPE, "Image Error 04", "이미지 파일만 업로드할 수 있습니다."),
    IMAGE_TRANSFER(INTERNAL_SERVER_ERROR, "Image Error 05", "이미지 업로드에 실패했습니다."),
    IMAGE_URL(NOT_FOUND, "Image Error 06", "이미지 URL을 확인할 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String msg;
}
