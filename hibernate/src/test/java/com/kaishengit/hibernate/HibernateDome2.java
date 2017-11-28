package com.kaishengit.hibernate;

import com.kaishengit.pojo.Book;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.Test;

/**
 * @author zh
 * Created by zh on 2017/11/28.
 * POJO对象的生命周期
 * 三种状态：自由态
 *          持久态
 *          游离态
 */
public class HibernateDome2 {

    @Test
    public void get(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

//        1.查询 get() 方法和load()方法都是通过主键查询，get方法无论查询的对象是否使用都会去查询。
//        而load方法只有在对象在使用时才会去查询
//        2.如果使用get方法查询的对象不存在，那么返回的对象就是null
        Book book = (Book) session.get(Book.class,18);

//        System.out.println(book == null);
        session.getTransaction().commit();
        System.out.println(book.toString());
    }

    @Test
    public void load(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
//       1.查询,load()方法根据主键查询，如果对象不使用，则不会查询，是懒加载模式。
//       2.使用load方法不管查询的对象存在不存在返回的都是false,因为懒加载时，Hibernate会自动产生POJO的动态代理子类
//       3.Hibernate中的懒加载时。使用这个对象必须在session对象关闭之前
        Book book = (Book) session.load(Book.class,100);

        System.out.println(book == null);
//        System.out.println(book.toString());
        //提交事务，关闭session
        session.getTransaction().commit();
    }


    @Test
    public void save(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Book book = new Book();
        book.setBookIsbn("2374578");
        book.setBookName("Spring");
        book.setBookOuther("PoJo");
        book.setBookPerss("机械工业出版社");
        book.setBookTotal(100);
        book.setBookNowTotal(100);

//       会返回一个Integer类型的主键ID值
        /*Integer id = (Integer) session.save(book);
        System.out.println("ID-->"+id);*/

//        这个方法和save都是执行insert操作，唯一不同的就是这个方法不反会值，如果需要返回主键则直接在book对象中取就可以
        session.persist(book);

        int bookId = book.getId();
        System.out.println(bookId);
        session.getTransaction().commit();
    }

    /**
     * 使用save方法让自由态到持久态同步到数据库中
     * 使用update方法让游离态到持久态同步到数据库中
     */
   @Test
   public void updateMethod(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

//        在执行get方法之后进入到持久态
        Book book = (Book) session.get(Book.class,55);
        System.out.println(book.toString());
//处于持久态的session对象会自动把改变的值同步到数据库
        book.setBookTotal(200);
//如果session对象被关闭就会进入游离态，那么就不会同步到数据库，只重新回到持久态才会同步到数据库
        session.getTransaction().commit();

//  ----------------------------------------------------------------
//        在session对象关闭后进入游离态，重新获取session对象
        Session session1 = HibernateUtil.getSession();
//        开启事务
       session1.beginTransaction();
//        设值
        book.setBookTotal(300);
//        使用update方法让游离态进入持久态同步到数据库
       session1.update(book);
//       提交事务关闭session,进入游离态
       session1.getTransaction().commit();
   }


    /**
     * saveOrUpdate可以根据持久换对象的状态来判断是使用save方法还是update方法
     */
   @Test
   public void saveOrUpdate(){
       Session session = HibernateUtil.getSession();
       session.beginTransaction();

       Book book = (Book) session.get(Book.class,55);

       System.out.println(book.toString());
       book.setBookTotal(400);

       session.getTransaction().commit();

       Session session1 = HibernateUtil.getSession();
       session1.beginTransaction();

       book.setBookTotal(50);
//saveOrUpdate可以根据持久换对象的状态来判断是使用save方法还是update方法
       session1.saveOrUpdate(book);

       session1.getTransaction().commit();
   }


    /**
     * merge不改变持久化对象的状态
     */
    @Test
    public void merge(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Book book = new Book();
        book.setBookIsbn("2374578");
        book.setBookName("Spring-MVC");
        book.setBookOuther("PoJo");
        book.setBookPerss("机械工业出版社");
        book.setBookTotal(50);
        book.setBookNowTotal(100);
//使用merge方法之前是自由态，在使用merge方法之后还是自由态，merge方法不改变拿到持久化对象的状态，
        session.merge(book);
//所有不会改变数据库中的值，因为在这里持久化对象的状态是自由态
        book.setBookTotal(200);
        session.getTransaction().commit();

     Session session1 = HibernateUtil.getSession();
        session1.beginTransaction();

        book.setBookTotal(60);
     //使用merge方法之前是自由态，在使用merge方法之后还是自由态，merge方法不改变拿到持久化对象的状态，
        session1.merge(book);

        session1.getTransaction().commit();
    }


    @Test
    public void clear(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
//      持久态
        Book book = (Book) session.get(Book.class,18);
//      游离态,执行了clear方法，所有持久化对象进入了游离态
        session.clear();
        book.setBookTotal(10);
        session.getTransaction().commit();
    }


    /**
     * 删除，使用delete方法执行有对象进入自由态
     */
    @Test
    public void delete(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
//      持久态
        Book book = (Book) session.get(Book.class,60);
//      游离态,执行了clear方法，
        session.delete(book);

        session.getTransaction().commit();
    }


    @Test
    public void flush(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        Book book = (Book) session.get(Book.class,62);

        book.setBookTotal(10);
//        flush()方法将对象立即同步到数据库中，不改变对象的状态，在commit之前同步到数据库中
// 如果不使flush方法则在事务提交之后同步到数据库中
        session.flush();
        session.getTransaction().commit();
    }
}