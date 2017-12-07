package com.neverend.pojo;


import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author zh
 * Created by Administrator on 2017/12/5.
 * 在多对一的情况下不会延迟加载，需要配置fetch = FetchType.LAZY
 */
@Entity
@Table(name = "kaola")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    private BigDecimal price;
    @Column(name = "market_price")
    private BigDecimal marketPrice;
    private String place;
    @Column(name = "comment_num")
    private Integer commentNum;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private ProductType productType;


    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

}