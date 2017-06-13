package com.baibao.cloud.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Info: 从星利源得到的客户信息
 * Created by xiaoyl
 * 创建时间:2017/4/17 14:36
 */

public class SynergyOrderInfo implements Parcelable {
    String ddh;
    String qy;
    String ddje;
    int jhzt;

    protected SynergyOrderInfo(Parcel in) {
        ddh = in.readString();
        qy = in.readString();
        ddje = in.readString();
        jhzt = in.readInt();
        status_str = in.readString();
    }
    public SynergyOrderInfo(){

    }
    public static final Creator<SynergyOrderInfo> CREATOR = new Creator<SynergyOrderInfo>() {
        @Override
        public SynergyOrderInfo createFromParcel(Parcel in) {
            return new SynergyOrderInfo(in);
        }

        @Override
        public SynergyOrderInfo[] newArray(int size) {
            return new SynergyOrderInfo[size];
        }
    };

    public String getStatus_str() {
        return status_str;
    }

    public void setStatus_str(String status_str) {
        this.status_str = status_str;
    }

    String status_str;


    public String getDdh() {
        return ddh;
    }

    public void setDdh(String ddh) {
        this.ddh = ddh;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    public String getDdje() {
        return ddje;
    }

    public void setDdje(String ddje) {
        this.ddje = ddje;
    }

    public int getJhzt() {
        return jhzt;
    }

    public void setJhzt(int jhzt) {
        this.jhzt = jhzt;
    }

    @Override
    public String toString() {
        return ddh + "--" + qy + "--" + ddje + "--" + jhzt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ddh);
        dest.writeString(qy);
        dest.writeString(ddje);
        dest.writeInt(jhzt);
        dest.writeString(status_str);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SynergyOrderInfo) {
            return ddh.equals(((SynergyOrderInfo) obj).getDdh());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ddh.hashCode();
    }
}
