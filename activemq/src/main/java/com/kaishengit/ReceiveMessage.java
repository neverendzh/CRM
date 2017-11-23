package com.kaishengit;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Administrator on 2017/11/22.
 */
@Component
public class ReceiveMessage implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("jsm-----"+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
