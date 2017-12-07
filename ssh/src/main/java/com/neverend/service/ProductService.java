package com.neverend.service;

import com.neverend.dao.ProductDao;
import com.neverend.pojo.Product;
import com.neverend.util.RequestQuery;
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
        return  productDao.findPage(0,50);
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }

    public Product findByid(Integer id) {
        return  productDao.findByid(id);

    }

    public void productDelete(Integer id) {
        productDao.deleteById(id);
    }

    public List<Product> findProductName(String productName) {
        if (productName != null && !"".equals(productName)) {
            return productDao.findProductName(productName);
        } else {
            return productDao.findPage(0,100);
        }
    }

    public List<Product> findByRequestQuery(List<RequestQuery> requestQueryList) {
        return productDao.findByRequestQueryList(requestQueryList);

    }
}