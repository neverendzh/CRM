package com.kaishengit.pojo;

/**
 * @author zh
 * Created by Administrator on 2017/12/4.
 * 用于演示hibernate的乐观锁和悲观锁机制的实体类
 */
public class Customer {
    private String id;
    private String name;
    private Integer version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}