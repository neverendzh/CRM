package com.kaishengit.exception;

/**
 * @author zh
 * Created by Administrator on 2017/11/21.
 * 微信异常
 */
public class WeiXinException extends RuntimeException{
    public WeiXinException (){}
    public WeiXinException (String msg){
         super(msg);
    }

}
