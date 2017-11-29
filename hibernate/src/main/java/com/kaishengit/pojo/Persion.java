package com.kaishengit.pojo;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 */
public class Persion {

    private Integer id;
    private String persionName;

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