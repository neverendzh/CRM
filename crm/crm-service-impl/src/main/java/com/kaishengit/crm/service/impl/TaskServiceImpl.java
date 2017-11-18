package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.example.TaskExample;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.jobs.SendMessageJob;
import com.kaishengit.crm.mapper.TaskMapper;
import com.kaishengit.crm.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 保存新的代办事项
     *
     * @param task
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewTask(Task task) {
        task.setDone((byte)0);
        task.setCreateTime(new Date());

        taskMapper.insert(task);
        logger.info("创建新的代办事项{}",task.getTitle());


        if(StringUtils.isNoneEmpty(task.getRemindTime())){
            //添加新的调度任务 JobData
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.putAsString("accountId",task.getAccountId());
            jobDataMap.put("message",task.getTitle());
            JobDetail jobDetail = JobBuilder.newJob(SendMessageJob.class).setJobData(jobDataMap).withIdentity(new JobKey("taskID:"+task.getId(),"sendMessageGroup")).build();

            //把拿到设定的提醒时间转换成CRON表达式
            DateTimeFormatter formatter  = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
            //string->DateTime类型
            DateTime dateTime = formatter.parseDateTime(task.getRemindTime());

            StringBuilder cron = new StringBuilder("0")
                    .append(" ")
                    .append(dateTime.getMinuteOfHour())
                    .append(" ")
                    .append(dateTime.getHourOfDay())
                    .append(" ")
                    .append(dateTime.getDayOfMonth())
                    .append(" ")
                    .append(dateTime.getMonthOfYear())
                    .append(" ? ")
                    .append(dateTime.getYear());
   logger.info("CRON EX: {}",cron.toString());



            //创建触犯器Trigger
            ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron.toString());
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            //创建调度者
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.scheduleJob(jobDetail,trigger);
                scheduler.start();
            } catch (Exception e) {
                throw new ServiceException(e,"添加定时任务异常");
            }



        }
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
     * 根据id查找代办事项
     *
     * @param id
     * @return
     */
    @Override
    public Task findTaskId(Integer id) {

        return taskMapper.selectByPrimaryKey(id);

    }


    /**
     * 根基id删除代办事项
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteById(Integer id) {
        Task task = findTaskId(id);
         taskMapper.deleteByPrimaryKey(id);
        //删除定时任务
        if(StringUtils.isNotEmpty(task.getRemindTime())) {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            try {
                scheduler.deleteJob(new JobKey("taskID:" + id, "sendMessageGroup"));
                logger.info("成功删除定时任务 ID:{} groupName:{}" ,id,"sendMessageGroup");
            } catch (Exception ex) {
                throw new ServiceException(ex,"删除定时任务异常");
            }
        }

    }


}
