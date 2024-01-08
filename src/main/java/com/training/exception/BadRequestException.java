package com.training.exception;

import java.io.Serial;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final SysError sysError;

    public BadRequestException(SysError sysError) {
        this.sysError = sysError;
    }

    public BadRequestException(String message, Throwable throwable, SysError sysError) {
        super(message, throwable);
        this.sysError = sysError;
    }

    public BadRequestException(Throwable throwable, SysError sysError) {
        super(throwable);
        this.sysError = sysError;
    }

    public BadRequestException(String message, SysError sysError) {
        super(message);
        this.sysError = sysError;
    }

}
