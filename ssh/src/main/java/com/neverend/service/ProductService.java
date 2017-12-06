package com.neverend.service;

import com.neverend.dao.ProductDao;
import com.neverend.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/12/5.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public List<Product> findAll(){
        return  productDao.findAll();
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }
}