package com.kaishengit.hibernate;

import com.kaishengit.pojo.Book;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/28.
 * hibernate中的hql语句查询
 */
public class HibernateHqlTest {
    private Session session;

    @Before
    public void before(){
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }


    @After
    public void after(){
        session.getTransaction().commit();
    }

    @Test
    public void findAll(){
        String hql = "from Book";
        Query query = session.createQuery(hql);
//        返回一个集合
        List<Book> bookList = query.list();
        showList(bookList);
    }

    /**
     * 站位符？
     */
   @Test
   public void findById(){
        String hql = "from Book where id = ?";
        Query query = session.createQuery(hql);
//        给问好赋值，从零开始，sql是从1开始
        query.setParameter(0,18);
//        如果返回的是唯一的则使用uniqueResult()方法
        Book book = (Book) query.uniqueResult();
        System.out.println(book.toString());
   }

    /**
     * 引用占位符
     */
   @Test
   public void findByName(){
//        使用：开头，给占位符声明一个名字
       String hql = "from Book where bookName = :name";
       Query query = session.createQuery(hql);
//       给站位符赋值使用setParameter()
//       query.setParameter("name","哈利波特");
//       也可以使用setString(),使用对应参数数据类型赋值
       query.setString("name","哈利波特");
       List<Book> books = query.list();
       showList(books);
   }

    /**
     * 查询一列中的值
     */
  @Test
  public void findSingleNameColumn(){
//       查询一列中的值
    String hql = "select bookName from Book";

    Query query = session.createQuery(hql);
    List<String> bookNameList = query.list();

    for(String name : bookNameList){
        System.out.println(name);
    }
  }



    /**
     * 查询多列中的值,返回一个集合，集合中装的是数组
     */
    @Test
    public void findIdAndNameColumn(){
//       查询一列中的值
        String hql = "select id,bookName from Book";

        Query query = session.createQuery(hql);
        List<Object[]> dataList = query.list();

        for(Object[] array : dataList){
            System.out.println(array[0]+"-->"+array[1]);
        }
    }


    /**
     * 函数
     */
    @Test
    public void count(){
        String hql = "select count(*),max(bookTotal) from Book";
        Query query = session.createQuery(hql);

        Object[] date = (Object[]) query.uniqueResult();
        System.out.println(date[0]+"-->"+date[1]);
    }

    /**
     * 分页
     */
    @Test
    public void page(){
        String hql = "from Book order by id desc";
        Query query = session.createQuery(hql);
//        相当于limit 0,5
        query.setFirstResult(0);
        query.setMaxResults(5);
//        返回一个集合
        List<Book> bookList = query.list();
        showList(bookList);
    }



    public void showList(List<Book> bookList){
        for(Book book : bookList){
            System.out.println(book.toString());
        }
    }
}