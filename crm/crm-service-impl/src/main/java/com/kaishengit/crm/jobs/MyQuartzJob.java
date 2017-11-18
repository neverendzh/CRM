package com.kaishengit.crm.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author zh
 * Created by Administrator on 2017/11/17.
 * 执行的任务
 */
public class MyQuartzJob implements Job{
    @Override
    public void execute (JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Quartz Running.........");

    }
}
