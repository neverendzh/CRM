package com.kaishengit.crm.jobs;

import com.kaishengit.crm.service.impl.util.MySpringContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author zh
 * Created by Administrator on 2017/11/18.
 * 定时发送消息的提醒任务
 */
public class SendMessageJob implements Job {

    private Logger logger = LoggerFactory.getLogger(SendMessageJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String message = (String) dataMap.get("message");
        Integer accountId = dataMap.getInt("accountId");
        logger.info("To:{} message:{}",accountId,message);

        try {

            ApplicationContext applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("springApplicationContext");

           JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");

            jmsTemplate.send("weixinMessage-Queue", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    String json = "{\"id\":\"" + accountId + "\",\"message\":\"" + message + "\"}";

                    TextMessage textMessage = session.createTextMessage(json);
                    return textMessage;
                }
            });
       } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
}
