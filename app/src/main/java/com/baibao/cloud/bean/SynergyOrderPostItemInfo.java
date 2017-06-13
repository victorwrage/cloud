package com.baibao.cloud.bean;

/**
 * Info: 单条订单信息
 * Created by xiaoyl
 * 创建时间:2017/4/24 14:36
 */

public class SynergyOrderPostItemInfo {
    String sku;//
    String wpsl;//

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getWpsl() {
        return wpsl;
    }

    public void setWpsl(String wpsl) {
        this.wpsl = wpsl;
    }

    @Override
    public String toString() {
        return "sku:"+sku+"|wpsl:"+wpsl;
    }
}
