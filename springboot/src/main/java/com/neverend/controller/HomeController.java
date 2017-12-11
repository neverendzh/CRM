package com.neverend.controller;

import com.neverend.dao.UserDao;
import com.neverend.entity.Person;
import com.neverend.entity.User;
import com.neverend.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/12/7.
 */
@Controller
public class HomeController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PersonMapper personMapper;

    @GetMapping("/save1")
    @ResponseBody
    public String saveAccount1(){
        Person person = new Person();
        person.setId(31);
        person.setPersonName("mybatis-SpringBoot");
        personMapper.save(person);
        return "hi";
    }


    @GetMapping("/save2")
    @ResponseBody
    public String saveAccount2(){
        userDao.save(29,"ZhangSan");
        return "hi";
    }
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