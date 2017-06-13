package com.baibao.cloud.present;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.baibao.cloud.SynergyMerItem;
import com.baibao.cloud.SynergyOrderItem;
import com.baibao.cloud.WDTMerItem;
import com.baibao.cloud.bean.DbResultBean;
import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderItemInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfo;
import com.baibao.cloud.bean.SynergyOrderPostInfoTest;
import com.baibao.cloud.bean.SynergyOrderPostResult;
import com.baibao.cloud.bean.SynergyRequest;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.bean.WandiantongLoginInfo;
import com.baibao.cloud.bean.WandiantongOrderMerPost;
import com.baibao.cloud.bean.WandiantongOrderPost;
import com.baibao.cloud.bean.WandiantongOrderPostCallBack;
import com.baibao.cloud.db.CloudDBUtil;
import com.baibao.cloud.model.IRequestMode;
import com.baibao.cloud.model.converter.CustomGsonConverter;
import com.baibao.cloud.model.converter.CustomXmlConverter;
import com.baibao.cloud.utils.Constant;
import com.baibao.cloud.view.IDbView;
import com.baibao.cloud.view.ILoginView;
import com.baibao.cloud.view.IOrderItemView;
import com.baibao.cloud.view.IOrderView;
import com.baibao.cloud.view.IView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/4/6.
 */

public class DbPresent implements IDbPresent {
    private IView iView;
    private Context context;
    private IDbPresent iRequestMode;
    private static CloudDBUtil dbUtil;
    private static DbPresent instance = null;

    public void setView(Activity activity) {
        iView = (IView) activity;
    }

    public void setView(Fragment fragment) {
        iView = (IView) fragment;
    }

    private DbPresent(Context context_) {
        context = context_;
    }

    public static synchronized DbPresent getInstance(Context context) {
        if (instance == null) {
            dbUtil = new CloudDBUtil(context);
            return new DbPresent(context);
        }
        return instance;
    }



    @Override
    public void InsertReplaceWDTMer(WandiantongItemInfo item) {

        Observable.fromIterable(item.getContent())
                .map(s->dbUtil.insertOrReplaceWDTMer(s))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveInsertDb(Constant.CAHCE_WDT_MERCHANDISE,new DbResultBean(s?1:0,s?"万店通商品缓存更新成功":"万店通商品缓存更新失败")));

    }

    @Override
    public void GetWDTMer() {



    }

    @Override
    public void InsertReplaceSynergyOrder(SynergyOrderInfo item) {
        Observable.just(dbUtil.insertOrReplaceSynergyOrder(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveInsertDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"星利源订单缓存更新成功":"星利源订单缓存更新失败")));
    }

    @Override
    public void InsertReplaceSynergyMer( SynergyOrderItemInfo item) {
        Observable.just(dbUtil.insertOrReplaceSynergyMer(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveInsertDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"星利源物品缓存更新成功":"星利源物品缓存更新失败")));
    }

    @Override
    public void DeleteSynergyOrder(SynergyOrderInfo item) {
        Observable.just(dbUtil.deleteSynergyOrder(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveDeleteDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"星利源订单缓存删除成功":"星利源订单缓存删除失败")));
    }

    @Override
    public void DeleteSynergyMer(SynergyMerItem item) {
        Observable.just(dbUtil.deleteSynergyMer(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveDeleteDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"星利源订单缓存删除成功":"星利源订单缓存删除失败")));

    }


    @Override
    public void GetSynergyOrder() {
        Observable.just(dbUtil.listAllSynergyOrder())
                .onErrorReturn(s -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveReadDbSynergyOrder(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s.size()>0?1:0,s.size()>0?"星利源订单缓存获取成功":"星利源订单缓存获取失败"),
                                s));
    }

    @Override
    public void GetSynergyMer() {
        Observable.just(dbUtil.listAllSynergyMer())
                .onErrorReturn(s -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveReadDbSynergyMer(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s.size()>0?1:0,s.size()>0?"星利源商品缓存获取成功":"星利源商品缓存获取失败"),
                                s));
    }
}
