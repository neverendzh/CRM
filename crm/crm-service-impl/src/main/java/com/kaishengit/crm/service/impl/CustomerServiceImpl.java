package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.example.CustomerExample;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/12.
 * 客户管理业务层
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Logger logger  = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Value("#{'${customer.trade}'.split(',')}")
    private List<String> customerTrade;
    /*@Value("${customer.trade}") //读取字符串
    private List<String> customerTrade;
*/
    @Value("#{'${customer.source}'.split(',')}")
    private List<String> customerSource;

    /**
     * 我的客户的分页方法
     *
     * @param account 当前登录的Account对象
     * @param pageNo  分页号
     * @return
     */
    @Override
    public PageInfo<Customer> pageTorMyCustomer(Account account, Integer pageNo) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());
        PageHelper.startPage(pageNo,5);
        List<Customer> customerList = customerMapper.selectByExample(customerExample);

        return new PageInfo<>(customerList);
    }

    /**
     * 获取所有客户的行业名称
     *
     * @return
     */
    @Override
    public List<String> findAllCustomerTrade() {
        return customerTrade;
    }

    /**
     * 获取所有客户来源
     *
     * @return
     */
    @Override
    public List<String> findAllCustomerSource() {
        return customerSource;
    }

    /**
     * 添加客户
      * @param customer
     */
    @Override
    public void saveNewCustomer(Customer customer) {
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customerMapper.insertSelective(customer);
        logger.info("添加了新客户 {}",customer.getCustName());

    }

    /**
     * 根据Id显示客户信息
     *
     * @param id
     * @return
     */
    @Override
    public Customer findCustomerById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除指定客户
     *
     * @param customer
     */
    @Override
    public void deleteCustomer(Customer customer) {
         customerMapper.deleteByPrimaryKey(customer.getId());
    }

    /**
     * 将客户放入公海
     *
     * @param customer
     */
    @Override
    public void publicCustomer(Customer customer) {
        Account account = accountMapper.selectByPrimaryKey(customer.getId());
        customer.setAccountId(null);
        customer.setReminder(customer.getReminder()+"--->"+account.getUserName()+"将该客户放入公海");
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 编辑客户
     *
     * @param customer
     */
    @Override
    public void editCustomer(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }
}
