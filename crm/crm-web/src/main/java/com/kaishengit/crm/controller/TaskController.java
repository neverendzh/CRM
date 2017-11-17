package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zh
 * Created by Administrator on 2017/11/16.
 * 代办事项的控制器
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;



    /**
     * 显示所有的代办事项
     * @return
     */
    @GetMapping
    public String taskList(){
        return "/task/home";
    }

    /**
     * 添加代办事项
     * @return
     */
    @GetMapping("/new")
    public String newTask(){
        return "/task/new";
    }

    @PostMapping("/new")
    public String newTask(Task task){

        return "redirect:/task";
    }



}
