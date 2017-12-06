package com.neverend.controller;

import com.neverend.pojo.Product;
import com.neverend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String home(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList",productList);
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

}