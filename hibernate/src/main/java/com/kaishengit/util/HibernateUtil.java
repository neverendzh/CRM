package com.kaishengit.util;

import com.kaishengit.pojo.Post;
import javafx.geometry.Pos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 * @author zh
 * Created by Administrator on 2017/11/27.
 *此工具类用于创建hibernate的sessionFactory
 * session
 */
public class HibernateUtil {
//在静态方法中new一个对象返回，如果在外部类中多次调用这个静态方法，那么返回的就是不同的新对象，
//如果想得到同一个对象那么就需要定义一个静态属性，把静态方法返回的对象赋给静态变量，在第一次调用这个类中的今天方法时，那么
//就会把这个方法返回的对象赋给静态变量，且在内存中值存在一份，如果其他类用到这个属性时，那么虚拟机
//就会去寻找这个静态属性在第一次符给的值,值是不变的.值直存在一份
//    ------------------------------

    private static Post post1 = getPost();

    private static Post getPost(){
        return new Post();
    }
    public static Post getPost1(){
        return getPost();
    }

    public  static Post getPost2(){
        return post1;
    }

//    ---------------------------------

    private static SessionFactory sessionFactory = builderSessionFactory();

    private static SessionFactory builderSessionFactory() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }


    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static Session getSession(){

        return getSessionFactory().getCurrentSession();
    }

}
