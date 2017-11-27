package com.kaishengit.util;

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
    private static SessionFactory sessionFactory = builderSessionFactory();

    private static SessionFactory builderSessionFactory() {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory(){
        return builderSessionFactory();
    }

    public static Session getSession(){
        return getSessionFactory().getCurrentSession();
    }

}
