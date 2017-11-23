package com.kaishengit;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author zj
 * Created by Administrator on 2017/11/22.
 * 基于注解的监听器
 */
@Component
public class SpringConsumer {

    @JmsListener(destination = "wei-xinQueue")
    public void doSomething(String message){

        System.out.println("----"+message);
    }
}
