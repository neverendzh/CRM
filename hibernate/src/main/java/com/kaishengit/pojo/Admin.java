package com.kaishengit.pojo;

import javax.persistence.*;

/**
 * @author zh
 * Created by Administrator on 2017/12/4.
 * 演示hibernate的注解使用方式POJO类
 * 1.如果是类名个表名不一样式还需要配置@Table(name = "数据库表名")
 * 2.@Id表示主键列 @GeneratedValue(strategy = GenerationType.AUTO)主键生成策略
 * GenerationType.AUTO ;GenerationType.IDENTITY 都表示自动增长
 * 3.@Column(name = "数据库列名") 如果数据库列名和属性名一致则不用配置
 */
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "admin_name")
    private String adminName;
    @Column(name = "admin_password")
    private String adminPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                '}';
    }
}