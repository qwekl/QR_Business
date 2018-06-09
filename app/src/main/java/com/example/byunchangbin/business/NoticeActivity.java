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
 * Created by byunchangbin on 2018-06-05.
 */


public class NoticeActivity extends AppCompatActivity {
    private Button NoticeUploadButton;
    private NoticeListAdapter adapter;
    private List<NoticeList> noticeList;
    private ListView noticeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        noticeListView = (ListView)findViewById(R.id.listView);
        noticeList = new ArrayList<NoticeList>();
        adapter = new NoticeListAdapter(getApplicationContext(),noticeList);
        noticeListView.setAdapter(adapter);
        NoticeUploadButton = (Button)findViewById(R.id.notice_upload2);

        final String code = getIntent().getStringExtra("code");

        //공지사항 클레스 접근
        new BackgroundTask().execute();

        noticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoticeActivity.this, NoticePopupActivity.class);
                intent.putExtra("title",noticeList.get(position).getTitle());
                intent.putExtra("code",code);
                startActivityForResult(intent,1);
            }
        });



        NoticeUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeActivity.this, UploadNoticeActivity.class);
                startActivity(intent);
            }
        });

    }

//서버에서 메뉴 불러오기
class BackgroundTask extends AsyncTask<Void, Void, String> {

    String target;
    String code = getIntent().getStringExtra("code");

    @Override
    protected void onPreExecute(){
        target = "http://sola0722.cafe24.com/CompanyNotice.php?code="+code;}

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
            int count = 0;
            String title,name,date;
            while (count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                title = object.getString("title");
                name = object.getString("name");
                date = object.getString("datecreated");
                NoticeList notice = new NoticeList(title,name,date);
                noticeList.add(notice);
                adapter.notifyDataSetChanged();
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

}