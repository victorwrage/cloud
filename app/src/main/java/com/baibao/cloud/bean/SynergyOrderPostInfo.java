package com.baibao.cloud.bean;

import java.util.ArrayList;

/**
 * Info: 提交的订单信息
 * Created by xiaoyl
 * 创建时间:2017/4/24 14:36
 */

public class SynergyOrderPostInfo {

    public SynergyOrderPostInfo(String ddh_,String jhzt_,ArrayList<SynergyOrderPostItemInfoA> ckmx_){
        this.ddh=ddh_;
        this.jhzt=jhzt_;
        this.ckmx=ckmx_;
    }

    public String getDdh() {
        return ddh;
    }

    public void setDdh(String ddh) {
        this.ddh = ddh;
    }

    public String getJhzt() {
        return jhzt;
    }

    public void setJhzt(String jhzt) {
        this.jhzt = jhzt;
    }

    public ArrayList<SynergyOrderPostItemInfoA> getCkmx() {
        return ckmx;
    }

    public void setCkmx(ArrayList<SynergyOrderPostItemInfoA> ckmx) {
        this.ckmx = ckmx;
    }

    public String ddh;//
    public String jhzt;//
    ArrayList<SynergyOrderPostItemInfoA> ckmx;

    @Override
    public String toString() {
        return "ddh:"+ddh+"|"+"jhzt:"+jhzt+"|"+"ckmx:"+ckmx.toString();
    }

    public static class SynergyOrderPostItemInfoA {

        public String sku;//
        public String wpsl;//

        public SynergyOrderPostItemInfoA(String sku_,String wpsl_){
            this.sku=sku_;
            this.wpsl=wpsl_;
        }
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

}
