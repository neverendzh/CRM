package com.kaishengit.pojo;

/**
 * @author zh
 * Created by Administrator on 2017/11/27.
 * POJO持久化类
 */
public class Book {
    private Integer id;
    private String bookIsbn;
    private String bookName;
    private String bookOuther;
    private String bookPerss;
    private Integer bookTotal;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookIsbn='" + bookIsbn + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookOuther='" + bookOuther + '\'' +
                ", bookPerss='" + bookPerss + '\'' +
                ", bookTotal=" + bookTotal +
                ", bookNowTotal=" + bookNowTotal +
                '}';
    }

    private  Integer bookNowTotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookOuther() {
        return bookOuther;
    }

    public void setBookOuther(String bookOuther) {
        this.bookOuther = bookOuther;
    }

    public String getBookPerss() {
        return bookPerss;
    }

    public void setBookPerss(String bookPerss) {
        this.bookPerss = bookPerss;
    }

    public Integer getBookTotal() {
        return bookTotal;
    }

    public void setBookTotal(Integer bookTotal) {
        this.bookTotal = bookTotal;
    }

    public Integer getBookNowTotal() {
        return bookNowTotal;
    }

    public void setBookNowTotal(Integer bookNowTotal) {
        this.bookNowTotal = bookNowTotal;
    }
}
