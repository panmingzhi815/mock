package com.yinuo.mock.base;

public class SocketServiceException extends Exception {
    public SocketServiceException() {
        super();
    }

    public SocketServiceException(String message) {
        super(message);
    }

    public SocketServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocketServiceException(Throwable cause) {
        super(cause);
    }

    protected SocketServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
