package com.neverend.controller;

import com.neverend.pojo.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/12/27.
 */
@RestController
public class MoveController {
    @GetMapping("/movie/{id:\\d+}")
    public Movie finMovie(@PathVariable Integer id){
        return new Movie(1,"绣春刀","哈哈");
    }
}