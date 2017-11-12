package com.kaishengit.crm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zh
 * Created by Administrator on 2017/11/12.
 * 403异常
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException{
    public ForbiddenException (){}

    public ForbiddenException(String message){
        super(message);
    }
}
