package com.neverend.dao.basedao;

import com.neverend.util.RequestQuery;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
/**
 * @author zh
 * Created by Administrator on 2017/12/6.
 * T表示泛型，在创建对象是传入的类名，用于反射机制
 * PK表示参数类型，必须是一个可序列话的
 *
 * *****通过子类继承父类而创建出来的父类对象，那么不管是在子类中还是在父类中的***this***都是子类对象
 *
 */
public abstract class BaseDao<T,Pk extends Serializable> {
    @Autowired
    private SessionFactory sessionFactory;
    /**
     * entityClazz类型对象
     */
    private Class<T> entityClazz;

    /**
     *this.getClass获取到子类对象，再调用类对象中的getGenericSuperclass()方法
     * 返回表示此Class所表示的实体（类，接口。基本类型或void）的直接超类的Type
     * 如果超类是参数化类型，则返回的 Type 对象必须准确反映源代码中所使用的实际类型参数，也就是返回
     * com.neverend.dao.basedao.BaseDao<com.neverend.pojo.Product, java.lang.Integer>,也就是返回的参数类型
     * 使用ParameterizedType对象接收，
     * 调用getActualTypeArguments（）方法返回实际类型参数为Type对象的数组，取第一个对象
     * 这样就获取到了类对象。
     *
     */
    public BaseDao(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
    }


    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public void save(T entity){
        getSession().saveOrUpdate(entity);
    }

    public T findByid(Pk id){
        return (T) getSession().get(entityClazz,id);
    }

    public void deleteById(Pk id){
        getSession().delete(findByid(id));
    }


    public void delete(T type){
        getSession().delete(type);
    }


    public List<T> findAll(){
        Criteria criteria = getSession().createCriteria(entityClazz);
        return criteria.list();
    }

    public List<T> findPage(Integer start,Integer size){
        Criteria criteria = getSession().createCriteria(entityClazz);
        criteria.setFirstResult(start);
        criteria.setMaxResults(size);
        return criteria.list();
    }

    public List<T> findByRequestQueryList(List<RequestQuery> requestQueryList){
        if(requestQueryList.isEmpty()){
            return findPage(0,50);
        }
        Criteria criteria = getSession().createCriteria(entityClazz);
        for(RequestQuery requestQuery : requestQueryList){
            String paramName = requestQuery.getParameterName();
            String type = requestQuery.getEqualType();
            Object value = requestQuery.getValue();
            criteria.add(createCriterion(paramName,type,value));
        }
        return criteria.list();
    }

    public Criterion createCriterion(String paramName,String type,Object value){
        if("eq".equalsIgnoreCase(type)) {
            return Restrictions.eq(paramName,value);
        } else if("like".equalsIgnoreCase(type)) {
            return Restrictions.like(paramName,value.toString(), MatchMode.ANYWHERE);
        } else if("gt".equalsIgnoreCase(type)) {
            return Restrictions.gt(paramName,value);
        } else if("ge".equalsIgnoreCase(type)) {
            return Restrictions.ge(paramName,value);
        } else if("lt".equalsIgnoreCase(type)) {
            return Restrictions.lt(paramName,value);
        } else if("le".equalsIgnoreCase(type)) {
            return Restrictions.le(paramName,value);
        }
        return null;
    }


}