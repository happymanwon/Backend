package org.hmanwon.global.common.exception;

import static org.hmanwon.global.common.exception.DefaultExceptionCode.BAD_REQUEST;
import static org.hmanwon.global.common.exception.DefaultExceptionCode.INTERNAL_SERVER_ERROR;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hmanwon.global.common.dto.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 예상한 예외들 잡는 곳
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
        DefaultException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleDefaultException(
        DefaultException e,
        HttpServletRequest request
    ) {
        log.error("custom error!!!! status : {} , errorCode : {}, message : {}, url : {}",
            e.getErrorCode().getStatus(),
            e.getErrorCode().getCode(),
            e.getErrorCode().getMsg(),
            request.getRequestURI()
        );

        return new ResponseEntity<>(
            ErrorResponseDTO.error(e.getErrorCode()),
            e.getErrorCode().getStatus()
        );
    }

    /**
     * 커스텀 에러를 제외한 badRequset 만 중점으로 처리하는 에러 핸들러
     *
     * @param
     * @param request
     * @return
     */
    @ExceptionHandler(value = {
        HttpRequestMethodNotSupportedException.class,
        HttpMessageNotReadableException.class,
        InvalidFormatException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(
        Exception e, HttpServletRequest request
    ) {
        log.error("request error url : {}, message : {}",
            request.getRequestURI(),
            e.getMessage()
        );

        return new ResponseEntity<>(
            ErrorResponseDTO.error(BAD_REQUEST),
            HttpStatus.BAD_REQUEST
        );
    }

    /**
     * @param e
     * @param request
     * @return
     * @RequestParam valid 할 때 예외 처리
     */
    @ExceptionHandler(value = {
        ConstraintViolationException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(
        ConstraintViolationException e, HttpServletRequest request
    ) {
        log.error("ConstraintViolationException error url : {}, message : {}",
            request.getRequestURI(),
            e.getMessage()
        );
        //searchTripListByKeyword.keyword: 검색어를 채워주세요 -> "검색어를 채워주세요" 반환.
        String[] msgList = e.getMessage().split(":");
        String msg = msgList[msgList.length - 1].substring(1);

        return new ResponseEntity<>(
            new ErrorResponseDTO(
                BAD_REQUEST.getStatus(),
                BAD_REQUEST.getCode(),
                msg
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    /**
     * @param e
     * @param request
     * @return
     * @Valid 를 통해 나타나는 예외 처리
     */
    @ExceptionHandler(value = {
        MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleArgumentNotVaildException(
        MethodArgumentNotValidException e, HttpServletRequest request
    ) {
        log.error("MethodArgumentNotValid error url : {}, message : {}",
            request.getRequestURI(),
            e.getMessage()
        );

        List<String> msgList = e.getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

        return new ResponseEntity<>(
            new ErrorResponseDTO(
                BAD_REQUEST.getStatus(),
                BAD_REQUEST.getCode(),
                String.join(", ", msgList)
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    /**
     * 기타 예외 처리
     *
     * @param e
     * @param request
     * @return
     */

    @ExceptionHandler(value = {
        Exception.class, RuntimeException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleException(
        Exception e, HttpServletRequest request
    ) {
        log.error("exception error class : {}, url : {}, message : {}"/*, trace : {}*/,
            e.getClass(),
            request.getRequestURI(),
            e.getMessage()
//            ,e.getStackTrace()
        );

        //개발중 예외처리 되지 않은 익셉션을 더 확실히 확인 할 수 있도록 스택트레이스 출력 
        e.printStackTrace();

        return new ResponseEntity<>(
            ErrorResponseDTO.error(INTERNAL_SERVER_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
