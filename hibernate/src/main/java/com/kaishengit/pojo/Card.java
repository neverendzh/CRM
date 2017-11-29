package com.kaishengit.pojo;

/**
 * @author zh
 * Created by Administrator on 2017/11/29.
 */
public class Card {

    private Integer id;
    private String cardNum;
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