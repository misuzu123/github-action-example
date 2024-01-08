package com.training.exception;

import com.training.dto.ResponseJson;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = {"com.training"})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String ERROR_MGS = "[training-app]: {}";

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseJson<Boolean>> exception(Exception ex) {
        LOG.error(ERROR_MGS, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseJson<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }

    @ResponseBody
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseJson<Boolean>> badRequestException(
            BadRequestException badRequestException) {
        String message = badRequestException.getMessage();
        LOG.error(ERROR_MGS, badRequestException.getMessage(), badRequestException);
        SysError ex = badRequestException.getSysError();
        if (ObjectUtils.isEmpty(ex)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseJson<>(message));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseJson<>(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex));
        }
    }

}
