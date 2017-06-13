package com.baibao.cloud;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibao.cloud.adapter.OrderAdapter;
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
import com.baibao.cloud.present.DbPresent;
import com.baibao.cloud.present.QueryPresent;
import com.baibao.cloud.service.RestartService;
import com.baibao.cloud.utils.Constant;
import com.baibao.cloud.utils.SortOrderComparator;
import com.baibao.cloud.utils.SortOrderStateComparator;
import com.baibao.cloud.utils.Utils;
import com.baibao.cloud.utils.VToast;
import com.baibao.cloud.view.CustomDatePicker;
import com.baibao.cloud.view.IDbView;
import com.baibao.cloud.view.ILoginView;
import com.baibao.cloud.view.IOrderView;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首先请求登录万店通，然后获取订单信息
 */
public class MainActivity extends BaseActivity implements IOrderView, ILoginView, IDbView {
    private static final String COOKIE_KEY = "cookie";
    private static final int ORDER_FETCH_SUCCESS = 10;//  订单获取成功
    private static final int ORDER_FETCH_FAIL = ORDER_FETCH_SUCCESS + 1;// 订单获取失败
    private static final int ORDER_FETCH_NOT = ORDER_FETCH_FAIL + 1;// 订单没记录
    private static final int LOGIN_SUCCESS = ORDER_FETCH_NOT + 1;// 登录万店通成功
    private static final int LOGIN_FAIL = LOGIN_SUCCESS + 1;// 登录万店通成功
    private static final int POST_ITEM_SUCESS = LOGIN_FAIL + 1;//提交单条成功
    private static final int POST_ITEM_FAIL = POST_ITEM_SUCESS + 1;//
    private static final int POST_SUCCESS = POST_ITEM_FAIL + 1;// 提交成功
    private static final int POST_FAIL = POST_SUCCESS + 1;//
    private static final int POST_STOCK_ITEM_SUCCESS = POST_FAIL + 1;//提交库存成功
    private static final int POST_STOCK_ITEM_FAIL = POST_STOCK_ITEM_SUCCESS + 1;//

    private static final int POST_WDT_STOCK_ITEM_SUCCESS = POST_STOCK_ITEM_FAIL + 1;//提交万店通销售单成功
    private static final int POST_WDT_STOCK_ITEM_FAIL = POST_WDT_STOCK_ITEM_SUCCESS + 1;//

    private static final int POST_WDT_STOCK_ITEM_MER_SUCCESS = POST_WDT_STOCK_ITEM_FAIL + 1;//提交万店通销售单商品成功
    private static final int POST_WDT_STOCK_ITEM_MER_FAIL = POST_WDT_STOCK_ITEM_MER_SUCCESS + 1;//

    private static final int POST_WDT_STOCK_ITEM_STATUS_SUCCESS = POST_WDT_STOCK_ITEM_MER_FAIL + 1;//更改万店通订单状态
    private static final int POST_WDT_STOCK_ITEM_STATUS_FAIL = POST_WDT_STOCK_ITEM_STATUS_SUCCESS + 1;//

    private static final int POST_STOCK_SUCCESS = POST_WDT_STOCK_ITEM_STATUS_FAIL + 1;//

    private static final int FETCH_MER_SUCCESS = POST_STOCK_SUCCESS + 1;// 获取万店通商品成功
    private static final int FETCH_MER_FAIL = FETCH_MER_SUCCESS + 1;// 获取万店通商品失败

    private static final int ORDER_STATUS_VERIFY_NOT = 2;//订单未拣货
    private static final int ORDER_STATUS_VERIFY_POST = 0;//订单已拣货已提交
    private static final int ORDER_STATUS_VERIFY = 1;//订单已拣货

