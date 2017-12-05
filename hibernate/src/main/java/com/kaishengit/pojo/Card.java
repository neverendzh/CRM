package com.kaishengit.pojo;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 * @GenericGenerator是hibernate自定义的主键生成器
 * generator = "fk"表示使用自定义的主键生成器
 */
@Entity
public class Card {

    @Id
    @GenericGenerator(name = "fk",strategy = "foreign",
            parameters = @Parameter(name = "property" ,value = "persion"))
    @GeneratedValue(generator = "fk")
    private Integer id;
    @Column(name = "card_num")
    private String cardNum;
    @OneToOne(mappedBy = "card")
    @PrimaryKeyJoinColumn
    private Persion persion;

    public Persion getPersion() {
        return persion;
    }

    public void setPersion(Persion persion) {
        this.persion = persion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}