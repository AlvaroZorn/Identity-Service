package com.zorn.identityservice.exception;

public class IdentityServiceException extends RuntimeException {
    public IdentityServiceException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public IdentityServiceException(String exMessage) {
        super(exMessage);
    }
}
