package com.example.finalproject;

public class OrderVO {

    /*계정테이블(account)*/
    private String email;//이메일(아이디대체),주문테이블 외래키(주문이메일)
    private String name;//이름
    private String tel;//전화번호

    /*상품테이블(productinfo_order)*/
    private int productcode;//상품코드,주문테이블 외래키(주문상품코드)
    private String productname;//상품이름
    private int price;//가격
    private String image;//이미지

    /*주문테이블(orderinfo)*/
    private int ordercode;//주문코드
    private String ordertime;//주문시간
    private int count;//개수

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getProductcode() {
        return productcode;
    }

    public void setProductcode(int productcode) {
        this.productcode = productcode;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(int ordercode) {
        this.ordercode = ordercode;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}