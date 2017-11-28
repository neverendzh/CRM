package com.kaishengit.hibernate;

import com.kaishengit.pojo.Address;
import com.kaishengit.pojo.User;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * 央射关系
 * Created by Administrator on 2017/11/28.
 */
public class OneToManyTest {
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



    /**
     * fetch="join"配置address 这是就会在查询是吧User查出来
     * 一条sql
     * 如果不配置这个就是在需要User对象是在去查询两条sql
     */
    @Test
    public void findAddress(){
        Address address = (Address) session.get(Address.class,2);
        System.out.println(address.getCityName()+"-->"+address.getAddress());

        User user = address.getUser();
        System.out.println(user.getName()+"-->"+user.getId());
    }

    @Test
    public void findUser(){
        User user = (User) session.get(User.class,1);
        System.out.println(user.getName());
        Criteria criteria = session.createCriteria(Address.class);
        criteria.add(Restrictions.eq("user.id",user.getId()));

        List<Address> addressList = criteria.list();
        for (Address address : addressList){
            System.out.println(address.getCityName()+address.getAddress());
        }
  }


    @Test
    public void findUser2(){
        User user = (User) session.get(User.class,1);
        System.out.println(user.getName());
        Set<Address> addressSet = user.getAddressSet();
        for (Address address : addressSet){
            System.out.println(address.getId()+address.getCityName()+address.getAddress());
        }
    }

}