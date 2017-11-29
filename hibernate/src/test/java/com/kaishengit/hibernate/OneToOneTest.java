package com.kaishengit.hibernate;

import com.kaishengit.pojo.Card;
import com.kaishengit.pojo.Persion;
import com.kaishengit.pojo.Post;
import com.kaishengit.pojo.PostContent;
import com.kaishengit.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 * 一对一
 */
public class OneToOneTest {
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
    }


    /**
     * 查询
     */
    @Test
    public void find(){
//        这样会自动关联Card这个表
        Persion persion = (Persion) session.get(Persion.class,1);
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
        post.setTitle("哈利波特");

        PostContent postContent = new PostContent();
        postContent.setContent("月黑风高");

        postContent.setPost(post);
        post.setPostContent(postContent);

        session.save(post);
        session.save(postContent);
    }


    /**
     * 同样是懒加载模式
     */
    @Test
    public void findByPost(){
        Post post = (Post) session.get(Post.class,1);
        System.out.println(post.getTitle());
        System.out.println(post.getPostContent().getContent());

    }

}