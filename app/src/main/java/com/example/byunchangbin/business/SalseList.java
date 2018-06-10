package com.example.byunchangbin.business;

public class SalseList {
    String id;
    String menuname;
    String price;
    String count;
    String userid;
    String phonenumber;

    public SalseList(String id, String menuname, String price, String count, String userid, String phonenumber) {
        this.id = id;
        this.menuname = menuname;
        this.price = price;
        this.count = count;
        this.userid = userid;
        this.phonenumber = phonenumber;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getPrice() {return price;}

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {this.count = count; }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


}
