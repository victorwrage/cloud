package com.baibao.cloud;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "WDTMER_ITEM".
 */
public class WDTMerItem {

    private Long id;
    private String code;
    private String barcode;
    private String type_code;
    private String name;
    private String unit;
    private String stock;
    private String price;
    private String purchase;
    private String address;
    private String createtime;
    private String mem_price;
    private String company_id;
    private String total_num;
    private String minstockm;
    private String remark;

    public WDTMerItem() {
    }

    public WDTMerItem(Long id) {
        this.id = id;
    }

    public WDTMerItem(Long id, String code, String barcode, String type_code, String name, String unit, String stock, String price, String purchase, String address, String createtime, String mem_price, String company_id, String total_num, String minstockm, String remark) {
        this.id = id;
        this.code = code;
        this.barcode = barcode;
        this.type_code = type_code;
        this.name = name;
        this.unit = unit;
        this.stock = stock;
        this.price = price;
        this.purchase = purchase;
        this.address = address;
        this.createtime = createtime;
        this.mem_price = mem_price;
        this.company_id = company_id;
        this.total_num = total_num;
        this.minstockm = minstockm;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
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

    public String getMinstockm() {
        return minstockm;
    }

    public void setMinstockm(String minstockm) {
        this.minstockm = minstockm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
