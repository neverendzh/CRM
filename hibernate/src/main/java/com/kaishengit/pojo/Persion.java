package com.kaishengit.pojo;

import javax.persistence.*;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 *@PrimaryKeyJoinColumn表示自己的主键和Card的主键是一致的
 */
@Entity
public class Persion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "persion_name")
    private String persionName;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Card card;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersionName() {
        return persionName;
    }

    public void setPersionName(String persionName) {
        this.persionName = persionName;
    }
}