package com.kaishengit.hibernate;

import com.kaishengit.pojo.Admin;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Table;

/**
 * @author zh
 * Created by Administrator on 2017/12/4.
 *hibernate基于注解形式的POJO类
 */
public class AnnotationAdminTest {

    private Session session;
    @Before
    public void before(){
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }

    @After
    public void after(){
        session.getTransaction().commit();;
    }


    @Test
    public void save(){
        Admin admin = new Admin();
        admin.setAdminName("小李子");
        admin.setAdminPassword("9999");
        session.save(admin);
    }

    @Test
    public void findById(){
        Admin admin = (Admin) session.get(Admin.class,37);
        System.out.println(admin);
    }
}