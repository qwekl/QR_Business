package com.example.byunchangbin.business;

/**
 * Created by byunchangbin on 2018-06-05.
 */
public class MenuList {

    String menuname;
    String price;
    String menuid;

    public MenuList(String menuname, String price, String menuid) {
        this.menuname = menuname;
        this.price = price;
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }
}
