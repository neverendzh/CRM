package com.kaishengit.crm.controller.interceptor;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.controller.BaseController;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/11/13.
 */
@Controller
@RequestMapping("/sales")
public class SaleChanceController extends BaseController {

    @Autowired
    private SaleChanceService saleChanceService;

    @Autowired
    private CustomerService customerService;

    /**
     *我的销售机会列表
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String mySalesList(@RequestParam(required = false,defaultValue = "1",name = "p") Integer pageNo,
                              Model model,
                              HttpSession httpSession){
        Account account = getCurrentAccount(httpSession);
        PageInfo<SaleChance> pageInfo = saleChanceService.pageForAccountSales(account,pageNo);

        model.addAttribute("page",pageInfo);
        return "sales/my";
    }

    @GetMapping("/my/{id:\\d+}")
    public String mySalesInfo(@PathVariable Integer id,
                              HttpSession httpSession,
                              Model model){

        SaleChance saleChance = checkRole(id, httpSession);

        //查询该销售机会对应的跟进记录列表
        List<SaleChanceRecord> saleChanceRecordList = saleChanceService.findSalesChanceRecodeListBySalesId(id);
        model.addAttribute("recordList",saleChanceRecordList);
        model.addAttribute("saleChance",saleChance);
        model.addAttribute("processList",saleChanceService.findAllSalesProgress());
        return "sales/chance";
    }

    private SaleChance checkRole( Integer id, HttpSession httpSession) {
        Account account = getCurrentAccount(httpSession);

        SaleChance saleChance = saleChanceService.findSalesChanceWithCustomerById(id);
        if(saleChance == null){
            throw new NotFoundException();
        }

        if(!saleChance.getAccountId().equals(account.getId())){
            throw new ForbiddenException();
        }
        return saleChance;
    }

    /**
     * 新增销售机会记录
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/my/new")
    public String newSalesChance(Model model,
                                 HttpSession httpSession){
        Account account = getCurrentAccount(httpSession);
        //当前登录对象的客户列表
        List<Customer> customerList = customerService.findAllCustomerByAccountId(account);
        //进度列表
        List<String> propressList = saleChanceService.findAllSalesProgress();

        model.addAttribute("customerList",customerList);
        model.addAttribute("progressList",propressList);
        return "sales/new_chance";
    }

    /**
     * 新增销售机会的跟进记录
     * @param record
     * @return
     */
    @PostMapping("/my/new/record")
    public String saveNewSaleChanceRecode(SaleChanceRecord record,HttpSession httpSession){
        checkRole(record.getSaleId(), httpSession);
        saleChanceService.saveNewSalesChanceRecode(record);
        return "redirect:/sales/my/"+record.getSaleId();

    }

    /**
     * 改变销售机会的状态
     * @return
     */
    @PostMapping("/my/{id:\\d+}/progress/update")
    public String updateSaleChanceState(@PathVariable Integer id,
                                        String progress,
                                        HttpSession httpSession){
        checkRole(id, httpSession);
        saleChanceService.updateSalesChanceState(id,progress);
        return "redirect:/sales/my/"+id;

    }



    /**
     * 保存新销售机会
     * @param saleChance
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/my/new")
    public String saveSalesChance(SaleChance saleChance,
                                  RedirectAttributes redirectAttributes){
        saleChanceService.saveNewSalesChance(saleChance);
        redirectAttributes.addFlashAttribute("message","保存成功");

        return "redirect:/sales/my";
    }


    /**
     * 删除销售机会
     */
    @GetMapping("/my/{id:\\d+}/delete")
    public String deleteSalesChance(@PathVariable Integer id,
                                    RedirectAttributes redirectAttributes,
                                    HttpSession httpSession){

         checkRole(id, httpSession);
        saleChanceService.deleteSalesChanceById(id);
        return "redirect:/sales/my";
    }

}
