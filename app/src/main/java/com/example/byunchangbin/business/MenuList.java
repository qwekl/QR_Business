package com.example.byunchangbin.business;

/**
 * Created by byunchangbin on 2018-06-05.
 */
public class MenuList {

    String menuname;
    String price;
    String menuid;
    String filename;
    String description;

    public MenuList(String menuname, String price, String menuid, String filename, String description) {
        this.menuname = menuname;
        this.price = price;
        this.menuid = menuid;
        this.filename = filename;
        this.description = description;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
