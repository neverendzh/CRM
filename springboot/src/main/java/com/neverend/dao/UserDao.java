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
    public void save(int id, String password){
        String sql = "insert into persion (id,persion_name) values (?,?) ";
        jdbcTemplate.update(sql,id,password);
    }

}