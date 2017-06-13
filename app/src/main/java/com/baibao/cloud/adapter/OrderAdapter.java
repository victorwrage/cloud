package com.baibao.cloud.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baibao.cloud.R;

import com.baibao.cloud.bean.SynergyOrderInfo;

import java.util.ArrayList;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/21 11:44
 */

public class OrderAdapter extends BaseAdapter {
    ArrayList<SynergyOrderInfo> items;
    Context context;
    public OrderAdapter(Context context_, ArrayList<SynergyOrderInfo> items_){
        items = items_;
        context = context_;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SynergyOrderInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        SynergyOrderInfo item = items.get(position);

        if(view ==null) {
            ViewHolder viewHolder = new ViewHolder();
            view = initView(item,viewHolder);

        }else{
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.order_no.setText(item.getDdh());
            viewHolder.order_region.setText(item.getQy());
            viewHolder.order_cash.setText(item.getDdje());
            viewHolder.order_status.setText(item.getStatus_str());
            switch(item.getJhzt()){
                case 0:
                    viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.gray));
                    break;
                case 1:
                    viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.red));
                    break;
                case 2:
                    viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.green));
                    break;
            }
        }
        return view;
    }

    private View initView(SynergyOrderInfo item,ViewHolder viewHolder){
        View view = View.inflate(context, R.layout.order_item,null);
        viewHolder.order_no = (TextView) view.findViewById(R.id.order_no);
        viewHolder.order_region = (TextView) view.findViewById(R.id.order_region);
        viewHolder.order_cash = (TextView) view.findViewById(R.id.order_cash);
        viewHolder.order_status = (TextView) view.findViewById(R.id.order_status);
        viewHolder.order_no.setText(item.getDdh());
        viewHolder.order_region.setText(item.getQy());
        viewHolder.order_cash.setText(item.getDdje());
        viewHolder.order_status.setText(item.getStatus_str());
        switch(item.getJhzt()){
            case 0:
                viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.gray));
                break;
            case 1:
                viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case 2:
                viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.green));
                break;
        }
        view.setTag(viewHolder);
        return view;
    }

      class ViewHolder{
         TextView order_no,order_region,order_cash,order_status;
    }
}
