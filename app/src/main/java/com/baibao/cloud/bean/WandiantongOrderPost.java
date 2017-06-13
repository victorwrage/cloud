package com.baibao.cloud.bean;

import java.util.ArrayList;

/**
 * Info: 万店通订单提交
 * Created by xiaoyl
 * 创建时间:2017/4/22 14:36
 */

public class WandiantongOrderPost {
    String secret;
    String ucode;
    String totalprice;
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

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }
}
