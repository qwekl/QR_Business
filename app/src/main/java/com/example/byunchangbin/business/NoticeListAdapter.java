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

public class NoticeListAdapter extends BaseAdapter {
    private Context context;
    private List<NoticeList> noticeList;

    public NoticeListAdapter(Context context, List<NoticeList> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }
    @Override
    public int getCount() {return noticeList.size();
    }

    @Override
    public Object getItem(int i) {return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.activity_noticelist, null);
        TextView noticetitleText = (TextView)v.findViewById(R.id.noticetitleText);
        TextView noticenameText = (TextView)v.findViewById(R.id.noticenameText);
        TextView noticedateText = (TextView)v.findViewById(R.id.noticedateText);


        noticetitleText.setText(noticeList.get(i).getTitle());
        noticenameText.setText(noticeList.get(i).getName());
        noticedateText.setText(noticeList.get(i).getDate());

        v.setTag(noticeList.get(i).getTitle());
        return v;
    }
}
