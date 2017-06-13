package com.baibao.cloud.model;



import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderItemInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfoTest;
import com.baibao.cloud.bean.SynergyOrderPostItemInfo;
import com.baibao.cloud.bean.SynergyOrderPostResult;
import com.baibao.cloud.bean.SynergyPayBack;
import com.baibao.cloud.bean.SynergyPayBackResult;
import com.baibao.cloud.bean.WandiantongCallBack;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.bean.WandiantongLoginInfo;
import com.baibao.cloud.bean.WandiantongOrderMerPost;
import com.baibao.cloud.bean.WandiantongOrderPost;
import com.baibao.cloud.bean.WandiantongOrderPostCallBack;
import com.baibao.cloud.bean.xml_check_info_root;
import com.baibao.cloud.bean.xml_login_info_root;
import com.baibao.cloud.bean.xml_pay_info_root;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Info:接口实现类
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:42
 */

public class RequestModelImpl implements IRequestMode {
    IRequestMode iRequestMode;

    @Override
    public Observable<ArrayList<SynergyOrderInfo>> QueryOrder(@Query("mdID") String mdID, @Query("kssj") String kssj, @Query("jssj") String jssj) {
        return iRequestMode.QueryOrder( mdID,  kssj,  jssj);
    }

    @Override
    public Observable<ArrayList<SynergyOrderItemInfo>> QueryOrderSingle(@Query("ddh") String ddh) {
        return iRequestMode.QueryOrderSingle( ddh);
    }

    @Override
    public Observable<SynergyOrderPostResult> CommitOrder(@Field("data") String data) {
        return iRequestMode.CommitOrder(data);
    }

    @Override
    public Observable<SynergyOrderPostResult> CommitOrderTest(@Body SynergyOrderPostInfoTest data) {
        return iRequestMode.CommitOrderTest(data);
    }


    @Override
    public Observable<WandiantongCallBack> UpdateMerCount(@Query("g") String g,@Query("m") String m,@Query("a") String a,@Field("secret") String secret, @Field("ucode") String ucode, @Field("code") String code, @Field("stock") String stock, @Field("company_id") String company_id) {
        return iRequestMode.UpdateMerCount(g,m,a,secret,ucode,code,stock,company_id);
    }

    @Override
    public Observable<WandiantongLoginInfo> Login(@Field("username") String username, @Field("password") String password, @Field("company_id") String company_id) {
        return iRequestMode.Login(username, password,company_id);
    }

    @Override
    public Observable<WandiantongItemInfo> FetchOrderAdd(@Field("secret") String secret,@Field("company_id") String company_id,@Field("ucode") String ucode) {
        return iRequestMode.FetchOrderAdd(secret,company_id,ucode);
    }

    @Override
    public Observable<WandiantongOrderPostCallBack> PostWDTOrder(@Field("secret") String secret,@Field("totalprice") String totalprice,@Field("ucode") String ucode,@Field("company_id") String company_id) {
        return iRequestMode.PostWDTOrder(secret,totalprice,ucode,company_id);
    }

    @Override
    public Observable<WandiantongOrderPostCallBack> PostWDTOrderMer(@Field("secret") String secret, @Field("ocode") String ocode, @Field("ucode") String ucode, @Field("pcode") String pcode,
                                                                    @Field("company_id") String company_id, @Field("unit") String unit, @Field("price") String price, @Field("name") String name, @Field("number") String number) {
        return iRequestMode.PostWDTOrderMer(secret,ocode,ucode,pcode,company_id,unit,price,name,number);
    }

    @Override
    public Observable<WandiantongOrderPostCallBack> PostWDTOrderStatus(@Field("secret") String secret, @Field("paystate") String paystate, @Field("code") String code, @Field("ucode") String ucode) {
        return iRequestMode.PostWDTOrderStatus(secret,paystate,code,ucode);
    }

}
