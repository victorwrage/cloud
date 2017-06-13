package com.baibao.cloud.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baibao.cloud.R;
import com.baibao.cloud.bean.SynergyOrderItemInfo;

import java.util.ArrayList;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/21 11:44
 */
public class OrderItemAdapter extends BaseAdapter {
    ArrayList<SynergyOrderItemInfo> items;
    Context context;
    public OrderItemAdapter(Context context_, ArrayList<SynergyOrderItemInfo> items_){
        items = items_;
        context = context_;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SynergyOrderItemInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        SynergyOrderItemInfo item = items.get(position);

        if(view ==null) {
            ViewHolder viewHolder = new ViewHolder();
            view = initView(item,viewHolder);
        }else{
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.mer_name.setText(item.getWpmc());
            viewHolder.mer_price.setText(item.getWpdj());
            viewHolder.mer_count.setText(item.getCwpsl()==null?item.getWpsl():item.getCwpsl());
            viewHolder.mer_unit.setText(item.getDw());
            viewHolder.mer_region.setText(item.getAddress());
            if(item.getStatus()){
                view.setBackgroundColor(context.getResources().getColor(R.color.green));
            }
        }

        return view;
    }

    private View initView(SynergyOrderItemInfo item,ViewHolder viewHolder){

        View view = View.inflate(context, R.layout.order_item_detail,null);
        viewHolder.mer_name = (TextView) view.findViewById(R.id.mer_name);
        viewHolder.mer_price = (TextView) view.findViewById(R.id.mer_price);
        viewHolder.mer_count = (TextView) view.findViewById(R.id.mer_count);
        viewHolder.mer_unit = (TextView) view.findViewById(R.id.mer_unit);
        viewHolder.mer_region = (TextView) view.findViewById(R.id.mer_region);

        viewHolder.mer_name.setText(item.getWpmc());
        viewHolder.mer_price.setText(item.getWpdj());
        viewHolder.mer_count.setText(item.getCwpsl()==null?item.getWpsl():item.getCwpsl());
        viewHolder.mer_unit.setText(item.getDw());
        viewHolder.mer_region.setText(item.getAddress());
        if(item.getStatus()){
            view.setBackgroundColor(context.getResources().getColor(R.color.green));
        }

        view.setTag(viewHolder);
        return view;
    }

      class ViewHolder{
         TextView mer_name,mer_price,mer_count,mer_unit,mer_region;
    }
}
