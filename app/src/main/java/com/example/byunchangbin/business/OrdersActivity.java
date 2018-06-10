package com.example.byunchangbin.business;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byunchangbin on 2018-06-06.
 */

public class OrdersActivity extends AppCompatActivity {
    private Button PushButton;
    private OrdersListAdapter adapter;
    private List<OrdersList> ordersList;
    private ListView ordersListView;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTitle("주문현황");
        setContentView(R.layout.activity_orders);

        ordersListView = (ListView)findViewById(R.id.listView);
        ordersList = new ArrayList<OrdersList>();
        adapter = new OrdersListAdapter(getApplicationContext(),ordersList);
        ordersListView.setAdapter(adapter);
        PushButton = (Button)findViewById(R.id.push_message);


        //주문 목록 클레스 접근
        new OrdersActivity.BackgroundTask().execute();

       ordersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrdersActivity.this, OrdersPopUpActivity.class);
                intent.putExtra("Userid",ordersList.get(position).getUserid());
                intent.putExtra("phonenember",ordersList.get(position).getPhonenumber());
                intent.putExtra("code",code);
                startActivityForResult(intent,1);
            }
        });
    }

    //서버에서 메뉴 불러오기
    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;
        String code = getIntent().getStringExtra("code");

        @Override
        protected void onPreExecute(){
            target = "http://sola0722.cafe24.com/Orders.php?code="+code;}

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
                String menuname,price, count,userid,phonenumber;
                while (counter < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(counter);
                    menuname = object.getString("menuname");
                    price = object.getString("price");
                    count = object.getString("count");
                    userid= object.getString("userid");
                    phonenumber= object.getString("phonenumber");
                    OrdersList orders = new OrdersList(menuname,price,count,userid,phonenumber);
                    ordersList.add(orders);
                    adapter.notifyDataSetChanged();
                    counter++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

