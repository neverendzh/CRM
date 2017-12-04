package com.kaishengit.hibernate;

import com.kaishengit.pojo.Customer;
import com.kaishengit.pojo.User;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author zh
 * Created by Administrator on 2017/12/4.
 * 演示hibernate中的锁机制
 */
public class HibernateLockTest {

    private Session session;

   /* @Before
    public void before(){
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }*/


  /*  @After
    public void after(){
        session.getTransaction().commit();
    }
*/
    @Test
    public void save(){
        Customer customer = new Customer();
        customer.setName("jak");
        session.save(customer);
    }


    /**
     *乐观锁机制，在事务提价时会校验version字段的值，如果不是查询时的值则提交失败。
     * @throws InterruptedException
     */
    @Test
    public void update() throws InterruptedException {
        Customer customer = (Customer) session.get(Customer.class,"402881af601f95cb01601f95d13d0000");
        customer.setName("tom");
        Thread.sleep(10000);
        session.saveOrUpdate(customer);
    }


    /**
     * 悲观锁，是在查询是将行锁定，当前事务不完成，其他事务无法提交
     */
    @Test
    public void update1(){

        session = HibernateUtil.getSession();
        session.beginTransaction();
//        设置悲观锁
        User user = (User) session.get(User.class,1, LockOptions.UPGRADE);
        user.setName("tom");

//        子线程
        Thread thread = new Thread(new Runnable() {
            public void run() {
                Session session1 = HibernateUtil.getSession();
                session1.beginTransaction();
                User user1 = (User) session1.get(User.class,1);
                user1.setName("xiaoxiao");
                session1.getTransaction().commit();


                session1.getTransaction().commit();
            }
        });
        thread.start();
        session.getTransaction().commit();
    }
}
