package com.baibao.cloud.present;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.baibao.cloud.SynergyOrderItem;
import com.baibao.cloud.bean.CheckPayInfo;
import com.baibao.cloud.bean.LoginInfoRequest;
import com.baibao.cloud.bean.PayInfo;
import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfoTest;
import com.baibao.cloud.bean.SynergyOrderPostItemInfo;
import com.baibao.cloud.bean.SynergyOrderPostResult;
import com.baibao.cloud.bean.SynergyPayBack;
import com.baibao.cloud.bean.SynergyPayBackResult;
import com.baibao.cloud.bean.SynergyRequest;
import com.baibao.cloud.bean.WandiantongCallBack;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.bean.WandiantongLoginInfo;
import com.baibao.cloud.bean.WandiantongOrderMerPost;
import com.baibao.cloud.bean.WandiantongOrderPost;
import com.baibao.cloud.bean.WandiantongOrderPostCallBack;
import com.baibao.cloud.bean.xml_check_info_root;
import com.baibao.cloud.bean.xml_login_info_root;
import com.baibao.cloud.bean.xml_pay_info_root;
import com.baibao.cloud.db.CloudDBUtil;
import com.baibao.cloud.model.IRequestMode;
import com.baibao.cloud.model.converter.CustomGsonConverter;
import com.baibao.cloud.model.converter.CustomXmlConverter;
import com.baibao.cloud.utils.Constant;
import com.baibao.cloud.view.ILoginView;
import com.baibao.cloud.view.IOrderItemView;
import com.baibao.cloud.view.IOrderView;
import com.baibao.cloud.view.IPayView;
import com.baibao.cloud.view.IView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;


/**
 * Created by Administrator on 2017/4/6.
 */

public class QueryPresent implements IRequestPresent {
    private IView iView;
    private Context context;
    private IRequestMode iRequestMode;
    private CloudDBUtil dbUtil;
    private static QueryPresent instance = null;

    public void setView(Activity activity) {
        iView = (IView) activity;
    }

    public void setView(Fragment fragment) {
        iView = (IView) fragment;
    }

    private QueryPresent(Context context_) {
        context = context_;
    }

    public static synchronized QueryPresent getInstance(Context context) {
        if (instance == null) {
            return new QueryPresent(context);
        }
        return instance;
    }

    public void initRetrofit(String url, boolean isXml) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        try {
            if (isXml) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .client(client)
                        .addConverterFactory(CustomXmlConverter.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                iRequestMode = retrofit.create(IRequestMode.class);
            } else {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                iRequestMode = retrofit.create(IRequestMode.class);
            }

        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
        }
    }

    public void initRetrofit2(String url, boolean isXml) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(CustomGsonConverter.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                    .build();
            iRequestMode = retrofit.create(IRequestMode.class);

        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void QueryOder(SynergyRequest synergyRequest) {

        iRequestMode.QueryOrder(synergyRequest.getMdlD(), synergyRequest.getKssj(), synergyRequest.getJssj())
                .onErrorReturn(s -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveOrder(s));
    }

    @Override
    public void QueryOderSingle(String order_no) {

        iRequestMode.QueryOrderSingle(order_no)
                .onErrorReturn(s -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderItemView) iView).ResolveOrderItem(s));
    }

    @Override
    public void Login(String username, String password, String company) {
        iRequestMode.Login(username, password, company)
                .onErrorReturn(s -> new WandiantongLoginInfo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((ILoginView) iView).ResolveLoginInfo(s));
    }

    @Override
    public void FetchOrderAdd(String secret, String company_id,String ucode) {
        // iRequestMode.FetchOrderAdd("Api","Product","SearchAllProduct",secret,company_id)
        iRequestMode.FetchOrderAdd(secret, company_id,ucode)
                .onErrorReturn(s -> new WandiantongItemInfo())

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveOrderAdd(s));
    }

    @Override
    public void CommitOrder(String data) {
        iRequestMode.CommitOrder(data)
                .onErrorReturn(s -> new SynergyOrderPostResult())
               // .doOnError(s -> s.fillInStackTrace())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveOrderCommit(s));

    }

    @Override
    public void CommitOrderTest(SynergyOrderPostInfoTest data) {
        iRequestMode.CommitOrderTest(data)
                .onErrorReturn(s -> new SynergyOrderPostResult())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveOrderCommit(s));
    }

    @Override
    public void PostWDTOrder(WandiantongOrderPost wop) {
        iRequestMode.PostWDTOrder(wop.getSecret(), wop.getTotalprice(), wop.getUcode(), wop.getCompany_id())
                .onErrorReturn(s -> new WandiantongOrderPostCallBack())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveWDTOrderPost(s));
    }

    @Override
    public void PostWDTOrderMer(WandiantongOrderMerPost womp) {
        iRequestMode.PostWDTOrderMer(womp.getSecret(), womp.getOcode(), womp.getUcode(), womp.getPcode(), womp.getCompany_id(), womp.getUnit(), womp.getPrice(), womp.getName(), womp.getNumber())
                .onErrorReturn(s -> new WandiantongOrderPostCallBack())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveWDTOrderMerPost(s));
    }

    @Override
    public void PostWDTOrderStatus(String secret, String paystate, String code, String ucode) {
        iRequestMode.PostWDTOrderStatus(secret, paystate, code, ucode)
                .onErrorReturn(s -> new WandiantongOrderPostCallBack())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveWDTOrderStatusPost(s));
    }

}
