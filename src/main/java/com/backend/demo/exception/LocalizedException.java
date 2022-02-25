package com.backend.demo.exception;

import lombok.Getter;

@Getter
public class LocalizedException extends RuntimeException {

   /* protected final LocalizedExceptionCode code;

    public LocalizedException(LocalizedExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }*/
}
