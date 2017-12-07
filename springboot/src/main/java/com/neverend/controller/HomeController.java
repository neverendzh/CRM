package com.neverend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zh
 * Created by Administrator on 2017/12/7.
 */
@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "hello SpringBoot";
    }

}