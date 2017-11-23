package com.kaishengit.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @author zh
 * Created by Administrator on 2017/11/22.
 * 测试activeMQ的消息服务。主要用于异步通讯
 */
public class ActiveMQTestCase {

    /**
     * 发送消息
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
        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);

        //4创建Destination对象，目的地对象
        Destination destination = session.createQueue("message-test");

        //5创建消息生产者
        MessageProducer messageProducer = session.createProducer(destination);

        //5.5设置持久化模式
        //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);


        //7发送消息
        //messageProducer.send(message);
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
     * 消费消息
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
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
//        创建目的地
        Destination destination = session.createQueue("message-test");
//        创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
//        消费信息，监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("---->"+textMessage.getText());
//                    手动签收消息，对列删除
                    textMessage.acknowledge();
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



    /**
     * 测试重试机制发送消息rollback()
     * @throws JMSException
     */
    @Test
    public void sendMessageActiveMqRedelivery1() throws JMSException {
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
        Destination destination = session.createQueue("message-test");

        //5创建消息生产者
        MessageProducer messageProducer = session.createProducer(destination);

        //5.5设置持久化模式
        //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);


        //7发送消息
        //messageProducer.send(message);
        for(int i = 4;i<=9;i++) {
            //6创建消息
            TextMessage message = session.createTextMessage("hello-activemq-"+i);
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
     * 测试重试机制的消费消息 rollback()
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void customerMessageActiveMqRedelivery1() throws JMSException, IOException {
//        创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
//        创建connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
//        创建session对象，CLIENT_ACKNOWLEDGE客户端签收,如果是true则需要手动提价事务也就是commit
        Session session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
//        创建目的地
        Destination destination = session.createQueue("message-test");
//        创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
//        消费信息，监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    if("hello-activemq-8".equals(text)){
                        throw  new JMSException("用于测试重试机制故意引发的异常");
                    }
                    System.out.println("---->"+textMessage.getText());
                    session.commit();
                } catch (JMSException e) {
                    try {
                        session.rollback();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
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



    /**
     * 测试重试机制发送消息 recover
     * @throws JMSException
     */
    @Test
    public void sendMessageActiveMqRedelivery2() throws JMSException {
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
        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);

        //4创建Destination对象，目的地对象
        Destination destination = session.createQueue("message-test");

        //5创建消息生产者
        MessageProducer messageProducer = session.createProducer(destination);

        //5.5设置持久化模式
        //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);


        //7发送消息
        //messageProducer.send(message);
        for(int i = 4;i<=9;i++) {
            //6创建消息
            TextMessage message = session.createTextMessage("hello-activemq-"+i);
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
     * 测试重试机制的消费消息 recover
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void customerMessageActiveMqRedelivery2() throws JMSException, IOException {
//        创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
//        创建connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
//        创建session对象，CLIENT_ACKNOWLEDGE客户端签收,如果是true则需要手动提价事务也就是commit
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
//        创建目的地
        Destination destination = session.createQueue("message-test");
//        创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
//        消费信息，监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    if("hello-activemq-8".equals(text)){
                        throw  new JMSException("用于测试重试机制故意引发的异常");
                    }
                    //如果没有异常则签收
                    textMessage.acknowledge();
                    System.out.println("---->"+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                    try {
                        //已发异常，触发重新投递机制
                        session.recover();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        //释放资源
        messageConsumer.close();
        session.close();
        connection.close();


    }



    /**
     * 发送消息 测试自动签收机制的，不捕获异常引发的重新投递机制
     * @throws JMSException
     */
    @Test
    public void sendMessageActiveMq3() throws JMSException {
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
        Destination destination = session.createQueue("message-test");

        //5创建消息生产者
        MessageProducer messageProducer = session.createProducer(destination);

        //5.5设置持久化模式
        //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);


        //7发送消息
        //messageProducer.send(message);
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
     * 消费消息 测试自动签收机制的，不捕获异常引发的重新投递机制
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void customerMessageActiveMq3() throws JMSException, IOException {
//        创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
//        创建connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
//        创建session对象，CLIENT_ACKNOWLEDGE客户端签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//        创建目的地
        Destination destination = session.createQueue("message-test");
//        创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
//        消费信息，监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    if("hello-activemq-87".equals(text)){
                    throw new JMSException("用于测试自动签收的环境中，引发异常的重新投递机制");
                    }
                    System.out.println("---->"+textMessage.getText());
                } catch (JMSException e) {
                    throw new RuntimeException("用于测试自动签收的环境中，引发异常的重新投递机制");
                }
            }
        });
        System.in.read();
        //释放资源
        messageConsumer.close();
        session.close();
        connection.close();


    }

    /**
     * 消费消息 测试自动签收机制的，不捕获异常引发的重新投递机制
     * 自定义重试机制
     * @throws JMSException
     * @throws IOException
     */
    @Test
    public void customerMessageActiveMq4() throws JMSException, IOException {
//        创建ConnectionFactory
        String brokerUrl = "tcp://localhost:61616";
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

//        自定义重试机制
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
//        自定义重试此时
        redeliveryPolicy.setMaximumRedeliveries(4);
//        第一次重试的延迟时间
        redeliveryPolicy.setInitialRedeliveryDelay(3000);
//        每次投递的延迟时间
        redeliveryPolicy.setRedeliveryDelay(3000);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

//        创建connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
//        创建session对象，CLIENT_ACKNOWLEDGE客户端签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
//        创建目的地
        Destination destination = session.createQueue("message-test");
//        创建消费者
        MessageConsumer messageConsumer = session.createConsumer(destination);
//        消费信息，监听队列中的消息，如果有新的消息，则会执行onMessage方法
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    if("hello-activemq-87".equals(text)){
                        throw new JMSException("用于测试自动签收的环境中，引发异常的重新投递机制");
                    }
                    System.out.println("---->"+textMessage.getText());
                } catch (JMSException e) {
                    throw new RuntimeException("用于测试自动签收的环境中，引发异常的重新投递机制");
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
