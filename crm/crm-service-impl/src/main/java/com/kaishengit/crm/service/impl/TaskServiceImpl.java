package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zj
 * Created by Administrator on 2017/11/17.
 * 新增代办事项的实现类
 */
@Service
public class TaskServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskMapper taskMapper;

    /**
     * 保存新的代办事项
     *
     * @param task
     */
    @Override
    public void saveNewTask(Task task) {
        task.setDone((byte)0);
        task.setCreateTime(new Date());

        taskMapper.insert(task);
        logger.info("创建新的代办事项{}",task.getTitle());

    }

    /**
     * 根据哟用户ID查找对应的代办事项
     *
     * @param id
     * @return
     */
    @Override
    public List<Task> findTaskByAccountId(Integer id) {
        TaskExample taskExample = new TaskExample();
        taskExample.createCriteria().andAccountIdEqualTo(id);
        taskExample.setOrderByClause("id desc");

        return taskMapper.selectByExample(taskExample);
    }

    /**
     * 根基id删除代办事项
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {

         taskMapper.deleteByPrimaryKey(id);

    }

    /**
     * 根据id查找代办事项
     *
     * @param id
     * @return
     */
    @Override
    public Task findTaskId(Integer id) {

        return taskMapper.selectByPrimaryKey(id);
    }
}
