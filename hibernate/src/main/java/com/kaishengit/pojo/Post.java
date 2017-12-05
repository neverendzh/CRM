package com.kaishengit.pojo;

import javax.persistence.*;

/**
 * @author zh
 * Created
 * by Administrator on 2017/11/29.
 * fetch = FetchType.LAZY表示延迟加载，默认不会懒加载
 * @JoinColumn(name = "content_id",unique = true)，name = "content_id"表示外键的列值name
 * unique = true表示对应的content_id外键是唯一的unique = true
 */
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id",unique = true)
    private PostContent postContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PostContent getPostContent() {
        return postContent;
    }

    public void setPostContent(PostContent postContent) {
        this.postContent = postContent;
    }
}