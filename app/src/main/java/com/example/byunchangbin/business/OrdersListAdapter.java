package com.example.byunchangbin.business;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by byunchangbin on 2018-06-05.
 */

public class OrdersListAdapter extends BaseAdapter {
    private Context context;
    private List<OrdersList> ordersList;

    public OrdersListAdapter(Context context, List<OrdersList> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }
    @Override
    public int getCount() {return ordersList.size();
    }

    @Override
    public Object getItem(int i) {return ordersList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.activity_orderslist, null);
        TextView order_menunameText = (TextView)v.findViewById(R.id.ordernameText);
        TextView orders_priceText = (TextView)v.findViewById(R.id.orderpriceText);
        TextView orders_countText = (TextView)v.findViewById(R.id.ordercountText);
        TextView orders_useridText = (TextView)v.findViewById(R.id.orderuserID);


        order_menunameText.setText(ordersList.get(i).getMenuname());
        orders_priceText.setText(ordersList.get(i).getPrice());
        orders_countText.setText(ordersList.get(i).getCount());
        orders_useridText.setText(ordersList.get(i).getUserid());

        v.setTag(ordersList.get(i).getMenuname());
        return v;
    }
}
