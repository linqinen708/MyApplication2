package com.yulin.myapplication;

/**
 * Created by lin on 2016/11/21.
 */

public class NormalMaterialPrice {

    private String name;

    private String PinYin;
    private String FirstPinYin;

    private String num;
    private String price;
    private int isRefresh;

    public NormalMaterialPrice() {

    }
    public NormalMaterialPrice(String name, String num, String price, int isRefresh) {
        this.name = name;
        this.num = num;
        this.price = price;
        this.isRefresh = isRefresh;
    }

    @Override
    public String toString() {
        return "NormalMaterialPrice{" +
                "name='" + name + '\'' +
                ", num='" + num + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }

    public String getFirstPinYin() {
        return FirstPinYin;
    }

    public void setFirstPinYin(String firstPinYin) {
        FirstPinYin = firstPinYin;
    }

    public int getIsRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(int isRefresh) {
        this.isRefresh = isRefresh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
