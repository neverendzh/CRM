package com.kaishengit.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @author zh
 * Created by Administrator on 2017/11/22.
 * 测试发布订阅模式 Topic
 */
public class ActiveMQTopicTestCase {
    /**
     * 发送消息 发布订阅模式Topic
     * @throws JMSException
     */
    @Test
    public void sendMessageActiveMq() throws JMSException {
        //1.创建ConnectionFactory,用于生产Connection对象
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        //2.创建Connection对象
        Connection connection = connectionFactory.createConnection();
        //启动
        connection.start();
        //3.1创建session对象,创建对象是中boolean类型是的true，false，是否使用事务。如果使用事务则不会默认提交，需要commit
        //3.2.如果没有使用事务，设置的是false则不用commit，会默认提交，推荐使用事务，
        //3.3.第二个参数，签收消息的模式，设置为AUTO_ACKNOWLEDGE则是自动签收。
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);

        //4创建Destination对象，目的地对象
        Destination destination = session.createTopic("Active-hello-topic");

        //5创建消息生产者
        MessageProducer messageProducer = session.createProducer(destination);

        for(int i = 4;i<=9;i++) {
            //6创建消息
            TextMessage message = session.createTextMessage("hello-activemq-8"+i);
            //7发送消息，message表示发送的消息,DeliveryMode.PERSISTENT表示持久化,i表示优先级,0表示存活的时间是永久的。
            //如果是不是0那么单位是毫秒，存活的毫秒数
            messageProducer.send(message, DeliveryMode.PERSISTENT,i,0);
        }
        session.commit();

        //释放资源
        messageProducer.close();
        session.close();
        connection.close();
    }

    /**
     * 消费消息 发布订阅模式Topic
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void customerMessageActiveMq() throws JMSException, IOException {
//        创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
//        创建connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
//        创建session对象，CLIENT_ACKNOWLEDGE客户端签收
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
//        创建目的地
        Destination destination = session.createTopic("Active-hello-topic");
//        创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
//        消费信息，监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("---->"+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        //释放资源
        messageConsumer.close();
        session.close();
        connection.close();


    }



}
