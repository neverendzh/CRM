package com.kaishengit.hibernate;

import com.kaishengit.pojo.Book;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/28.
 * Hibernate的原声SQL使用
 *
 * 用于在追求性能情况下使用，使用该方式，表示已经放弃了Hibernate的可移植性
 *
 */
public class NativeSqlTest {

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

    /**
     * 查询全部返回一个Object对象
     */
    @Test
    public void findAll(){
        String sql = "select * from book";
        SQLQuery sqlQuery = session.createSQLQuery(sql);

        List<Object[]> list = sqlQuery.list();
        for (Object[] data : list){
            System.out.println(Arrays.toString(data));
        }
    }



    /**
     *返回一个实体对象
     */
    @Test
    public void findAllToPojo(){
        String sql = "select * from book";
        SQLQuery sqlQuery = session.createSQLQuery(sql)
                .addEntity(Book.class);

        List<Book> list = sqlQuery.list();
        showList(list);
    }


    /**
     * ：id 赋值
     */
    @Test
    public void findBy(){
        //：id 赋值
       String sql = "select * from book where id = :id order BY id DESC ";

        SQLQuery sqlQuery = session.createSQLQuery(sql)
                .addEntity(Book.class);
        sqlQuery.setParameter("id",18);
        List<Book> list = sqlQuery.list();
        showList(list);
    }

    /**
     * ？ 赋值
     */
    @Test
    public void findBy2(){
        //：id 赋值
        String sql = "select * from book where id = ? order BY id DESC ";

        SQLQuery sqlQuery = session.createSQLQuery(sql)
                .addEntity(Book.class);
        sqlQuery.setParameter(0,18);
        List<Book> list = sqlQuery.list();
        showList(list);
    }



    public void showList(List<Book> bookList){
        for(Book book : bookList){
            System.out.println(book.toString());
        }
    }

}