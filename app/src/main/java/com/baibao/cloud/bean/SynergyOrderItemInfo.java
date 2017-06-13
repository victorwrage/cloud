package com.baibao.cloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.baibao.cloud.SynergyMerItem;

/**
 * Info: 单条订单信息
 * Created by xiaoyl
 * 创建时间:2017/4/17 14:36
 */

public class SynergyOrderItemInfo implements Parcelable {
    String sku;//商品编码
    String qr_code;//商品条码
    String wpmc;//物品名称
    String item_code;//物品编码
    String code;//物品
    String ddh;

    public String getDdh() {
        return ddh;
    }

    public void setDdh(String ddh) {
        this.ddh = ddh;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItem_code() {
        return item_code;

    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    String wpdj;//物品单价
    String wpsl;//物品数量
    String cwpsl;//缓存的物品数量
    String wpkc;//物品库存 ，从万店通得到

    public String getCwpsl() {
        return cwpsl;
    }

    public void setCwpsl(String cwpsl) {
        this.cwpsl = cwpsl;
    }

    public String getWpkc() {
        return wpkc;
    }

    public void setWpkc(String wpkc) {
        this.wpkc = wpkc;
    }

    String dw;//单位
    String address;

    protected SynergyOrderItemInfo(Parcel in) {
        sku = in.readString();
        qr_code = in.readString();
        item_code = in.readString();
        code = in.readString();
        wpmc = in.readString();
        wpdj = in.readString();
        wpsl = in.readString();
        cwpsl = in.readString();
        dw = in.readString();
        address = in.readString();
        status = in.readInt() == 1 ? true : false;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    boolean status;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getWpmc() {
        return wpmc;
    }

    public void setWpmc(String wpmc) {
        this.wpmc = wpmc;
    }

    public String getWpdj() {
        return wpdj;
    }

    public void setWpdj(String wpdj) {
        this.wpdj = wpdj;
    }

    public String getWpsl() {
        return wpsl;
    }

    public void setWpsl(String wpsl) {
        this.wpsl = wpsl;
    }


    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    @Override
    public String toString() {
        return sku + "--" + wpmc + "--" + wpdj + "--" + wpsl + "--" + "--" + dw;
    }

    public static final Creator<SynergyOrderItemInfo> CREATOR = new Creator<SynergyOrderItemInfo>() {
        @Override
        public SynergyOrderItemInfo createFromParcel(Parcel in) {
            return new SynergyOrderItemInfo(in);
        }

        @Override
        public SynergyOrderItemInfo[] newArray(int size) {
            return new SynergyOrderItemInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sku);
        dest.writeString(qr_code);
        dest.writeString(item_code);
        dest.writeString(code);
        dest.writeString(wpmc);
        dest.writeString(wpdj);
        dest.writeString(wpsl);
        dest.writeString(cwpsl);
        dest.writeString(dw);
        dest.writeString(address);
        dest.writeInt(status ? 1 : 0);
    }

    @Override
    public boolean equals(Object obj_) {
        if(!(obj_ instanceof SynergyMerItem)){
            return false;
        }
        SynergyMerItem obj = (SynergyMerItem)obj_;
        if (obj.getDdh() != null && obj.getSku() != null) {
            if (obj.getDdh() == getDdh() && obj.getSku() == getSku()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        String id_str = getDdh() + getSku();
        int ids = id_str.hashCode();
        return ids;
    }
}
