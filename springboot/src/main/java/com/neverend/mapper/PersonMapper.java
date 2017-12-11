package com.neverend.mapper;

import com.neverend.entity.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zh
 * Created by Administrator on 2017/12/11.
 * @Mapper 放入Spring容器中
 */
@Mapper
public interface PersonMapper {


   /* @Insert("insert into person (id,person_name) values (#{id},#{name})")
    void save(@Param("id") Integer id,
              @Param("name") String name);*/

    /**
     * 配置文件方式
     * @param person
     */
   void save(Person person);
}