package com.neverend.dao;

import com.neverend.pojo.Product;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/12/5.
 */
@Repository
public class ProductDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public void save(Product product){
        getSession().saveOrUpdate(product);

    }

    public Product findByid(Integer id){
        return (Product) getSession().get(Product.class,id);
    }

    public void deleteById(Integer id){
        getSession().delete(findByid(id));
    }

    public void delete(Product product){
        getSession().delete(product);
    }

    public List<Product> findAll(){
        String hql = "from Product order by id desc";
        Query query = getSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(50);
        return query.list();

    }
}