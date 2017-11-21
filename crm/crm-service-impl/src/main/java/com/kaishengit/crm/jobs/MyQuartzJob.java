package com.kaishengit.crm.jobs;

import com.kaishengit.crm.entity.Account;
import org.quartz.Job;
import org.quartz.JobDataMap;
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
        JobDataMap dataMap  = jobExecutionContext.getJobDetail().getJobDataMap();
        Integer accountId = dataMap.getIntegerFromString("accountId");
        //int put = dataMap.getInt("put");
        System.out.println("accountId-----"+accountId);
        //System.out.println("put-----"+put);
        System.out.println("Quartz Running.........");

    }
}
