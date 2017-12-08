package com.neverend.service;

import com.neverend.dao.ProductDao;
import com.neverend.pojo.Product;
import com.neverend.util.Page;
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

    /**
     * 根据查询条件返回数据
     * @param requestQueryList
     * @return
     */
    public List<Product> findByRequestQuery(List<RequestQuery> requestQueryList) {
        return productDao.findByRequestQueryList(requestQueryList);

    }

    /**
     * 根据查询条件和分页查询数据
     * @param requestQueryList
     * @param pageNo
     * @return
     */
    public Page<Product> findByRequestQuery(List<RequestQuery> requestQueryList, Integer pageNo) {
        return productDao.findByRequestListAndPageNo(requestQueryList,pageNo);
    }
}