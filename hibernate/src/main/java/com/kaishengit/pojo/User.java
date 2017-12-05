package com.kaishengit.pojo;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/28.
 * @OneToMany(mappedBy = "user")一对多，mappedBy = "user"表示放弃关系维护
 *
 *  @OrderBy("id desc")排序根据id
 *
 *  cascade = CascadeType.REMOVE 级联删除
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private Set<Address> addressSet;


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

    public Set<Address> getAddressSet() {
        return addressSet;
    }

    public void setAddressSet(Set<Address> addressSet) {
        this.addressSet = addressSet;
    }
}