    /**
     * 对话框类型
     */
    private static final int ORDER_HAS_CONFIRM = 1024;
    private static final int EXIT_CONFIRM = ORDER_HAS_CONFIRM + 1;
    private static final int NETWORD_CONFIRM = EXIT_CONFIRM + 1;
    private static final int POST_RETRY_CONFIRM = NETWORD_CONFIRM + 1;
    private static final int POST_CONFIRM = POST_RETRY_CONFIRM + 1;
    private static final int POST_WDT_ITEM_CONFIRM = POST_CONFIRM + 1;
    private static final int POST_WDT_ITEM_MER_CONFIRM = POST_WDT_ITEM_CONFIRM + 1;
    private static final int POST_WDT_ITEM_STATUS_CONFIRM = POST_WDT_ITEM_MER_CONFIRM + 1;
    @Bind(R.id.rl_xly_back)
    RelativeLayout rl_xly_back;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.iv_picker)
    ImageView iv_picker;
    @Bind(R.id.search_start_tv)
    TextView search_start_tv;
    @Bind(R.id.search_end_tv)
    TextView search_end_tv;
    @Bind(R.id.search_tv)
    TextView search_tv;

    @Bind(R.id.tv_xly_account)
    TextView tv_xly_account;
    @Bind(R.id.tv_xly_jieshou)
    TextView tv_xly_jieshou;
    @Bind(R.id.lv_xyl)
    ListView lv_xyl;
    @Bind(R.id.date_lay)
    LinearLayout date_lay;
    @Bind(R.id.empty_lay)
    RelativeLayout empty_lay;

    CustomDatePicker customDatePicker;

    EditText et_username;
    EditText et_shopid;
    EditText et_password;
    CheckBox chk_remember;
    TextView btn_login;

    View popupWindowView;
    private PopupWindow popupWindow;
    WandiantongLoginInfo cur_user;
    SharedPreferences sp;

    QueryPresent present;
    DbPresent dbPresent;
    Utils util;
    OrderAdapter adapter;
    ArrayList<SynergyOrderInfo> items;
    ArrayList<SynergyOrderPostInfo> commit_items;
    ArrayList<WandiantongOrderPost> commit_wdt_items;
    ArrayList<ArrayList<WandiantongOrderMerPost>> commit_wdt_mer_items;
    ArrayList<SynergyOrderInfo> confirmed_items;
    ArrayList<SynergyOrderItemInfo> backMerItems;
    SynergyOrderInfo cur_item;

    boolean isLogin;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case LOGIN_SUCCESS:
                    isLogin = true;
                    hideWaitDialog();
                    popupWindow.dismiss();

                    SynergyRequest request = new SynergyRequest();
                    request.setMdlD(cur_user.getContent().getCompany_id());
                    //request.setMdlD("1165");
                    // long dec = (long) 3600000 * (long) 24 * (long) 1;
                    long dec = util.getTodayZero();

                    String start_t = currentDate( dec, "yyyy-MM-dd HH:mm:ss");
                    String end_t = currentDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
                    KLog.v(start_t + "----" + end_t);
                    request.setKssj(start_t);
                    request.setJssj(end_t);
                    present.initRetrofit(Constant.URL_SYNERGY, false);

                    showWaitDialog("正在查询订单");
                    present.QueryOder(request);
                    break;
                case LOGIN_FAIL:
                    hideWaitDialog();
                    VToast.toast(context, "登录失败");
                    showPopupWindow();
                    break;
                case ORDER_FETCH_FAIL:
                    VToast.toast(context, "没有订单");
                    hideWaitDialog();
                    break;
                case ORDER_FETCH_SUCCESS:
                    hideWaitDialog();
                    GetAllMerchandise();
                    adapter.notifyDataSetChanged();
                    break;
                case ORDER_FETCH_NOT:
                    hideWaitDialog();
                    break;
                case FETCH_MER_FAIL:
                    KLog.v("获取万店通商品失败");
                    break;
                case FETCH_MER_SUCCESS:
                    KLog.v("获取万店通商品成功");
                    break;
                case POST_ITEM_SUCESS:
                    KLog.v(commit_items.size() + "");

                    for (SynergyOrderPostInfo.SynergyOrderPostItemInfoA postedItem : commit_items.get(0).getCkmx()) {
                        SynergyMerItem d_i = new SynergyMerItem();
                        String id_str = commit_items.get(0).getDdh() + postedItem.getSku();
                        long ids = (long) id_str.hashCode();
                        d_i.setId(ids);
                        dbPresent.DeleteSynergyMer(d_i);
                    }
                    commit_items.remove(0);
                    SynergyOrderInfo p_i = confirmed_items.remove(0);
                    dbPresent.DeleteSynergyOrder(items.get(items.indexOf(p_i)));//清除缓存
                    items.get(items.indexOf(p_i)).setStatus_str("已拣货");
                    items.get(items.indexOf(p_i)).setJhzt(ORDER_STATUS_VERIFY_POST);
                    adapter.notifyDataSetChanged();
                    if (commit_items.size() > 0) {//星利源的提交循环
                        doSynergyPost();
                    } else {
                        commit_items = new ArrayList<>();
                        confirmed_items = new ArrayList<>();
                        hideWaitDialog();
                        VToast.toast(context, "提交成功");
                        doWDTOrderPost();
                    }
                    //  doWDTOrderPost();
                    break;
                case POST_ITEM_FAIL:
                    showDialog(POST_CONFIRM, "提示", "提交失败，是否继续？", "是", "否");
                    break;
                case POST_WDT_STOCK_ITEM_SUCCESS:
                    doWDTOrderMerPost();
                    break;
                case POST_WDT_STOCK_ITEM_FAIL:
                    //  showDialog(POST_WDT_ITEM_CONFIRM, "提示", "提交出库订单失败，是否继续？", "重试", null);
                    break;
                case POST_WDT_STOCK_ITEM_MER_SUCCESS:
                    if (commit_wdt_mer_items.get(0).size() == 1) {
                        doWDTOrderStatusPost();
                        commit_wdt_mer_items.get(0).remove(0);

                        return;
                    }

                    if (commit_wdt_mer_items.get(0).size() > 0) {
                        commit_wdt_mer_items.get(0).remove(0);
                        doWDTOrderMerPost();
                    }
                    break;
                case POST_WDT_STOCK_ITEM_MER_FAIL:
                    //   showDialog(POST_WDT_ITEM_MER_CONFIRM, "提示", "提交出库商品失败，是否继续？", "重试", null);
                    break;
                case POST_WDT_STOCK_ITEM_STATUS_SUCCESS:
                    commit_wdt_mer_items.remove(0);
                    commit_wdt_items.remove(0);

                    if (commit_wdt_mer_items.size() > 0) {
                        // doSynergyPost();
                        doWDTOrderPost();
                    } else {
                        handler.sendEmptyMessage(POST_SUCCESS);
                    }
                    break;
                case POST_WDT_STOCK_ITEM_STATUS_FAIL:
                    //  showDialog(POST_WDT_ITEM_STATUS_CONFIRM, "提示", "提交出库订单状态失败，是否继续？", "重试", null);
                    break;
                case POST_SUCCESS:
                    commit_wdt_items = new ArrayList<>();
                    commit_wdt_mer_items = new ArrayList<>();
                    break;
                case POST_FAIL://redundant
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlyordera);
        ButterKnife.bind(MainActivity.this);
        initDate();
        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !isLogin) {
            LoginAuto();
        }
    }

    private void initDate() {
        Constant.activity = 0;
        //  startService(new Intent(MainActivity.this,Service1.class));//进程常驻，
        //  startService(new Intent(MainActivity.this, RestartService.class));

        sp = getSharedPreferences(COOKIE_KEY, 0);
        present = QueryPresent.getInstance(context);
        present.initRetrofit(Constant.URL_WANDIAN, false);
        present.setView(MainActivity.this);
        dbPresent = DbPresent.getInstance(context);
        dbPresent.setView(MainActivity.this);
        util = Utils.getInstance();
        items = new ArrayList<>();

        commit_items = new ArrayList<>();
        commit_wdt_items = new ArrayList<>();
        commit_wdt_mer_items = new ArrayList<>();
        confirmed_items = new ArrayList<>();
        adapter = new OrderAdapter(context, items);
        lv_xyl.setAdapter(adapter);

    }

    private void onItemClick(int position) {
        date_lay.setVisibility(View.GONE);
        cur_item = items.get(position);
    }

    private void initView() {
        RxView.clicks(rl_xly_back).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Exit());
        RxView.clicks(iv_scan).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Scan());
        RxView.clicks(iv_picker).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> ShowScope());
        RxView.clicks(tv_xly_account).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> showPostDialog());
        RxView.clicks(tv_xly_jieshou).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> VerifyOrder());
        popupWindowView = getLayoutInflater().inflate(R.layout.pop_login, null);
        et_username = (EditText) popupWindowView.findViewById(R.id.et_username);

        lv_xyl.setOnItemClickListener((parent, view, position, id) -> onItemClick(position));
        lv_xyl.setEmptyView(findViewById(R.id.empty_lay));

        et_shopid = (EditText) popupWindowView.findViewById(R.id.et_shopid);
        et_password = (EditText) popupWindowView.findViewById(R.id.et_password);
        btn_login = (TextView) popupWindowView.findViewById(R.id.btn_login);

        chk_remember = (CheckBox) popupWindowView.findViewById(R.id.chk_remember);
        RxView.clicks(btn_login).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Login());

        RxView.clicks(search_start_tv).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Select(1));
        RxView.clicks(search_end_tv).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Select(2));
        RxView.clicks(search_tv).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> ApplyScope());

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.AnimationTopFade);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOnDismissListener(() -> onDissmiss());
        et_username.setText(sp.getString("user_name", ""));
        et_password.setText(sp.getString("user_pw", ""));
        et_shopid.setText(sp.getString("shop_id", ""));
        Constant.cookie.put("user_name", sp.getString("user_name", ""));
        Constant.cookie.put("user_pw", sp.getString("user_pw", ""));
        Constant.cookie.put("shop_id", sp.getString("shop_id", ""));


        Drawable drawable = getResources().getDrawable(R.drawable.verify);
        drawable.setBounds(0, 2, 30, 30);
        tv_xly_jieshou.setCompoundDrawables(null, drawable, null, null);

        Drawable drawable2 = getResources().getDrawable(R.drawable.commit);
        drawable2.setBounds(0, 2, 30, 30);
        tv_xly_account.setCompoundDrawables(null, drawable2, null, null);

        Drawable drawable3 =getResources().getDrawable(R.drawable.search);
        drawable3.setBounds(0, 2, 30, 30);
        search_tv.setCompoundDrawables(null, drawable3, null, null);

        Drawable drawable4 = getResources().getDrawable(R.drawable.login);
        drawable4.setBounds(0, 2, 30, 30);
        btn_login.setCompoundDrawables(null, drawable4, null, null);
    }

    /**
     * 显示日期选择
     */
    private void ShowScope() {
        if (date_lay.getVisibility() == View.VISIBLE) {
            date_lay.setVisibility(View.GONE);
        } else {
            long dec = util.getTodayZero();
            String start_t = currentDate(dec, "yyyy-MM-dd HH:mm:ss");
            search_start_tv.setText(start_t);
            search_end_tv.setText(currentDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
            date_lay.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 日期段查询订单
     */
    private void ApplyScope() {
        items.clear();
        if (commit_items != null) {
            commit_items.clear();
        }
        if (commit_wdt_items != null) {
            commit_wdt_items.clear();
        }
        if (confirmed_items != null) {
            confirmed_items.clear();
        }
        if (backMerItems != null) {
            backMerItems.clear();
        }

        date_lay.setVisibility(View.GONE);

        SynergyRequest request = new SynergyRequest();
        request.setMdlD(cur_user.getContent().getCompany_id());
        //request.setMdlD("1165");
        request.setKssj(search_start_tv.getText().toString());
        request.setJssj(search_end_tv.getText().toString());
        present.initRetrofit(Constant.URL_SYNERGY, false);

        showWaitDialog("正在查询订单");
        present.QueryOder(request);
    }

    /**
     * 弹出日期选择框
     *
     * @param type
     */
    private void Select(int type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker(MainActivity.this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                long dec = (long) 3600000 * (long) 24 * (long) 7;
                switch (type) {
                    case 1:
                        if (util.ValidateFormat(time) > util.ValidateFormat(search_end_tv.getText().toString())) {
                            VToast.toast(context, "开始日期不能在结束日期之后");
                            return;
                        }

                        if (util.ValidateFormat(search_end_tv.getText().toString())-util.ValidateFormat(time)>dec) {
                            VToast.toast(context, "查询范围最多为7天");
                            return;
                        }
                        search_start_tv.setText(time + ":00");
                        break;
                    case 2:
                        if (util.ValidateFormat(time) < util.ValidateFormat(search_start_tv.getText().toString())) {
                            VToast.toast(context, "结束日期不能在开始日期之前");
                            return;
                        }

                        if (util.ValidateFormat(time)-util.ValidateFormat(search_start_tv.getText().toString())>dec) {
                            VToast.toast(context, "查询范围最多为7天");
                            return;
                        }
                        search_end_tv.setText(time + ":00");
                        break;
                }
            }
        }, "2010-01-01 00:00", now);
        customDatePicker.showSpecificTime(true);
        customDatePicker.setIsLoop(true);
        customDatePicker.show(search_start_tv.getText().toString());
    }

    private void Exit() {
       /* Intent service = new Intent(context, RestartService.class);
        stopService(service);
        finish();*/
        showDialog(EXIT_CONFIRM, "提示", "是否退出?", "确认", "取消");
    }

    private void Login() {
        if (!util.isNetworkConnected(context)) {
            VToast.toast(context, "没有网络连接");
            return;
        }

        if (et_username.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入用户名");
        } else if (et_password.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入密码");
        } else if (et_shopid.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入门店号");
        } else {
            SharedPreferences.Editor editor = sp.edit();
            if (chk_remember.isChecked()) {
                editor.putString("user_name", et_username.getText().toString().trim());
                editor.putString("user_pw", et_password.getText().toString().trim());
                editor.putString("shop_id", et_shopid.getText().toString().trim());
                editor.commit();
            } else {
                editor.clear();
                editor.commit();
            }
            showWaitDialog("正在登录");
            present.Login(et_username.getText().toString().trim(), et_password.getText().toString().trim(), et_shopid.getText().toString().trim());
        }
    }

    /**
     * 扫描订单
     */
    private void Scan() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * 验证定单数量
     */
    private void VerifyOrder() {
        if (cur_item == null) {
            VToast.toast(context, "请选择一个订单");
            return;
        }
        if (cur_item.getJhzt() == ORDER_STATUS_VERIFY_POST) {
            VToast.toast(context, "订单已经提交，不能拣货");
            return;
        }
        gotoOrderItem(cur_item.getDdh());
    }


    private void showPostDialog() {
        if (commit_items.size() == 0) {
            VToast.toast(context, "没有已经确认的订单");
            return;
        }
        showDialog(POST_CONFIRM, "提示", "确定提交" + commit_items.size() + "个订单吗？提交后不能更改", "是", "否");
    }

    /**
     * 提交订单
     */
    private void Post() {
        KLog.v("Post" + "commit_items-" + commit_items.size());
        showWaitDialog("正在提交");
        doSynergyPost();
    }

    /**
     * 获取万店通所有商品信息
     */
    private void GetAllMerchandise() {
        present.initRetrofit(Constant.URL_WANDIAN, false);
        present.FetchOrderAdd(cur_user.getSecret(), cur_user.getContent().getCompany_id(), cur_user.getContent().getCode());
        //present.FetchOrderAdd(cur_user.getSecret(), "1165");
    }

    /**
     * Post新利源订单状态
     */
    private void doSynergyPost() {
        KLog.v(commit_items.get(0).toString());

        Gson gson = new Gson();
        String strEntity = gson.toJson(commit_items.get(0));
        KLog.v(strEntity);
        present.initRetrofit(Constant.URL_SYNERGY, false);
        present.CommitOrder(strEntity);

    }

    private void doSynergyPostTest() {
        SynergyOrderPostInfoTest test = new SynergyOrderPostInfoTest("5443534", "1");
        present.initRetrofit2(Constant.URL_SYNERGY, false);
        present.CommitOrderTest(test);
    }

    /**
     * 发送万店通出库订单
     */
    private void doWDTOrderPost() {
        KLog.v(commit_wdt_items.get(0).getCompany_id() + "|" + commit_wdt_items.get(0).getUcode() + "|" + commit_wdt_items.get(0).getTotalprice());
        present.initRetrofit(Constant.URL_WANDIAN, false);
        present.PostWDTOrder(commit_wdt_items.get(0));
    }

    /**
     * 发送万店通出库商品
     */
    private void doWDTOrderMerPost() {
        present.initRetrofit(Constant.URL_WANDIAN, false);
        present.PostWDTOrderMer(commit_wdt_mer_items.get(0).get(0));
    }

    /**
     * 发送万店通订单状态
     */
    private void doWDTOrderStatusPost() {
        present.initRetrofit(Constant.URL_WANDIAN, false);
        KLog.v(commit_wdt_mer_items.get(0).get(0).getOcode() + "()" + commit_wdt_mer_items.get(0).get(0).getUcode());
        present.PostWDTOrderStatus(cur_user.getSecret(), "0", commit_wdt_mer_items.get(0).get(0).getOcode(), commit_wdt_mer_items.get(0).get(0).getUcode());
    }

    @Override
    public void ResolveOrder(List<SynergyOrderInfo> items_) {
        if (items_.size() > 0) {
            KLog.v(items_.get(0).toString());
        }
        if (items_.size() == 0) {
            VToast.toast(context, "没有查询到订单");
            handler.sendEmptyMessage(ORDER_FETCH_FAIL);
            return;
        }

        Iterator<SynergyOrderInfo> iter = items_.iterator();
        while (iter.hasNext()) {
            SynergyOrderInfo i = iter.next();
            i.setStatus_str(i.getJhzt() == ORDER_STATUS_VERIFY_NOT ? "未拣货" : "已拣货");

            items.add(i);
        }


        SortOrderComparator sort = new SortOrderComparator();
        Collections.sort(items, sort);
        handler.sendEmptyMessage(ORDER_FETCH_SUCCESS);
    }

    @Override
    public void ResolveOrderAdd(WandiantongItemInfo item) {

        if (item.getErrcode() == null || !item.getErrcode().equals("200")) {
            handler.sendEmptyMessage(FETCH_MER_FAIL);
            return;
        }
        Constant.cache_items = item.getContent();
        KLog.v(Constant.cache_items.get(0).toString());


        dbPresent.GetSynergyOrder();
        dbPresent.GetSynergyMer();

        handler.sendEmptyMessage(FETCH_MER_SUCCESS);
        // doSynergyPostTest();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            KLog.v(bundle.getString("result"));

            if (!util.isNetworkConnected(context)) {
                VToast.toast(context, "貌似没有网络");
                return;
            }
            Iterator<SynergyOrderInfo> v_s = items.iterator();
            while (v_s.hasNext()) {
                SynergyOrderInfo a_t = v_s.next();
                if (a_t.getDdh().equals(bundle.getString("result"))) {
                    cur_item = a_t;
                }
            }
            if (cur_item != null) {
                if (cur_item.getJhzt() != ORDER_STATUS_VERIFY_POST) {
                    gotoOrderItem(bundle.getString("result"));
                } else {
                    VToast.toast(context, "订单已经拣货");
                }
            } else {
                VToast.toast(context, "没有找到对应的单号");
            }

        } else if (resultCode == RESULT_CANCELED) {
        } else if (resultCode == ORDER_HAS_CONFIRM) {//已经修改成功
            SynergyOrderInfo backItem = data.getParcelableExtra("commit_item");
            backMerItems = data.getParcelableArrayListExtra("commit_mer_items");

            cur_item.setJhzt(ORDER_STATUS_VERIFY);
            cur_item.setStatus_str("已确认");
            adapter.notifyDataSetChanged();

            ArrayList<SynergyOrderPostInfo.SynergyOrderPostItemInfoA> temp_items = new ArrayList<>();

            /** 加入缓存*/
            dbPresent.InsertReplaceSynergyOrder(cur_item);
            for (SynergyOrderItemInfo m_i : backMerItems) {
                m_i.setDdh(cur_item.getDdh());//用于缓存关联订单--商品
                /** 加入缓存*/
                dbPresent.InsertReplaceSynergyMer(m_i);
                String id_str = m_i.getDdh() + m_i.getSku();
                int ids = id_str.hashCode();
                KLog.v("Constant.cache_detail_items" + Constant.cache_detail_items.size());
                if( Constant.cache_detail_items.size()>0) {
                    boolean isExist = false;
                    for (SynergyMerItem synergyMerItem : Constant.cache_detail_items) {//对比缓存
                        KLog.v(synergyMerItem.getId()+"更新" + ids);
                        if (synergyMerItem.getId() == ids) {
                            synergyMerItem.setWpsl(m_i.getCwpsl());
                            KLog.v("更新数量为" + synergyMerItem.getWpsl());
                        }
                        if(synergyMerItem.getId()==ids){
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        KLog.v("未包含，新添加");
                        SynergyMerItem synergyMerItem = new SynergyMerItem((long)ids,m_i.getSku(),m_i.getDdh(),m_i.getCwpsl(),m_i.getAddress(),
                                m_i.getCode(),m_i.getDw(),m_i.getWpdj(),m_i.getWpmc(),m_i.getWpsl());
                        Constant.cache_detail_items.add(synergyMerItem);
                    }
                }else{
                    SynergyMerItem synergyMerItem = new SynergyMerItem((long)ids,m_i.getSku(),m_i.getDdh(),m_i.getCwpsl(),m_i.getAddress(),
                            m_i.getCode(),m_i.getDw(),m_i.getWpdj(),m_i.getWpmc(),m_i.getWpsl());
                    Constant.cache_detail_items.add(synergyMerItem);
                }
            }

            if (!confirmed_items.contains(backItem)) {

                for (SynergyOrderItemInfo m_i : backMerItems) {
                    SynergyOrderPostInfo.SynergyOrderPostItemInfoA s_o_p_i = new SynergyOrderPostInfo.SynergyOrderPostItemInfoA(m_i.getSku(), m_i.getCwpsl());
                    temp_items.add(s_o_p_i);
                    m_i.setDdh(cur_item.getDdh());//用于缓存关联订单--商品

                }
                /**  新利源POst*/
                SynergyOrderPostInfo postItem = new SynergyOrderPostInfo(backItem.getDdh(), "0", temp_items);


                /**  万店通POst*/
                WandiantongOrderPost wdtPostItem = new WandiantongOrderPost();
                wdtPostItem.setSecret(cur_user.getSecret());
                wdtPostItem.setUcode(cur_user.getContent().getCode());
                wdtPostItem.setCompany_id(cur_user.getContent().getCompany_id());

                Float totalPrice = 0.00f;
                for (SynergyOrderItemInfo s_i : backMerItems) {
                    totalPrice += Float.parseFloat(s_i.getCwpsl()) * Float.parseFloat(s_i.getWpdj());
                }
                wdtPostItem.setTotalprice(totalPrice + "");
                KLog.v(wdtPostItem.getTotalprice());//

                /** 万店通Post商品*/
                ArrayList<WandiantongOrderMerPost> wdtPostMerItems = new ArrayList<>();
                for (SynergyOrderItemInfo s_i : backMerItems) {
                    WandiantongOrderMerPost wdtMerItem = new WandiantongOrderMerPost();
                    wdtMerItem.setSecret(cur_user.getSecret());
                    wdtMerItem.setUcode(cur_user.getContent().getCode());
                    wdtMerItem.setCompany_id(cur_user.getContent().getCompany_id());
                    // wdtMerItem.setCompany_id("1165");
                    wdtMerItem.setName(s_i.getWpmc());
                    wdtMerItem.setNumber(s_i.getCwpsl());
                    wdtMerItem.setPrice(s_i.getWpdj());
                    wdtMerItem.setUnit(s_i.getDw());
                    //   wdtMerItem.setOcode(backItem.getDdh());//订单号
//                    KLog.v(s_i.getCode() + "|"+s_i.getSku() );
                    wdtMerItem.setPcode(s_i.getCode());//商品编码

                    wdtPostMerItems.add(wdtMerItem);

                }
                commit_wdt_mer_items.add(wdtPostMerItems);
                commit_wdt_items.add(wdtPostItem);
                commit_items.add(postItem);
                confirmed_items.add(backItem);
                KLog.v("commit_wdt_mer_items:" + commit_wdt_mer_items.size() + "commit_items.size:" + commit_items.size() + "commit_wdt_items:" + commit_wdt_items.size() + "commit_mer_items-size:" + backMerItems.size()
                        + "confirmed_items:" + confirmed_items.size());
            }
        } else {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    private void gotoOrderItem(String order_no) {
        Intent intent = new Intent(MainActivity.this, OrderItemActivity.class);
        intent.putExtra("cur_item", cur_item);
        intent.putExtra("secret", cur_user.getSecret());
        intent.putExtra("ucode", cur_user.getContent().getCode());
        intent.putExtra("company_id", cur_user.getContent().getCompany_id());
        startActivityForResult(intent, 0);
    }

    @Override
    protected void confirm(int type, DialogInterface dialog) {
        super.confirm(type, dialog);
        switch (type) {
            case EXIT_CONFIRM:
                Intent service = new Intent(context, RestartService.class);
                stopService(service);
                finish();
                break;
            case NETWORD_CONFIRM:
                LoginAuto();
                break;
            case POST_RETRY_CONFIRM:
                doSynergyPost();
                break;
            case POST_CONFIRM:
                Post();
                break;
            case POST_WDT_ITEM_CONFIRM:
                doWDTOrderPost();
                break;
            case POST_WDT_ITEM_MER_CONFIRM:
                doWDTOrderMerPost();
                break;
            case POST_WDT_ITEM_STATUS_CONFIRM:
                doWDTOrderStatusPost();
                break;
        }
    }

    @Override
    protected void cancel(int type, DialogInterface dialog) {
        super.cancel(type, dialog);
        KLog.v("cancel");
        hideWaitDialog();
    }

    private void LoginAuto() {
        if (sp.getString("user_name", "").equals("")
                || sp.getString("user_pw", "").equals("")
                || sp.getString("shop_id", "").equals("")) {
            showPopupWindow();
            return;
        }
        present.Login(sp.getString("user_name", ""), sp.getString("user_pw", ""), sp.getString("shop_id", ""));
        showWaitDialog("正在登录...");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void ResolveLoginInfo(WandiantongLoginInfo info) {

        if (info.getContent() == null) {
            handler.sendEmptyMessage(LOGIN_FAIL);
            return;
        }
        KLog.v(info.toString());
        info.setSecret(util.UrlEnco(info.getSecret()));

        cur_user = info;
        handler.sendEmptyMessage(LOGIN_SUCCESS);
    }

    @Override
    public void ResolveOrderCommit(SynergyOrderPostResult item) {
        KLog.v(item.toString());
        if (item.getGxxx() != null && item.getGxxx().equals("01")) {
            handler.sendEmptyMessage(POST_ITEM_SUCESS);
            return;
        }
        handler.sendEmptyMessage(POST_ITEM_FAIL);
    }

    @Override
    public void ResolveWDTOrderPost(WandiantongOrderPostCallBack item) {
        KLog.v(item.toString());
        if (item.getErrcode() != null && item.getErrcode().equals("200")) {
            for (WandiantongOrderMerPost s : commit_wdt_mer_items.get(0)) {
                s.setOcode(item.getContent().getCode());
            }
            handler.sendEmptyMessage(POST_WDT_STOCK_ITEM_SUCCESS);
            return;
        }
        handler.sendEmptyMessage(POST_WDT_STOCK_ITEM_FAIL);
    }

    @Override
    public void ResolveWDTOrderMerPost(WandiantongOrderPostCallBack item) {
        KLog.v(item.toString());
        if (item.getErrcode() != null && item.getErrcode().equals("200")) {
            handler.sendEmptyMessage(POST_WDT_STOCK_ITEM_MER_SUCCESS);
            return;
        }
        handler.sendEmptyMessage(POST_WDT_STOCK_ITEM_MER_FAIL);
    }

    @Override
    public void ResolveWDTOrderStatusPost(WandiantongOrderPostCallBack item) {
        KLog.v(item.toString());
        if (item.getErrcode() != null && item.getErrcode().equals("200")) {
            handler.sendEmptyMessage(POST_WDT_STOCK_ITEM_STATUS_SUCCESS);
            return;
        }
        handler.sendEmptyMessage(POST_WDT_STOCK_ITEM_STATUS_FAIL);
    }

    private void showPopupWindow() {
        backgroundAlpha(0.5f);
        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_xlyordera, null),
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    private void onDissmiss() {
        backgroundAlpha(1.0f);
        if (cur_user.getSecret() == null) {
            Exit();
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


    @Override
    public void ResolveInsertDb(int type, DbResultBean result) {

    }

    @Override
    public void ResolveReadDbSynergyOrder(int type, DbResultBean result, List<SynergyOrderItem> items_) {
        if (items_.size() == 0) return;
        Iterator<SynergyOrderInfo> v_s = items.iterator();
        ArrayList<SynergyOrderItem> temp_delete_items = new ArrayList<>();
        while (v_s.hasNext()) {
            SynergyOrderInfo a_t = v_s.next();
            for (SynergyOrderItem i : items_) {
                if (a_t.getDdh().equals(i.getDdh())) {
                    if (a_t.getJhzt() == ORDER_STATUS_VERIFY_POST) {//服务器延迟提交，手动更改状态且删除缓存
                        temp_delete_items.add(i);
                    } else {
                        a_t.setJhzt(i.getJhzt());
                        a_t.setStatus_str("已确认");
                        confirmed_items.add(a_t);
                    }

                }
            }
        }

        for (SynergyOrderItem d_i : temp_delete_items) {
            SynergyOrderInfo t_i = new SynergyOrderInfo();
            t_i.setDdh(d_i.getDdh());
            dbPresent.DeleteSynergyOrder(t_i);
        }
        SortOrderStateComparator sort1 = new SortOrderStateComparator();//状态排序
        Collections.sort(items, sort1);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void ResolveReadDbSynergyMer(int type, DbResultBean result, List<SynergyMerItem> items_) {
        KLog.v("items_:" + items_.size());
        if (items_.size() == 0) return;
        ArrayList<SynergyMerItem> temp_delete_items = new ArrayList<>();
        Constant.cache_detail_items = new ArrayList<>();
        /**组装星利源*/
        for (SynergyOrderInfo synerOrder : confirmed_items) {
            ArrayList<SynergyOrderPostInfo.SynergyOrderPostItemInfoA> temp_items = new ArrayList<>();
            for (SynergyMerItem m_i : items_) {

                long id = (synerOrder.getDdh() + m_i.getSku()).hashCode();
                if (id == m_i.getId()) {
                    SynergyOrderPostInfo.SynergyOrderPostItemInfoA s_o_p_i = new SynergyOrderPostInfo.SynergyOrderPostItemInfoA(m_i.getSku(), m_i.getWpsl());
                    temp_items.add(s_o_p_i);
                    temp_delete_items.add(m_i);

                }

            }
            SynergyOrderPostInfo postItem = new SynergyOrderPostInfo(synerOrder.getDdh(), "0", temp_items);
            commit_items.add(postItem);
        }
        /**  万店通POst*/
        for (SynergyOrderInfo synerOrder : confirmed_items) {
            WandiantongOrderPost wdtPostItem = new WandiantongOrderPost();
            wdtPostItem.setSecret(cur_user.getSecret());
            wdtPostItem.setUcode(cur_user.getContent().getCode());
            wdtPostItem.setCompany_id(cur_user.getContent().getCompany_id());
            Float totalPrice = 0.00f;
            for (SynergyMerItem s_i : items_) {
                long id = (synerOrder.getDdh() + s_i.getSku()).hashCode();
                if (id == s_i.getId()) {
                    KLog.v(s_i.getWpsl()+"----"+s_i.getPrice());
                    totalPrice += Float.parseFloat(s_i.getWpsl()==null?"0":s_i.getWpsl()) * Float.parseFloat(s_i.getPrice());
                }
            }
            wdtPostItem.setTotalprice(totalPrice + "");
            commit_wdt_items.add(wdtPostItem);
        }
        /** 万店通Post商品*/
        for (SynergyOrderInfo synerOrder : confirmed_items) {
            ArrayList<WandiantongOrderMerPost> wdtPostMerItems = new ArrayList<>();
            for (SynergyMerItem s_i : items_) {
                long id = (synerOrder.getDdh() + s_i.getSku()).hashCode();
                if (id == s_i.getId()) {
                    WandiantongOrderMerPost wdtMerItem = new WandiantongOrderMerPost();
                    wdtMerItem.setSecret(cur_user.getSecret());
                    wdtMerItem.setUcode(cur_user.getContent().getCode());
                    wdtMerItem.setCompany_id(cur_user.getContent().getCompany_id());
                    wdtMerItem.setName(s_i.getName());
                    wdtMerItem.setNumber(s_i.getNumber());
                    wdtMerItem.setPrice(s_i.getPrice());
                    wdtMerItem.setUnit(s_i.getUnit());
                    wdtMerItem.setPcode(s_i.getPcode());//商品编码
                    wdtPostMerItems.add(wdtMerItem);
                }
            }
            Constant.cache_detail_items.addAll(temp_delete_items);
            items_.removeAll(temp_delete_items);

            for (SynergyMerItem s_i : items_) {//删除提交延迟造成的状态错误
                dbPresent.DeleteSynergyMer(s_i);
            }
            commit_wdt_mer_items.add(wdtPostMerItems);
        }
    }


    @Override
    public void ResolveDeleteDb(int type, DbResultBean result) {

    }
}
