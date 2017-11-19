package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.example.SaleChanceExample;
import com.kaishengit.crm.example.SaleChanceRecordExample;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.SaleChanceMapper;
import com.kaishengit.crm.mapper.SaleChanceRecordMapper;
import com.kaishengit.crm.service.SaleChanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zh
 * Created by Administrator on 2017/11/13.
 */
@Service
public class SaleChanceServiceImpl  implements SaleChanceService{

    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SaleChanceRecordMapper saleChanceRecordMapper;
    @Autowired
    private CustomerMapper customerMapper;


    @Value("#{'${sales.progress}'.split(',')}")
    private List<String> progressList;

    /**
     * 获取机会进度列表
     *
     * @return
     */
    @Override
    public List<String> findAllSalesProgress() {
        return progressList;
    }

    /**
     * 根据Account对象查询对应的销售机会分页列表
     * @param account
     * @param pageNo
     * @return
     */
    @Override
    public PageInfo<SaleChance> pageForAccountSales(Account account, Integer pageNo) {
        PageHelper.startPage(pageNo,10);
        List<SaleChance> saleChanceList = saleChanceMapper.findByAccountId(account.getId());
        return new PageInfo<>(saleChanceList);
    }

    /**
     * 根据主键查询销售机会
     *
     * @param id
     * @return
     */
    @Override
    public SaleChance findSalesChanceWithCustomerById(Integer id) {


        return saleChanceMapper.findChanceWithCustomerById(id);
    }

    /**
     * 根据销售机会的ID查询对应的跟进记录列表
     *
     * @param id
     * @return
     */
    @Override
    public List<SaleChanceRecord> findSalesChanceRecodeListBySalesId(Integer id) {
        SaleChanceRecordExample recordExample = new SaleChanceRecordExample();
        recordExample.createCriteria().andSaleIdEqualTo(id);

        return saleChanceRecordMapper.selectByExampleWithBLOBs(recordExample);
    }

    /**
     * 改变销售机会的进度
     *
     * @param id
     * @param progress
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateSalesChanceState(Integer id, String progress) {

        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        //改变状态
        saleChance.setProgress(progress);

        //销售机会的最后跟进时间
        saleChance.setLastTime(new Date());
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);

        //改变关联客户的最后跟进时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

        //产生一个跟进记录
        SaleChanceRecord record = new SaleChanceRecord();
        record.setCreateTime(new Date());
        record.setSaleId(id);
        record.setContent("将当前进度修改为:"+progress+"");


        saleChanceRecordMapper.insert(record);

    }

    /**
     * 删除销售机会
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteSalesChanceById(Integer id) {
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);

        //删除对应的跟进记录
        SaleChanceRecordExample saleChanceRecordExample = new SaleChanceRecordExample();
        saleChanceRecordExample.createCriteria().andSaleIdEqualTo(id);
        saleChanceRecordMapper.deleteByExample(saleChanceRecordExample);


        //删除销售机会
        saleChanceMapper.deleteByPrimaryKey(id);


        //将对应客户的最后跟进时间设置为null 或 空

        //判断当前客户是否还有其他销售机会，如果没有最后跟进时间设置为null或者空
        //如果有，则修改为最近销售机会的最后跟进时间

        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustIdEqualTo(saleChance.getCustId());
        saleChanceExample.setOrderByClause("last_time desc");

        List<SaleChance> saleChanceList = saleChanceMapper.selectByExample(saleChanceExample);


        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustId());


        if(saleChanceList.isEmpty()){
            customer.setLastContactTime(null);
        }else{
            customer.setLastContactTime(saleChanceList.get(0).getLastTime());
        }

        customerMapper.updateByPrimaryKeySelective(customer);


    }

    /**
     * 跟进客户ID查询对应的销售记录
     *
     * @param id
     * @return
     */
    @Override
    public List<SaleChance> findSalesChanceByCustId(Integer id) {
        SaleChanceExample saleChanceExample = new SaleChanceExample();
        saleChanceExample.createCriteria().andCustIdEqualTo(id);
        saleChanceExample.setOrderByClause("last_time desc");
        return saleChanceMapper.selectByExample(saleChanceExample);
    }

    /**
     * 查询客户成交的数据分类
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> findCustomerCountBySaleChangce() {
        return saleChanceMapper.findCustomerCountBySaleChangce();
    }

    /**
     * 给销售机会添加新跟进记录
     *
     * @param record
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveNewSalesChanceRecode(SaleChanceRecord record) {
        record.setCreateTime(new Date());
        saleChanceRecordMapper.insert(record);

        //销售机会的最后跟进时间
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(record.getSaleId());
        saleChance.setLastTime(new Date());
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);

        //改变关联客户的最后跟进时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 新增销售机会
     *
     * @param saleChance
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class )
    public void saveNewSalesChance(SaleChance saleChance) {
        saleChance.setCreateTime(new Date());
        saleChance.setLastTime(new Date());

        //更新对应客户的最后更近时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);

        // 保存销售机会
        saleChanceMapper.insert(saleChance);


        //判断销售机会的详细内容是否有值，如果存在则新增跟进记录
        if (StringUtils.isNoneEmpty(saleChance.getContent())){
            SaleChanceRecord record = new SaleChanceRecord();
            record.setContent(saleChance.getContent());
            System.out.println("理由----》"+saleChance.getContent());
            record.setCreateTime(new Date());
            record.setSaleId(saleChance.getId());
            System.out.println("理由----》"+saleChance.getId());
            saleChanceRecordMapper.insert(record);
        }


    }
}
