package com.example.byunchangbin.business;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadNoticeActivity extends AppCompatActivity {

    private static String TAG = "phptest_UploadNoticeActivity";

    private EditText NoticeTitle,NoticeName,NoticeDate,NoticeDetail;
    private Button UploaderButton;
    private Button RegisterButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeupload);
        NoticeTitle = (EditText)findViewById(R.id.notice_title);
        NoticeName = (EditText)findViewById(R.id.notice_name);
        NoticeDate = (EditText)findViewById(R.id.notice_date);
        NoticeDetail = (EditText)findViewById(R.id.notice_detail);
        RegisterButton3= (Button)findViewById(R.id.registerButton3);


        final String code = getIntent().getStringExtra("code");

        RegisterButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = NoticeTitle.getText().toString();
                String name = NoticeName.getText().toString();
                String date = NoticeDate.getText().toString();
                String detail = NoticeDetail.getText().toString();
                String companyid = code;

                InsertData take = new InsertData();
                take.execute(title,name,date,detail,companyid);

                NoticeTitle.setText("");
                NoticeName.setText("");
                NoticeDate.setText("");
                NoticeDetail.setText("");
            }
        });

        UploaderButton = (Button)findViewById(R.id.upload_button);


        UploaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadNoticeActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
        });


    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog1;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog1 = ProgressDialog.show(UploadNoticeActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog1.dismiss();
            RegisterButton3.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String title = (String)params[0];
            String name = (String)params[1];
            String date = (String)params[2];
            String detail = (String)params[3];
            String companyid = (String)params[4];


            String serverURL = "http://sola0722.cafe24.com/UploadNotice.php";
            String postParameters = "&title=" + title + "&name=" + name + "&date=" + date + "&detail=" + detail + "&companyid=" + companyid ;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
