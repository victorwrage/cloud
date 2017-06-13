package com.baibao.cloud;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baibao.cloud.adapter.OrderItemAdapter;
import com.baibao.cloud.bean.SynergyOrderInfo;
import com.baibao.cloud.bean.SynergyOrderItemInfo;
import com.baibao.cloud.bean.WandiantongItemInfo;
import com.baibao.cloud.present.QueryPresent;
import com.baibao.cloud.utils.Constant;
import com.baibao.cloud.utils.SortComparator;
import com.baibao.cloud.utils.Utils;
import com.baibao.cloud.utils.VToast;
import com.baibao.cloud.view.IOrderItemView;
import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单详情(先请求新利源获取详情，再获取万店通的信息比对得到区域)
 */
public class OrderItemActivity extends BaseActivity implements IOrderItemView {
    private static final int ORDER_FETCH_SUCCESS = 10;//  订单获取成功
    private static final int ORDER_FETCH_FAIL = ORDER_FETCH_SUCCESS + 1;// 订单获取失败
    private static final int ORDER_FETCH_NOT = ORDER_FETCH_FAIL + 1;// 订单没记录

    private static final int FETCH_MER_SUCCESS = ORDER_FETCH_NOT + 1;// 获取万店通商品成功
    private static final int FETCH_MER_FAIL = FETCH_MER_SUCCESS + 1;// 获取万店通商品失败

    private static final int ALL_COMMIT = 10;// 提交所有订单

