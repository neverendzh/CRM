package com.kaishengit.hibernate;

import com.kaishengit.pojo.Book;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/27.
 * 使用hibernate的示例
 */
public class hibernateDome {
    @Test
    public void saveHelloHibernate(){
//        1.读取classpath中名称为hibernate.cfg.xml的主配置文件
        Configuration configuration = new Configuration().configure();
//        2.创建SessionFaction工厂
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//        3创建Session
        Session session = sessionFactory.getCurrentSession();
//        4.创建事务
        Transaction transaction = session.beginTransaction();
//        5.执行操作
        Book book = new Book();
        book.setBookIsbn("2374578");
        book.setBookName("Hibernate");
        book.setBookOuther("PoJo");
        book.setBookPerss("机械工业出版社");
        book.setBookTotal(100);
        book.setBookNowTotal(100);
        session.save(book);
//        6.提交回滚事务
        transaction.commit();
//        7.释放资源 默认会自动关闭，如果手动关闭则异常
        session.close();
    }



  @Test
  public void findById(){
        Session session = HibernateUtil.getSession();
//        开启事务
        session.beginTransaction();
        Book book = (Book) session.get(Book.class,18);
      System.out.println(book.toString());
//      提交事务
      session.getTransaction().commit();
  }

    @Test
    public void update(){
      Session session = HibernateUtil.getSession();
//      开启事务
      session.beginTransaction();
      Book book = (Book) session.get(Book.class,43);
      book.setBookIsbn("2374578");
      book.setBookName("Hibernate");
      book.setBookOuther("PoJo");
      book.setBookPerss("机械工业出版社");
      book.setBookTotal(100);
      book.setBookNowTotal(100);
      session.getTransaction().commit();
  }

    @Test
    public void delete(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Book book = (Book) session.get(Book.class,40);
        session.delete(book);
        session.getTransaction().commit();
  }
    @Test
    public void findAll(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

//        hql
        String hql = "from Book order by id desc";
        Query query = session.createQuery(hql);
        List<Book> bookList = query.list();

        for (Book book :bookList){
            System.out.println(book.toString());
        }
        session.getTransaction().commit();
    }
}
