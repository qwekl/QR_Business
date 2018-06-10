package com.example.byunchangbin.business;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SalseListAdapter extends BaseAdapter {

    private Context context;
    private List<SalseList> salseList;

    public SalseListAdapter(Context context, List<SalseList> salseList) {
        this.context = context;
        this.salseList = salseList;
    }
    @Override
    public int getCount() {return salseList.size();
    }

    @Override
    public Object getItem(int i) {return salseList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.activity_salselist, null);
        TextView salse_menunameText = (TextView)v.findViewById(R.id.salsenameText);
        TextView salse_priceText = (TextView)v.findViewById(R.id.salsepriceText);
        TextView salse_countText = (TextView)v.findViewById(R.id.salsecountText);
        TextView salse_useridText = (TextView)v.findViewById(R.id.salseuserID);
        TextView salse_userphonenumberText = (TextView)v.findViewById(R.id.salseuserPhoneNumber);


        salse_menunameText.setText(salseList.get(i).getMenuname());
        salse_priceText.setText(salseList.get(i).getPrice());
        salse_countText.setText(salseList.get(i).getCount());
        salse_useridText.setText(salseList.get(i).getUserid());
        salse_userphonenumberText.setText(salseList.get(i).getPhonenumber());

        v.setTag(salseList.get(i).getMenuname());
        return v;
    }
}
