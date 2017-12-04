package com.kaishengit.hibernate;

import com.kaishengit.pojo.*;
import com.kaishengit.util.HibernateUtil;
import net.sf.ehcache.Ehcache;
import org.hibernate.Cache;
import org.hibernate.Session;
import org.junit.Test;

/**
 * @author zh
 * Created by Administrator on 2017/11/30.
 */
public class CacheTest {



    /**
     * 一级缓存（内置缓存，在session中实现）
     */
    @Test
    public void firstLevelCache(){
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();

//      在同一个session中查询同一个对象多次，只会执行一次数据库查询，其他查询从缓存中取
//       session关闭后一级缓存失效
        User user = (User) session.get(User.class,1);


//      session的clean方法可以将所有一级缓存对象清空
//        session.clear();


//        session的evict方法可以将制定对象从一级缓存对象中清空
//        session.evict(user);



//      session对象的contains方法用于判断对象是否在一级缓存中
        boolean b = session.contains(user);

        user = (User) session.get(User.class,1);
        user = (User) session.get(User.class,1);

        session.getTransaction().commit();
    }




//Hibernate只定义了二级缓存的接口，未做实现，需要第三方的支持才可以使用
//如果设置并开启了二级缓存，查询数据时会先从二级缓存中查找，
//如果二级缓存中不存在再从一级缓存中查找，如果一级缓存不存在，则查询数据库，并将查询结果放入一级和二级缓存
//二级缓存在同一个sessionFactory产生的多个session实例间共享

    /**
     * 二级缓存实现,POJO 类需要可序列话的。用于咋内存不够是放入磁盘中
     */
    @Test
    public void secondLevelCache(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        EhcacheEntity ehcache = (EhcacheEntity) session.get(EhcacheEntity.class,1);

        System.out.println(ehcache.getName());
//        关闭session
        session.getTransaction().commit();

//        清空二级缓存evictEntityRegion()方法用于将执行对象从二级缓存中清楚
//        evictAllRegions清空所有缓存对象
        Cache cache = HibernateUtil.getSessionFactory().getCache();
        cache.evictEntityRegion(EhcacheEntity.class);
//        cache.evictAllRegions();


//      重新获取session
        Session session1 = HibernateUtil.getSession();
        session1.beginTransaction();
//      先从二级缓存中获取，如果二级缓存没有在去一级缓存中获取，如果一级缓存中没有再去数据库中查找
        EhcacheEntity ehcache1 = (EhcacheEntity) session1.get(EhcacheEntity.class,1);
        System.out.println(ehcache1.getName());
        session1.getTransaction().commit();


    }

    /**
     *测试两个相同静态方法产生对象是否相等
     */
    @Test
    public void test(){
        Post post = HibernateUtil.getPost1();
        Post post1 = HibernateUtil.getPost1();
        if(post==post1){
            System.out.println("true");
        }else{
            System.out.println(post);
            System.out.println(post1);
        }
    }


    /**
     * 测试两个相同静态方法返回一个实例中的静态属性是否相等。
     * 这个静态属性是有一个静态方法new出来的一个对象
     *
     * 测试结果，在一个方法中，一个静态方法给静态属性赋值，那么这个静态属性的
     * 值是在第一次赋值之后是保持不变的。
     *
     * 而同调用两次静态方法返回的对象是不同的。
     */
    @Test
    public void test2(){
        Post post = HibernateUtil.getPost2();
        Post post1 = HibernateUtil.getPost2();
        if(post == post1){
            System.out.println("true");
        }else{
            System.out.println(post);
            System.out.println(post1);
        }
    }

}