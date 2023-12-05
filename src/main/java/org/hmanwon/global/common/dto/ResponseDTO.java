package org.hmanwon.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

@Getter
@Setter
@ToString
@Builder
public class ResponseDTO {

    private DataBody body;

    /**
     * 상태 코드 : created (201) : Post, Put
     * @param data
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponseEntity<DataBody<T>> created(@Nullable T data, String message) {
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DataBody<T>(data, message));
    }

    /**
     * 상태 코드 : created (201)
     * @param message
     * @return
     * @param
     */
    public static <T> ResponseEntity<DataBody<T>> created(String message) {
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new DataBody<T>(message));
    }

    /**
     * 상태 코드 : ok (200)
     * @param data
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponseEntity<DataBody<T>> ok(@Nullable T data, String message) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataBody<T>(data, message));
    }

    /**
     * 상태 코드 : ok (200)
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponseEntity<DataBody<T>> ok(String message) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(new DataBody<T>(message));
    }

    /**
     * 상태 코드 : accepted (202) : 클라이언트의 요청은 정상적이나, 서버가 아직 요청을 완료하지 못했다.
     * @param data
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponseEntity<DataBody<T>> accepted(@Nullable T data, String message) {
        return  ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new DataBody<T>(data, message));
    }

    /**
     * 상태 코드 : accepted (202) : 클라이언트의 요청은 정상적이나, 서버가 아직 요청을 완료하지 못했다.
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ResponseEntity<T> accepted(String message) {
        return (ResponseEntity<T>) ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new DataBody<T>(message));
    }


    @Builder
    @AllArgsConstructor
    @Getter
    public static class DataBody<T> {
        /**
         * HTTP 상태 코드, 데이터, 그리고 메시지를 이용하여 ResponseBody 객체를 생성하는 메서드
         *
         * @param data       응답 데이터
         * @param message    응답 메시지
         * @param <T>        데이터의 제네릭 타입
         * @return 생성된 ResponseBody 객체
         */

        private T data;
        private String message;

        public DataBody(String message) {
            this.message = message;
        }
    }
}
