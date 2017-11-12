package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Account;

import javax.servlet.http.HttpSession;

/**
 * @author zh
 * Created by Administrator on 2017/11/12.
 * 获取当前用户作为父类存在
 */
public abstract class BaseController {

    /**
     * 获取当前登录用户的对象
     * @param httpSession
     * @return
     */
    public Account getCurrentAccount(HttpSession httpSession){
        return (Account) httpSession.getAttribute("curr_account");

    }

}
