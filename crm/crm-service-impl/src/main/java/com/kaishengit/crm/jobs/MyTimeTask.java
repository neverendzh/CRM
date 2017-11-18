package com.kaishengit.crm.jobs;

import java.util.TimerTask;

/**
 * @author zh
 * Created by Administrator on 2017/11/17.
 *
 */
public class MyTimeTask extends TimerTask {
    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {

        System.out.println("hello ,TimeTaskTest--------->");
    }
}
