package com.neverend.pojo;

/**
 * Created by Administrator on 2017/12/27.
 */
public class Movie {
    private Integer id;
    private String name;
    private String actor;

    public Movie(Integer id, String name, String actor) {
        this.id = id;
        this.name = name;
        this.actor = actor;
    }

    public Movie() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
