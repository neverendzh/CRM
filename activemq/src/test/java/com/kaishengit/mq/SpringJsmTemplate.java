package com.kaishengit.mq;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author zh
 * Created by Administrator on 2017/11/22.
 * 测试吧ActiveMQ整合到Spring容器中的测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-jms.xml")
public class SpringJsmTemplate {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Test
    public void sendMassage(){
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
              TextMessage textMessage =  session.createTextMessage("hello-spring-jms");
              return textMessage;
            }
        });
    }

    @Test
    public void sendMassageTopic(){
        ActiveMQTopic topic = new ActiveMQTopic("active-topic");
        jmsTemplate.send(topic, new MessageCreator() {
        @Override
        public Message createMessage(Session session) throws JMSException {
            TextMessage textMessage = session.createTextMessage("hello-topic");
            return textMessage;
        }
    });
}
}
