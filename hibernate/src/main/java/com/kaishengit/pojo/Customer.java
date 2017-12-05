package com.kaishengit.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author zh
 * Created by Administrator on 2017/12/4.
 * 用于演示hibernate的乐观锁和悲观锁机制的实体类
 */
@Entity
public class Customer {
    @Id
    @GenericGenerator(name = "ctuuid",strategy = "uuid")
    @GeneratedValue(generator = "ctuuid")
    private String id;
    private String name;
    @Version
    private Integer version;
    /**
     * 该属性不予数据库有关系 @Transient表示不参与持久化数据库
     */
    @Transient
    private String niceName;
    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }



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