package com.baibao.cloud.bean;

/**
 * Info: 万店通订单商品提交
 * Created by xiaoyl
 * 创建时间:2017/4/22 14:36
 */

public class WandiantongOrderMerPost {
    String secret;
    String ucode;
    String ocode;
    String pcode;
    String company_id;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    String unit;
    String price;
    String name;
    String number;

}
