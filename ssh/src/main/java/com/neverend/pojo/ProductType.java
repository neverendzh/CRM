package com.neverend.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author zh
 * Created by Administrator on 2017/12/6.
 */
@Entity
@Table(name = "kaola_type")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "type_name")
    private String typeName;
    @Column(name = "parent_id")
    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}