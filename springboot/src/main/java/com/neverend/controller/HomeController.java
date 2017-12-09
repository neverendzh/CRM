package com.neverend.controller;

import com.neverend.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/12/7.
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, HttpSession session){
        model.addAttribute("message","SpringBoot-Demo-Thymeleaf视图");
        User user = new User(100,"张三","湖北河畔");
        model.addAttribute("user",user);
        session.setAttribute("msg","Session Message");

        List<String> userNameList = Arrays.asList("aa","bb","cc","dd");
        model.addAttribute("userNameList",userNameList);
        return "index";
    }


}