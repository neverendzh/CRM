package com.neverend;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author
 * Created by Administrator on 2017/12/8.
 */
public class AppRun {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-dubbo-consumer.xml");
        context.start();
        System.out.println("CommodityService---start......");
        System.in.read();
    }
}