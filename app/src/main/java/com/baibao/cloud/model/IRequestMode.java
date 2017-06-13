package com.baibao.cloud.model;

import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderItemInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfoTest;
import com.baibao.cloud.bean.SynergyOrderPostResult;
import com.baibao.cloud.bean.WandiantongCallBack;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.bean.WandiantongLoginInfo;
import com.baibao.cloud.bean.WandiantongOrderPostCallBack;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xyl on 2017/4/6.
 */

public interface IRequestMode {

    @GET("synergyMallServcie/order/getOrderByTime.jhtml?")
    Observable<ArrayList<SynergyOrderInfo>> QueryOrder(@Query("mdID") String mdID, @Query("kssj") String kssj, @Query("jssj") String jssj);

    @GET("synergyMallServcie/order/getDetailByScmForZDW.jhtml?")
    Observable<ArrayList<SynergyOrderItemInfo>> QueryOrderSingle(@Query("ddh") String ddh);

    @FormUrlEncoded
    @POST("synergyMallServcie/order/updateSendQty.jhtml?")
    Observable<SynergyOrderPostResult> CommitOrder(@Field("data") String data);


    @POST("synergyMallServcie/order/updateSendQtyCopy.jhtml?")
    Observable<SynergyOrderPostResult> CommitOrderTest(@Body SynergyOrderPostInfoTest data);
    //Observable<SynergyOrderPostResult> CommitOrderTest(@Field("ddh") String ddh,@Field("jhzt") String jhzt);


    @FormUrlEncoded
    @POST("index.php?")
    Observable<WandiantongCallBack> UpdateMerCount(@Field("g") String g,@Field("m") String m,@Field("a") String a,@Field("secret") String secret, @Field("ucode") String ucode, @Field("code")String code, @Field("stock") String stock, @Field("company_id") String company_id);


    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=Login")
    Observable<WandiantongLoginInfo> Login(@Field("username") String username, @Field("password") String password, @Field("company_id") String company_id);

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Product&a=SearchAllProduct")
    Observable<WandiantongItemInfo> FetchOrderAdd(@Field("secret") String secret,@Field("company_id") String company_id,@Field("ucode") String ucode);

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Order&a=AddOrder")
    Observable<WandiantongOrderPostCallBack> PostWDTOrder(@Field("secret") String secret, @Field("totalprice") String totalprice, @Field("ucode") String ucode, @Field("company_id") String company_id);

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Order&a=AddOrderLists")
    Observable<WandiantongOrderPostCallBack> PostWDTOrderMer(@Field("secret") String secret,@Field("ocode") String ocode,@Field("ucode") String ucode,@Field("pcode") String pcode,
    @Field("company_id") String company_id,@Field("unit") String unit,@Field("price") String price,@Field("name") String name,@Field("number") String number);


    @FormUrlEncoded
    @POST("index.php?g=Api&m=Order&a=EditOrderStatus")
    Observable<WandiantongOrderPostCallBack> PostWDTOrderStatus(@Field("secret") String secret, @Field("paystate") String paystate, @Field("code") String code, @Field("ucode") String ucode);



}
