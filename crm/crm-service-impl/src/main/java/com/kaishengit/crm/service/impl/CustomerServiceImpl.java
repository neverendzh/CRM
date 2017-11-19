package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.example.CustomerExample;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 转交客户给其他员工
     *
     * @param customer    客户对象
     * @param toAccountId 转入账号ID
     */
    @Override
    public void tranCustomer(Customer customer, Integer toAccountId) {
        Account account = accountMapper.selectByPrimaryKey(customer.getAccountId());
        customer.setAccountId(toAccountId);
        customer.setReminder(customer.getReminder()+"--->"+"从"+account.getUserName()+"转交过来");
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 导出客户资料为CSV文件
     *
     * @param outputStream
     * @param account
     */
    @Override
    public void exportCsvFileToOutputStream(OutputStream outputStream, Account account) throws IOException {
        List<Customer> customerList = findAllCustomerByAccountId(account);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("姓名")
                .append(",")
                .append("联系电话")
                .append(",")
                .append("职位")
                .append(",")
                .append("地址")
                .append(",")
                .append("创建时间")
                .append("\r\n");
        for(Customer customer :customerList){
            stringBuilder.append(customer.getCustName())
                    .append(",")
                    .append(customer.getMobile())
                    .append(",")
                    .append(customer.getJobTitle())
                    .append(",")
                    .append(customer.getAddress())
                    .append(",")
                    .append(customer.getCreateTime())
                    .append("\r\n");
        }
        IOUtils.write(stringBuilder.toString(),outputStream,"UTF-8");
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 将客户资料导出为slx文件
     *
     * @param outputStream
     * @param account
     * @throws IOException
     */
    @Override
    public void exportXlsFileToOutputStream(OutputStream outputStream, Account account) throws IOException {
        List<Customer> customerList = findAllCustomerByAccountId(account);

        //创建工作表
        Workbook workbook = new HSSFWorkbook();
        //创见sheet页脚
        Sheet sheet = workbook.createSheet("我的客户");
        //创建行
        Row titleRow = sheet.createRow(0);
        //创建单元格
        Cell nameCell = titleRow.createCell(0);
        nameCell.setCellValue("姓名");
        titleRow.createCell(1).setCellValue("电话");
        titleRow.createCell(2).setCellValue("职位");
        titleRow.createCell(3).setCellValue("地址");
        titleRow.createCell(4).setCellValue("创建时间");
        for(int i = 0;i<customerList.size();i++){
            Customer customer = customerList.get(i);
            Row datRow = sheet.createRow(i+1);
            datRow.createCell(0).setCellValue(customer.getCustName());
            datRow.createCell(1).setCellValue(customer.getMobile());
            datRow.createCell(2).setCellValue(customer.getJobTitle());
            datRow.createCell(3).setCellValue(customer.getAddress());
            datRow.createCell(4).setCellValue(customer.getCreateTime());
        }
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    @Override
    public List<Customer> findAllCustomerByAccountId(Account account) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(account.getId());
        List<Customer> customerList = customerMapper.selectByExample(customerExample);
        return  customerList;
    }

    /**
     * 查询各个星级的客户数量
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findCustomerCountBylevel() {
        return customerMapper.findCustomerCountBylevel();
    }

    /**
     * 查询每个月新增可数的数量
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findCustomerCountByMouthNum() {
        return customerMapper.findCustomerCountByMouthNum();
    }

}
