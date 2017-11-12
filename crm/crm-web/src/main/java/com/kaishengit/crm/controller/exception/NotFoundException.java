package com.kaishengit.crm.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author zh
 * Created by Administrator on 2017/11/12.
 * 404
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(){}
    public NotFoundException(String message){
        super(message);
    }
}
