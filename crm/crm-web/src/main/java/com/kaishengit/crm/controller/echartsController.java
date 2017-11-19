package com.kaishengit.crm.controller;

import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import com.kaishengit.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author zh
 * Created by Administrator on 2017/11/18.
 *  统计报表的控制器
 */
@Controller
@RequestMapping("/echarts")
public class echartsController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private SaleChanceService saleChanceService;

    /**
     * 没有加载数据的空的二维图
     * @return
     */
    @GetMapping("/echarts")
    public String echartsTest(){
        return "echarts/echarts";

    }

    /**
     * 返回静态页面的示例视图
     * @return
     */
    @GetMapping("/static")
    public String echartsStatic(){
        return "echarts/static";
    }

    /**
     *异步加载数据，查出不同星级对应的客户有多少个
     * @return
     */
    @GetMapping("/customer/level")
    @ResponseBody
    public AjaxResult customerLevel(){
        List<Map<String,Object>> result = customerService.findCustomerCountBylevel();
        return AjaxResult.successWithData(result);
    }

    /**
     *异步加载数据，查询每月新增客户的数量
     * @return
     */
    @GetMapping("/customer/mouth/num")
    @ResponseBody
    public AjaxResult customerNum(){
        List<Map<String,Object>> result = customerService.findCustomerCountByMouthNum();
        return AjaxResult.successWithData(result);
    }


    /**
     *异步加载数据，查询客户初防，意向，报价，成交，搁置的数据分类
     * @return
     */
    @GetMapping("/customer/sale/num")
    @ResponseBody
    public AjaxResult customerSaleChangce(){
        List<Map<String,Object>> result = saleChanceService.findCustomerCountBySaleChangce();
        return AjaxResult.successWithData(result);
    }



}
