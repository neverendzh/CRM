package com.kaishengit.hibernate;

import com.kaishengit.pojo.Book;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/28.
 * 在hibernate中面向对象Criteria的查询方式
 */
public class HibernateCriteriaTest {

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
     *查询全部
     */
    @Test
    public void findAll(){
//        针对这个对象的查询
        Criteria criteria = session.createCriteria(Book.class);
//        返回一个List集合
         List<Book> bookList = criteria.list();
         showList(bookList);
    }


    /**
     * 增加条件查询
     */
    @Test
    public void findByWhere(){
        Criteria criteria = session.createCriteria(Book.class);


/*//        相当一增加where id = 18
        criteria.add(Restrictions.eq("id",18));
//        uniqueResult方法返回单个结果对象 list返回多个结果对象
        Book book = (Book) criteria.uniqueResult();

        System.out.println(book.toString());*/




//        相当于 where book_name like '哈利',criteria的add方法组合的多个where之间是and关系
        criteria.add(Restrictions.like("bookName","哈利", MatchMode.ANYWHERE));
//        添加多个条件
        criteria.add(Restrictions.eq("bookIsbn","21"));

        List<Book> bookList = criteria.list();
        showList(bookList);
    }

    /**
     * 增加条件或的查询
     */
    @Test
    public void findByOrWhere(){
        Criteria criteria = session.createCriteria(Book.class);
        //查询单列中多个条件，是或者的关系
//        criteria.add(Restrictions.in("bookTotal", Arrays.asList(20,100)));


//        查询多个列中的多个条件，是或的关系
        SimpleExpression bookNameWhere =  Restrictions.eq("bookName","活着");
        SimpleExpression bookTotalWhere = Restrictions.eq("bookTotal",100);
//        criteria.add(Restrictions.or(bookNameWhere,bookTotalWhere));





//        查询多个列中的多个条件，是或的关系
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(bookNameWhere);
        disjunction.add(bookTotalWhere);
        criteria.add(disjunction);


        List<Book> bookList = criteria.list();
        showList(bookList);
    }


    /**
     * 排序
     */
    @Test
    public void findAndOrder(){
//        针对这个对象的查询
        Criteria criteria = session.createCriteria(Book.class);

//        Order对象有desc和asc两个静态方法，用于倒叙和正序排列
        criteria.addOrder(Order.desc("id"));
        criteria.addOrder(Order.desc("bookTotal"));
//        返回一个List集合
        List<Book> bookList = criteria.list();
        showList(bookList);
    }


    /**
     * 分页方法
     */
    @Test
    public void page(){
//        针对这个对象的查询
        Criteria criteria = session.createCriteria(Book.class);

//        Order对象有desc和asc两个静态方法，用于倒叙和正序排列
        criteria.addOrder(Order.desc("id"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(5);
//        返回一个List集合
        List<Book> bookList = criteria.list();
        showList(bookList);
    }

    /**
     * 查询一个表中有多少列
     */
      @Test
    public void count(){
//        针对这个对象的查询
        Criteria criteria = session.createCriteria(Book.class);
//        相当于count(*),同过set不能同时添加多个条件的
        criteria.setProjection(Projections.rowCount());

        Long cout = (Long) criteria.uniqueResult();
        System.out.println("count-->"+cout);

    }


    /**
     * 多个列条件的添加
     */
    @Test
    public void countWhere(){

//      针对这个对象的查询
        Criteria criteria = session.createCriteria(Book.class);

        ProjectionList projectionList = Projections.projectionList();

        projectionList.add(Projections.rowCount());
        projectionList.add(Projections.avg("bookTotal"));

        criteria.setProjection(projectionList);

        Object[] data = (Object[]) criteria.uniqueResult();
        System.out.println("count-->"+data[0]);
        System.out.println("ACG-->"+data[1]);

    }



    public void showList(List<Book> bookList){
        for(Book book : bookList){
            System.out.println(book.toString());
        }
    }



}