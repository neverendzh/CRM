package com.kaishengit.pojo;

import javax.persistence.*;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 * fetch = FetchType.LAZY表示延迟加载，默认不会懒加载
 */
@Entity
@Table(name = "post_content")
public class PostContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",unique = true)
    private Post post;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}