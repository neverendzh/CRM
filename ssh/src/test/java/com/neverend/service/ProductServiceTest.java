package com.neverend.service;

import com.neverend.pojo.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-hibernate.xml")
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void findAll() throws Exception {
        List<Product> productList = productService.findAll();
        Assert.assertEquals(productList.size(),50);
    }
}