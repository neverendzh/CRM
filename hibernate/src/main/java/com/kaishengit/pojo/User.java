package com.kaishengit.pojo;

import java.util.Set;

/**
 * Created by Administrator on 2017/11/28.
 */
public class User {
    private Integer id;
    private String name;
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