package com.kaishengit.crm.controller;

import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/16.
 * 代办事项的控制器
 */
@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;



    /**
     * 显示所有的代办事项
     * @return
     */
    @GetMapping
    public String taskList(Model model,
                           HttpSession httpSession){
        Account account = getCurrentAccount(httpSession);
        List<Task> taskList = taskService.findTaskByAccountId(account.getId());
        model.addAttribute("taskList",taskList);
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

    /**
     * 保存代办事项
     * @param task
     * @return
     */
    @PostMapping("/new")
    public String newTask(Task task){
        taskService.saveNewTask(task);
        return "redirect:/task";
    }


    @GetMapping("/{id:\\d}/del")
    public String deleteTask(@PathVariable Integer id,
                             HttpSession httpSession){
        Account account = getCurrentAccount(httpSession);

         Task task = taskService.findTaskId(id);
        if (task == null){
            throw new NotFoundException();
        }
        if(!task.getAccountId().equals(account.getId())){
            throw new ForbiddenException();
        }
        taskService.deleteById(id);
        return "redirect:/task";
    }



}
