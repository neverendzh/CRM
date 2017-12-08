package com.neverend.controller;

import com.neverend.pojo.Product;
import com.neverend.service.ProductService;
import com.neverend.util.Page;
import com.neverend.util.RequestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zh
 * Created by Administrator on 2017/12/6.
 *
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 显示所有商品信息
     * @param model
     * @return
     */
    @GetMapping
    public String home(Model model,
                       HttpServletRequest request,
                       @RequestParam(required = false,name = "p",defaultValue = "1") Integer pageNo) {
        List<RequestQuery> requestQueryList = RequestQuery.builderRequestQuery(request);
        Page<Product> productList = productService.findByRequestQuery(requestQueryList,pageNo);
        model.addAttribute("page",productList);
        return "list";
    }

    /**
     * 新增商品页面
     * @return
     */
    @GetMapping("/new")
    public String newProduct(){
        return "new";
    }

    /**
     * 保存新增商品
     * @param product
     * @return
     */
    @PostMapping("/new")
    public String saveProduct(Product product){
        productService.saveProduct(product);
        return "redirect:/product";
    }

    /**
     * 商品详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id:\\d+}")
    public String productDetails(@PathVariable Integer id, Model model){
            Product product = productService.findByid(id);
            model.addAttribute("product",product);
            return "product";
    }

    /**
     * 删除商品
     * @param id
     * @param model
     * @return
     */
    @GetMapping("delete/{id:\\d+}")
    public String productDelete(@PathVariable Integer id,Model model){
        productService.productDelete(id);
        return "redirect:/product";
    }

    /**
     * 修改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("edit/{id:\\d+}")
    public String productEdit(@PathVariable Integer id,Model model){

        Product product = productService.findByid(id);
        model.addAttribute("product",product);
        return "edit";
    }

    /**
     * 保存修改后的数据
     * @param product
     * @return
     */
    @PostMapping("edit/{id:\\d+}")
    public String productEditSave(Product product){
        productService.saveProduct(product);
        return "redirect:/product/"+product.getId();

    }
}