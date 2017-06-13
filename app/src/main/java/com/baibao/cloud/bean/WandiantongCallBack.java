package com.baibao.cloud.bean;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/25 18:34
 */

public class WandiantongCallBack {
    String errcode;
    String errmsg;

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
        return errcode+errmsg;
    }
}
