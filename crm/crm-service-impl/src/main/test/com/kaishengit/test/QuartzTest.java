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
      /*  //定义Job
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

        System.in.read();*/

    }


    @Test
    public void conTrigger() throws SchedulerException, IOException {
       /* //定义Job,也就是需要执行的任务
        JobDataMap dataMap = new JobDataMap();
        //使用putAsString存值，取值时使用getIntegerFromString取值
        dataMap.putAsString("accountId",1000);
        //使用put存值时，取值时使用getInt取值
        dataMap.put("put",2000);
        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class).setJobData(dataMap).build();

        //定义Trigger,也就是触发器，scheduleBuilder在什么时间执行
        ScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ? *");//CRON表达式
        //把在什么时间执行的ScheduleBuilder传给触发器，这样一个触发器就定义好了。
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();
        //创建调度者对象
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        //由调度者对象调用scheduleJob()方法，把job需要执行的任务，触发器Trigger传给调度者对象
        scheduler.scheduleJob(jobDetail,trigger);
        //运行程序
        scheduler.start();

        System.in.read();*/

    }
}


