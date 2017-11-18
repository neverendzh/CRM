package com.kaishengit.test;

import com.kaishengit.crm.jobs.MyTimeTask;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

/**
 * @author zh
 * Created by Administrator on 2017/11/17.
 * 测试任务调度的方法
 */
public class TimeTaskTest {

    @Test
    public void timeTest() throws IOException {
        Timer timer = new Timer();
        //延迟0毫秒，每隔100毫秒执行一次
        //timer.schedule(new MyTimeTask(),0,100);


        //延迟2000毫秒执行一次
        //timer.schedule(new MyTimeTask(),2000);


        //在指定的时间执行一次,这个表示当前时间
        //timer.schedule(new MyTimeTask(),new Date());


        //从执定的时间开始,没隔2000毫秒执行一次
        timer.schedule(new MyTimeTask(),new Date(),2000);

        //表示程序不会退出
        System.in.read();


    }

}
