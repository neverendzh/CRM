package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Task;

import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/17.
 * 新增代办事项的接口
 */
public interface TaskService {

    /**
     * 保存新的代办事项
     * @param task
     */
    void saveNewTask(Task task);

    /**
     * 根据哟用户ID查找对应的代办事项
     * @param id
     * @return
     */
    List<Task> findTaskByAccountId(Integer id);

    /**
     * 根基id删除代办事项
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id查找代办事项
     * @param id
     * @return
     */
    Task findTaskId(Integer id);
}
