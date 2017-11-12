package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;

import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/12.
 * 客户管理业务层
 *
 */
public interface CustomerService {

    /**
     * 我的客户的分页方法
     * @param account 当前登录的Account对象
     * @param pageNo 分页号
     * @return
     */
    PageInfo<Customer> pageTorMyCustomer(Account account, Integer pageNo);


    /**
     * 获取所有客户的行业名称
     * @return
     */
    List<String> findAllCustomerTrade();


    /**
     * 获取所有客户来源
     * @return
     */
    List<String> findAllCustomerSource();

    /**
     * 添加客户
     * @param customer
     */
    void saveNewCustomer(Customer customer);

    /**
     * 根据Id显示客户信息
     * @param id
     * @return
     */
    Customer findCustomerById(Integer id);

    /**
     * 删除指定客户
     * @param customer
     */
    void deleteCustomer(Customer customer);

    /**
     * 将客户放入公海
     * @param customer
     */
    void publicCustomer(Customer customer);

    /**
     * 编辑客户
     * @param customer
     */
    void editCustomer(Customer customer);
}
