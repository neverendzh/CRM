package com.helloword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author  zh
 * Created by Administrator on 2017/12/12.
 */
@Controller
@RequestMapping("/home")
public class LogIndex {
    @PostMapping
    public String home(){
        return "index";
    }
}