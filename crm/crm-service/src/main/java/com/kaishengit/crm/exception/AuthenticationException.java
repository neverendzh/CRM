package com.kaishengit.crm.exception;

/**
 * @author zh
 * 账号登录异常
 */
public class AuthenticationException extends RuntimeException {

    public AuthenticationException(){}

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable th) {
        super(th);
    }

    public AuthenticationException(Throwable th,String message) {
        super(message,th);
    }

}
