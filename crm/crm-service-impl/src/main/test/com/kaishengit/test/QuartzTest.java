package com.kaishengit.test;

import com.kaishengit.crm.jobs.MyQuartzJob;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;

/**
 * @author zh
 * Created by Administrator on 2017/11/17.
 */
public class QuartzTest {
    @Test
    public void simpleTrigger() throws SchedulerException, IOException {
        //定义Job
        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).build();
        //定义Trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        scheduleBuilder.withIntervalInSeconds(5); //每隔5秒中执行
        scheduleBuilder.repeatForever();//永远重复
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(scheduleBuilder).build();
        //创建调度者对象
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        System.in.read();

    }

}


