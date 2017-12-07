package com.neverend.dao;
import com.neverend.dao.basedao.BaseDao;
import com.neverend.pojo.Product;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/12/5.
 */
@Repository
public class ProductDao extends BaseDao<Product,Integer>{

    public List<Product> findProductName(String productName) {
        Criteria criteria = getSession().createCriteria(Product.class);
        criteria.add(Restrictions.like("productName",productName, MatchMode.ANYWHERE));
        return criteria.list();
    }
}