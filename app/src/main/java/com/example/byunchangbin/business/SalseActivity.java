package com.example.byunchangbin.business;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SalseActivity extends AppCompatActivity {

    private SalseListAdapter adapter;
    private List<SalseList> SalseList;
    private ListView SalseListView;
    private TextView textSalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("매출현황");
        setContentView(R.layout.activity_salse);

        SalseListView = (ListView)findViewById(R.id.listView);
        SalseList = new ArrayList<SalseList>();
        adapter = new SalseListAdapter(getApplicationContext(),SalseList);
        SalseListView.setAdapter(adapter);
        textSalse = (TextView)findViewById(R.id.textSalse);

        //주문 목록 클레스 접근
        new BackgroundTask().execute();

        new SalseSum().execute();

    }

    //주문 완료 목록
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;
        String code = getIntent().getStringExtra("code");

        @Override
        protected void onPreExecute(){
            target = "http://sola0722.cafe24.com/Salse.php?code="+code;}

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void...values){
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int counter = 0;
                String id,menuname,price, count,userid,phonenumber;
                while (counter < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(counter);
                    id = object.getString("id");
                    menuname = object.getString("menuname");
                    price = object.getString("price");
                    count = object.getString("count");
                    userid= object.getString("userid");
                    phonenumber= object.getString("phonenumber");
                    SalseList orders = new SalseList(id,menuname,price,count,userid,phonenumber);
                    SalseList.add(orders);
                    adapter.notifyDataSetChanged();
                    counter++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //총 매출
    class SalseSum extends AsyncTask<Void, Void, String> {

        String target;
        String code = getIntent().getStringExtra("code");

        @Override
        protected void onPreExecute(){
            target = "http://sola0722.cafe24.com/SalseSum.php?code="+code;}

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void...values){
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result){
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int counter = 0;
                String price = null;
                while (counter < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(counter);
                    price = object.getString("price");
                    counter++;
                }
                textSalse.setText(price + "원");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