    private static final int ORDER_HAS_CONFIRM = 1024;
    @Bind(R.id.rl_xly_jh_back)
    RelativeLayout rl_xly_jh_back;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.tv_jh_ordernum)
    TextView tv_jh_ordernum;
    @Bind(R.id.tv_jh_money)
    TextView tv_jh_money;
    @Bind(R.id.tv_xly_jh_comdit)
    TextView tv_xly_jh_comdit;
    @Bind(R.id.tv_jh_state)
    TextView tv_jh_state;
    @Bind(R.id.tv_jh_count)
    TextView tv_jh_count;
    @Bind(R.id.lv_xyl_jh)
    ListView lv_xyl_jh;

    QueryPresent present;
    Utils util;
    OrderItemAdapter adapter;
    ArrayList<SynergyOrderItemInfo> items;
    SynergyOrderItemInfo cur_item;
    SynergyOrderInfo cur_order_item;
    String secret;
    String company_id;
    String ucode;
    boolean allCommit = true;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case ORDER_FETCH_FAIL:
                    VToast.toast(context, "此订单没有物件");
                    break;
                case ORDER_FETCH_SUCCESS:
                    adapter.notifyDataSetChanged();
                    doCompare();
                    break;
                case FETCH_MER_SUCCESS:
                    hideWaitDialog();
                    adapter.notifyDataSetChanged();
                    break;
                case FETCH_MER_FAIL:
                    hideWaitDialog();
                    // VToast.toast(context,""+msg.obj);
                    break;

            }
        }
    };

    /**
     * 给新利源Item赋值
     */
    private void doCompare() {

        for (SynergyOrderItemInfo s : items) {

            for (WandiantongItemInfo.OrderInfo i : Constant.cache_items) {//同步新利源与万店通信息
                if (i.getItem_code().equals(s.getSku())) {
                    s.setAddress(i.getAddress() != null ? i.getAddress() : "");
                    s.setWpkc(i.getStock() != null ? i.getStock() : "");
                    //  KLog.v("i.getBarcode():"+i.getItem_code());
                    s.setQr_code(i.getBarcode() != null ? i.getBarcode() : "");
                    s.setItem_code(i.getItem_code() != null ? i.getItem_code() : "");
                    s.setCode(i.getCode() != null ? i.getCode() : "");
                }
            }
            for(SynergyMerItem synergyMerItem:Constant.cache_detail_items){//对比缓存
                String id_str  = s.getDdh()+s.getSku();
                int ids = id_str.hashCode();
                KLog.v("更新"+ids);
                if(synergyMerItem.getId()==ids){
                    // s.setWpsl(synergyMerItem.getWpsl());
                    s.setCwpsl(synergyMerItem.getWpsl());
                    s.setStatus(true);
                    KLog.v("更新数量为"+s.getCwpsl());
                }
            }
        }
        updateCount();
        Sort();
        handler.sendEmptyMessage(FETCH_MER_SUCCESS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlyjian_huo);
        ButterKnife.bind(OrderItemActivity.this);

        initDate(getIntent());
        initView();

    }

    private void initDate(Intent intent) {
        Constant.activity = 2;
        cur_order_item = intent.getParcelableExtra("cur_item");
        secret = intent.getStringExtra("secret");
        ucode = intent.getStringExtra("ucode");
        company_id = intent.getStringExtra("company_id");

        present = QueryPresent.getInstance(context);
        present.initRetrofit(Constant.URL_SYNERGY, false);
        present.setView(OrderItemActivity.this);
        util = Utils.getInstance();
        items = new ArrayList<>();
        adapter = new OrderItemAdapter(context, items);
        lv_xyl_jh.setAdapter(adapter);

    }


    private void onItemClick(int position) {
        cur_item = items.get(position);
        showEditDialog(cur_item.getWpsl());

    }

    private void initView() {
        RxView.clicks(rl_xly_jh_back).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> finish());
        RxView.clicks(tv_xly_jh_comdit).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> confirmOrder());
        RxView.clicks(iv_scan).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Scan());

        lv_xyl_jh.setOnItemClickListener((parent, view, position, id) -> onItemClick(position));
        lv_xyl_jh.setEmptyView(findViewById(R.id.empty_lay));

        tv_jh_ordernum.setText(cur_order_item.getDdh());
        tv_jh_money.setText(cur_order_item.getDdje());
        tv_jh_state.setText(cur_order_item.getStatus_str());
        present.QueryOderSingle(cur_order_item.getDdh());

        Drawable drawable = getResources().getDrawable(R.drawable.confirm);
        drawable.setBounds(0, 2, 30, 30);
        tv_xly_jh_comdit.setCompoundDrawables(null, drawable, null, null);
        // tv_xly_jh_comdit.setCompoundDrawablePadding(25);

        showWaitDialog("正在查询订单详情...");
    }

    private void confirmOrder() {
        showDialog(ALL_COMMIT, "提示", "是否提交?", "确认", "取消");
    }

    /**
     * 提交修改
     */
    private void Commit() {

        Intent backIntent = new Intent();
        backIntent.putExtra("commit_item", cur_order_item);
        backIntent.putParcelableArrayListExtra("commit_mer_items", items);
        setResult(ORDER_HAS_CONFIRM, backIntent);
        finish();
    }

    private void Scan() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void ResolveOrderItem(List<SynergyOrderItemInfo> items_) {
        if (items_.size() > 0) {
            KLog.v(items_.get(0).toString());
        } else {
            handler.sendEmptyMessage(ORDER_FETCH_FAIL);
        }
        Iterator<SynergyOrderItemInfo> iter = items_.iterator();
        while (iter.hasNext()) {
            SynergyOrderItemInfo next = iter.next();
            next.setWpsl(Math.round(Float.parseFloat(next.getWpsl())) + "");
            next.setCwpsl(next.getWpsl());
            next.setDdh(cur_order_item.getDdh());
            items.add(next);
        }
        updateCount();
        handler.sendEmptyMessage(ORDER_FETCH_SUCCESS);
    }

    /**
     * 更新物品总数
     */
    private void updateCount() {
        Iterator<SynergyOrderItemInfo> iter = items.iterator();
        int count = 0;
        while (iter.hasNext()) {
            SynergyOrderItemInfo next = iter.next();
            count += Integer.parseInt(next.getWpsl());
        }
        tv_jh_count.setText(count + "件");
    }

    private void Sort() {
        SortComparator sort = new SortComparator();
        Collections.sort(items, sort);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

            String res = bundle.getString("result");
            editItem(res);//

        } else if (resultCode == RESULT_CANCELED) {
        } else {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    /**
     * 扫描后的edit
     *
     * @param sku
     */
    private void editItem(String sku) {
        if (items.size() < 1) {
            return;
        }
        boolean isExist = false;
        for (SynergyOrderItemInfo i : items) {
            KLog.v(i.getQr_code());
            if (i.getQr_code() != null && i.getQr_code().equals(sku)) {//直接过，不弹窗
                cur_item = i;
                cur_item.setStatus(true);
                allCommit = false;
                isExist = true;
                break;
            }
        }
        adapter.notifyDataSetChanged();
        if (!isExist) {
            VToast.toast(context, "没有找到相应物品");
        }
    }

    /**
     * 确定修改数量
     *
     * @param type
     * @param dialog
     */
    @Override
    protected void edit(EditText inputServer, int type, DialogInterface dialog) {
        super.edit(inputServer, type, dialog);
        if (inputServer.getText().toString().trim().equals("")) {
            return;
        }
        if (Integer.parseInt(inputServer.getText().toString()) > Integer.parseInt(cur_item.getWpsl())) {
            VToast.toast(context, "不能大于已有数量");
            return;
        }

        if (inputServer.getText().toString().equals("")) {
            VToast.toast(context, "没有修改");
            return;
        }
        cur_item.setStatus(true);
        cur_item.setCwpsl(inputServer.getText().toString());
        allCommit = false;
    }

    @Override
    protected void confirm(int type, DialogInterface dialog) {
        super.confirm(type, dialog);
        switch (type) {
            case ALL_COMMIT:
                Commit();
                break;
        }
    }
}
