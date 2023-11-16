package org.hmanwon.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@Builder
public class ResponseDTO<T> {

    private final HttpStatus status;
    private final String msg;
    private final T data;

    public static <T> ResponseDTO<T> ok(String msg, T data) {
        return new ResponseDTO<>(HttpStatus.OK, msg, data);
    }

    public static <T> ResponseDTO<Void> ok(String msg) {
        return new ResponseDTO<>(HttpStatus.OK, msg, null);
    }

    public static <T> ResponseDTO<Void> ok() {
        return new ResponseDTO<>(HttpStatus.OK, null, null);
    }


}
