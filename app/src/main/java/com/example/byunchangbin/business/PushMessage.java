package com.example.byunchangbin.business;

/**
 * Created by byunchangbin on 2018-06-10.
 */

import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.EditText;


public class PushMessage extends AppCompatActivity {
    private EditText etNumber, etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        etNumber = (EditText) findViewById(R.id.et_number);
        etMessage = (EditText) findViewById(R.id.et_message);
    }

    //버튼 클릭 리스너 등록 : SMS전송
    public void onClickSMS(View v) {
        Uri n = Uri.parse("smsto: " + etNumber.getText());
        Intent intent = new Intent(Intent.ACTION_SENDTO, n);
        String t = etMessage.getText().toString();
        intent.putExtra("sms_body", t);
        startActivity(intent);
    }
}