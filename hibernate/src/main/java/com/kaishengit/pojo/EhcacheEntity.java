package com.kaishengit.pojo;

import java.io.Serializable;

/**
 * @author zh
 * Created by Administrator on 2017/12/1.
 *
 */
public class EhcacheEntity implements Serializable{
    private Integer id;
    private String name;

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
}