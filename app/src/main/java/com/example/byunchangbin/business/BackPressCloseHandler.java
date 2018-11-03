package com.example.byunchangbin.business;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by byunchangbin on 2018-06-07.
 */

public class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context)
    {
        this.activity = context;
    }
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return; }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }
    public void showGuide() {
        toast = Toast.makeText(activity, "\'後ろ\'ボタンをもう一度押されたら終了します", Toast.LENGTH_SHORT);
        toast.show();
    }
}

