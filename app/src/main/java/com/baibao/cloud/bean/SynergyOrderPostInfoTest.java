package com.baibao.cloud.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Info: 提交的订单信息
 * Created by xiaoyl
 * 创建时间:2017/4/24 14:36
 */

public class SynergyOrderPostInfoTest {

    public SynergyOrderPostInfoTest(String ddh_, String jhzt_){
        this.ddh=ddh_;
        this.jhzt=jhzt_;

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


    @SerializedName("ddh")
    public String ddh;//
    @SerializedName("jhzt")
    public String jhzt;//


    @Override
    public String toString() {
        return "ddh:"+ddh+"|"+"jhzt:"+jhzt+"|"+"ckmx:";
    }



}
