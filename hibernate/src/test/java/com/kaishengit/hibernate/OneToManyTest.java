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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 央射关系 一对多保存与删除
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

    /**
     * 查询出一个User中对应的多个地址
     */
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

    /**
     * 保存用户
     */
    @Test
    public void saveUser(){
        User user = new User();
        user.setName("小王");
        session.saveOrUpdate(user);
    }

    /**
     * 多对一的添加收货地址
     */
    @Test
    public void saveAddress(){
        User user = (User) session.get(User.class,9);

        Address address = new Address();
        address.setCityName("上海");
        address.setAddress("浦东区");

        address.setUser(user);

        session.save(address);
    }


    /**
     * 一个人对应对个收货地址的保存
     */
    @Test
    public void save(){

        User user = new User();
        user.setName("tom");

        Address address = new Address();
        address.setCityName("纽约");
        address.setAddress("莫宁路110号");
        address.setUser(user);

        Address address1 = new Address();
        address1.setCityName("南京");
        address1.setAddress("小胡同");
        address1.setUser(user);

//        包Address放入set集合中在传给User对象这样就可以使User维护关联关系，
//        如果在User.hbm.xml中配置inverse="true"则不会维护关联关系，这样写了也没用。
      /*  Set<Address> addressSet = new HashSet<Address>();
        addressSet.add(address);
        addressSet.add(address1);
        user.setAddressSet(addressSet);*/

//      最佳实践，在保存这种一对多的数据时，应该先存一在存多。只需要让多的一端去维护关系
        session.save(user);
        session.save(address);
        session.save(address1);
    }


    /**
     *删除有外键约束的的数据
     *Address的外键user_id是User表中id的值，先删除User对应在Address表中的收获地址。
     *再删除User用户
     */
    @Test
    public void delete(){
        Criteria criteria = session.createCriteria(Address.class);
        criteria.add(Restrictions.eq("user.id",11));
        List<Address> addressList = criteria.list();
        for(Address address : addressList){
            session.delete(address);
        }

        User user = (User) session.get(User.class,11);
        session.delete(user);
    }

    @Test
    public void delete2(){
        User user = (User) session.get(User.class,11);
        session.delete(user);
    }
}