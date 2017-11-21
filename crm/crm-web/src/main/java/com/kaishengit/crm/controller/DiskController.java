package com.kaishengit.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zh
 * Created by Administrator on 2017/11/20.
 * 公司网盘的控制器层
 */
@Controller
@RequestMapping("/disk")
public class DiskController  {

    @GetMapping("/home")
    public String diskHome(){
            return "disk/home";
    }

}
