package com.example.finalproject;

public class OrderVO {

    /*계정테이블(account)*/
    private String account_email;//이메일(아이디대체)
    private String account_name;//이름
    private String account_phone;//전화번호

    /*상품테이블(productinfo_order)*/
    private int productinfo_productcode;
    private String productinfo_productName;
    private int productinfo_price;
    private String productinfo_image;

    /*주문테이블(orderinfo)*/
    private int orderinfo_ordercode;
    private String orderinfo_ordertime;
    private int orderinfo_count;
    private String orderinfo_email;
    private int orderinfo_productcode;

    public String getAccount_email() {
        return account_email;
    }

    public void setAccount_email(String account_email) {
        this.account_email = account_email;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_phone() {
        return account_phone;
    }

    public void setAccount_phone(String account_phone) {
        this.account_phone = account_phone;
    }

    public int getProductinfo_productcode() {
        return productinfo_productcode;
    }

    public void setProductinfo_productcode(int productinfo_productcode) {
        this.productinfo_productcode = productinfo_productcode;
    }

    public String getProductinfo_productName() {
        return productinfo_productName;
    }

    public void setProductinfo_productName(String productinfo_productName) {
        this.productinfo_productName = productinfo_productName;
    }

    public int getProductinfo_price() {
        return productinfo_price;
    }

    public void setProductinfo_price(int productinfo_price) {
        this.productinfo_price = productinfo_price;
    }

    public String getProductinfo_image() {
        return productinfo_image;
    }

    public void setProductinfo_image(String productinfo_image) {
        this.productinfo_image = productinfo_image;
    }

    public int getOrderinfo_ordercode() {
        return orderinfo_ordercode;
    }

    public void setOrderinfo_ordercode(int orderinfo_ordercode) {
        this.orderinfo_ordercode = orderinfo_ordercode;
    }

    public String getOrderinfo_ordertime() {
        return orderinfo_ordertime;
    }

    public void setOrderinfo_ordertime(String orderinfo_ordertime) {
        this.orderinfo_ordertime = orderinfo_ordertime;
    }

    public int getOrderinfo_count() {
        return orderinfo_count;
    }

    public void setOrderinfo_count(int orderinfo_count) {
        this.orderinfo_count = orderinfo_count;
    }

    public String getOrderinfo_email() {
        return orderinfo_email;
    }

    public void setOrderinfo_email(String orderinfo_email) {
        this.orderinfo_email = orderinfo_email;
    }

    public int getOrderinfo_productcode() {
        return orderinfo_productcode;
    }

    public void setOrderinfo_productcode(int orderinfo_productcode) {
        this.orderinfo_productcode = orderinfo_productcode;
    }


}