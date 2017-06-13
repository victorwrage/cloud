package com.baibao.cloud.bean;

import java.util.ArrayList;

/**
 * Info: 提交订单信息的结果
 * Created by xiaoyl
 * 创建时间:2017/4/24 14:36
 */

public class SynergyOrderPostResult {
    public String getGxxx() {
        return gxxx;
    }

    public void setGxxx(String gxxx) {
        this.gxxx = gxxx;
    }

    public String getXxms() {
        return xxms;
    }

    public void setXxms(String xxms) {
        this.xxms = xxms;
    }

    String gxxx;//
    String xxms;//

    @Override
    public String toString() {
        return gxxx+xxms;
    }
}
