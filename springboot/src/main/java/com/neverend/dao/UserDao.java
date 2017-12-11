package com.neverend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author zh
 * Created by Administrator on 2017/12/10.
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void save(int id, String name){
        String sql = "insert into person (id,person_name) values (?,?) ";
        jdbcTemplate.update(sql,id,name);
    }

}