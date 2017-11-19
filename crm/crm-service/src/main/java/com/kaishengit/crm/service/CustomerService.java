package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

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

    /**
     * 转交客户给其他员工
     * @param customer 客户对象
     * @param toAccountId 转入账号ID
     */
    void tranCustomer(Customer customer, Integer toAccountId);

    /**
     * 导出客户资料为CSV文件
     * @param outputStream
     * @param account
     */
    void exportCsvFileToOutputStream(OutputStream outputStream, Account account) throws IOException;

    /**
     * 将客户资料导出为slx文件
     * @param outputStream
     * @param account
     * @throws IOException
     */
    void exportXlsFileToOutputStream(OutputStream outputStream, Account account)throws IOException;

    /**
     * 查找属于当前Account对象的客户列表
     * @param account
     * @return
     */
    List<Customer> findAllCustomerByAccountId(Account account);

    /**
     *  查询各个星级的客户数量
     * @return
     */
    List<Map<String,Object>> findCustomerCountBylevel();

    /**
     * 查询每个月新增可数的数量
     * @return
     */
    List<Map<String,Object>> findCustomerCountByMouthNum();

    /**
     *
     */
    List<Customer> findAllpublicCustomer();
}
