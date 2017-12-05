package com.kaishengit.hibernate;

import com.kaishengit.pojo.Card;
import com.kaishengit.pojo.Persion;
import com.kaishengit.pojo.Post;
import com.kaishengit.pojo.PostContent;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 * 1.一对一央射关系
 * 2.以及延长延迟懒加载模式
 * 3.其中OpenSessionInview最佳方案
 *
 */
public class OneToOneTest {
    private Session session;

    @Before
    public void before(){
        session = HibernateUtil.getSession();
        session.beginTransaction();
    }


//    @After
    public void after(){
        session.getTransaction().commit();
    }

    /**
     * 一对一保存，
     */
    @Test
    public void save(){
        Persion persion = new Persion();
        persion.setPersionName("小凯");

        Card card = new Card();
        card.setCardNum("23453253");
//      设置关联
        card.setPersion(persion);

//      最佳实践，先存主数据，在存从数据
        session.save(persion);
        session.save(card);
        session.getTransaction().commit();
    }


    /**
     * 查询
     */
    @Test
    public void find(){
//        这样会自动关联Card这个表
        Persion persion = (Persion) session.get(Persion.class,2);
        System.out.println(persion.getPersionName());
        System.out.println(persion.getCard().getCardNum());

    }


    /**
     * 删除
     */
    @Test
    public void delete(){
        Persion persion = (Persion) session.get(Persion.class,1);
        session.delete(persion);
    }

    @Test
    public void savePost(){
        Post post = new Post();
        post.setTitle("哈利波特-2");

        PostContent postContent = new PostContent();
        postContent.setContent("月黑风高-2");

        postContent.setPost(post);
        post.setPostContent(postContent);

        session.save(post);
        session.save(postContent);
        session.getTransaction().commit();
    }


    /**
     * 1.同样是懒加载模式
     * 2.延长延迟懒加载的范围
     */
    @Test
    public void findByPost(){
        Post post = (Post) session.get(Post.class,1);
        System.out.println(post.getTitle());

//        在session对象关闭之前使用一次该对象就会把对象加载到内存中，
//        在session对象关闭之后同样可以从内存中取值
        post.getPostContent().getContent();
//        这一步相当于上一步吧PostContent对象初始化加载到内存中
        Hibernate.initialize(post.getPostContent());
        session.getTransaction().commit();

//        懒加载的使用必须在session对象未关闭的情况下使用，底层使用动态代理的模式
//        在post的hbm.xml文件中配置lazy="false"表示关闭懒加载，在查询Post是也吧PostContent查询出来了
//        也可以使用 fetch="join 不使用懒加载模式
        System.out.println(post.getPostContent().getContent());

    }

}