package com.baibao.cloud.bean;

import java.util.ArrayList;

/**
 * Info: 万店通登录
 * Created by xiaoyl
 * 创建时间:2017/4/22 14:36
 */

public class WandiantongItemInfo {
    String errcode;
    String errmsg;
    ArrayList<OrderInfo> content;

    public  ArrayList<OrderInfo> getContent() {
        return content;
    }

    public void setContent( ArrayList<OrderInfo> content) {
        this.content = content;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        if(content==null){
            return  errcode+errmsg;
        }
        return errcode+errmsg+"content======="+content.toString();
    }

    public class OrderInfo {
        String code;
        String barcode;
        String type_code;
        String name;
        String unit;
        String stock;
        String price;
        String purchase;
        String address;
        String createtime;
        String mem_price;
        String company_id;

        public String getMinstock() {
            return minstock;
        }

        public void setMinstock(String minstock) {
            this.minstock = minstock;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getSaled() {
            return saled;
        }

        public void setSaled(String saled) {
            this.saled = saled;
        }

        public String getCost_inventory() {
            return cost_inventory;
        }

        public void setCost_inventory(String cost_inventory) {
            this.cost_inventory = cost_inventory;
        }

        String total_num;
        String minstock;
        String imgurl;
        String saled;
        String cost_inventory;
        String remark;
        String item_code;
        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getItem_code() {
            return item_code;
        }

        public void setItem_code(String item_code) {
            this.item_code = item_code;
        }



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPurchase() {
            return purchase;
        }

        public void setPurchase(String purchase) {
            this.purchase = purchase;
        }

        public String getMem_price() {
            return mem_price;
        }

        public void setMem_price(String mem_price) {
            this.mem_price = mem_price;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getTotal_num() {
            return total_num;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }



        @Override
        public String toString() {
            return code +
                    barcode +
                    type_code +
                    name +
                    unit +
                    stock +
                    price +
                    purchase +
                    address +
                    mem_price +
                    total_num +
                    minstock +
                    imgurl +
                    saled +
                    cost_inventory +
                    item_code +
                    remark +
                    createtime +
                    company_id;
        }
    }
}
