package com.kaishengit.crm.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.SaleChanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/11/10.
 * 客户管理的控制器
 */
@Controller
@RequestMapping("/customer")
public class CustomerController  extends BaseController{

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SaleChanceService saleChanceService;
    /**
     * 访问我的客户
     * @return
     */
    @GetMapping("/my")
    public String myCustomer(@RequestParam(required = false,defaultValue = "1") Integer pageNo,
                            Model model,
                            HttpSession httpSession){
        Account account = getCurrentAccount(httpSession);
        PageInfo<Customer> pageInfo = customerService.pageTorMyCustomer(account,pageNo);

        model.addAttribute("page",pageInfo);
        return "customer/my";
    }

    /**
     * 新增客户
     * @return
     */
    @GetMapping("my/new")
    public String newCustomer(Model model){
        model.addAttribute("trades",customerService.findAllCustomerTrade());
        model.addAttribute("suorces",customerService.findAllCustomerSource());
        return "customer/new";
    }

    @PostMapping("/my/new")
    public String newCustomer(Customer customer, RedirectAttributes redirectAttributes){
        customerService.saveNewCustomer(customer);
        redirectAttributes.addFlashAttribute("message","添加客户成功");
        return "redirect:/customer/my";
    }

    /**
     * 显示客户详细信息
     * @return
     */
    @GetMapping("/my/{id:\\d+}")
    public String showCustomer(@PathVariable Integer id,HttpSession httpSession,Model model){

      Customer customer = checkCustomerRole(id,httpSession);
      //显示客户关联关系的销售机会列表
        List<SaleChance> saleChanceList = saleChanceService.findSalesChanceByCustId(id);
        model.addAttribute("saleChanceList",saleChanceList);
        model.addAttribute("customer",customer);
        model.addAttribute("accountList",accountService.findAllAccount());
        return "customer/show";
    }

    /**
     *根据主键删除客户
     * @param id
     * @param httpSession
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{id:\\d+}/delete")
    public String deletlCustomerById(@PathVariable Integer id,
                                     HttpSession httpSession,
                                     RedirectAttributes redirectAttributes){
            Customer customer = checkCustomerRole(id,httpSession);
            customerService.deleteCustomer(customer);
            redirectAttributes.addFlashAttribute("message","删除客户成功");
            return "redirect:/customer/my";
    }

    /**
     * 将客户放入公海
     * @return
     */
    @GetMapping("/my/{id:\\d+}/public")
    public String publicCustomer(@PathVariable Integer id,
                                 HttpSession httpSession,
                                 RedirectAttributes redirectAttributes){
        Customer customer = checkCustomerRole(id,httpSession);
        customerService.publicCustomer(customer);
        redirectAttributes.addFlashAttribute("message","客户放入公海成功");
        return "redirect:/customer/my";

    }
    /**
     * 编辑客户资料
     */
    @GetMapping("/my/{id:\\d+}/edit")
    public String deitCustomer(@PathVariable Integer id,
                               HttpSession httpSession,
                               Model model){
        Customer customer = checkCustomerRole(id,httpSession);

        model.addAttribute("customer",customer);
        model.addAttribute("trades",customerService.findAllCustomerTrade());
        model.addAttribute("suorces",customerService.findAllCustomerSource());
        return "customer/edit";
    }

    @PostMapping("/my/{id:\\d+}/edit")
    public String editCustomer(Customer customer,HttpSession httpSession,RedirectAttributes redirectAttributes){
           checkCustomerRole(customer.getId(),httpSession);
           customerService.editCustomer(customer);
           redirectAttributes.addFlashAttribute("message","编辑成功");

           return "redirect:/customer/my/"+customer.getId();

    }


    /**
     * 转交给客户给其他销售人员
     * @return
     */
    @GetMapping("/my/{customerId:\\d+}/tran/{toAccountId:\\d+}")
    public String tranCustomer(@PathVariable Integer customerId,
                               @PathVariable Integer toAccountId,
                               HttpSession httpSession,
                               RedirectAttributes redirectAttributes){
        Customer customer = checkCustomerRole(customerId,httpSession);
        customerService.tranCustomer(customer,toAccountId);
        redirectAttributes.addFlashAttribute("message","客户转件成功");
        return "redirect:/customer/my";

    }

    /**
     * 将数据导出为CSV文件
     */
    @GetMapping("/my/export.csv")
    public void exportCsv(HttpServletResponse httpServletResponse,
                          HttpSession httpSession) throws IOException {
        Account account = getCurrentAccount(httpSession);

        httpServletResponse.setContentType("text/csv;charset=UTF-8");

        String fileName = new String("我的客户".getBytes("UTF-8"),"ISO8859-1");
        httpServletResponse.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");

        OutputStream outputStream = httpServletResponse.getOutputStream();
        customerService.exportCsvFileToOutputStream(outputStream,account);


    }

    /**
     * 将客户资料导出为xls文件
     * @param httpServletResponse
     * @param httpSession
     * @throws IOException
     */
    @GetMapping("/my/export.xls")
    public void exportXls(HttpServletResponse httpServletResponse,
                          HttpSession httpSession) throws IOException {
        Account account = getCurrentAccount(httpSession);

        httpServletResponse.setContentType("application/vnd.ms-excel");

        String fileName = new String("我的客户.xls".getBytes("UTF-8"),"ISO8859-1");
        httpServletResponse.addHeader("Content-Disposition","attachment; filename=\""+fileName+"\"");

        OutputStream outputStream = httpServletResponse.getOutputStream();
        customerService.exportXlsFileToOutputStream(outputStream,account);


    }

    /**
     * 公海客户列表
     * @return
     */
    @GetMapping("/public")
    public String publicCustomer(){
        return "customer/public";
    }

    /**
     * 验证客户是否是当前登录的对象
     * @param id
     * @param httpSession
     * @return
     */
    private Customer checkCustomerRole(Integer id,HttpSession httpSession){
        //根据ID查找客户
        Customer customer = customerService.findCustomerById(id);
        if(customer == null){
            //404
            throw new NotFoundException("找不到"+id+"对应的客户");
        }
        Account account = getCurrentAccount(httpSession);
        if(!customer.getAccountId().equals(account.getId())){
            //403没权限
            throw new ForbiddenException("您没有查看"+id+"用户的权限");
        }
        return customer;
    }
}
