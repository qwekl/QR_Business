package com.example.byunchangbin.business;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadMenuActivity extends AppCompatActivity {

    private static String TAG = "phptest_UploadMenuActivity";

    private EditText MenuName,MenuPrice,MenuDescription,MenuFilename;
    private Button UploaderButton;
    private Button RegisterButton2;
    private Toast toast;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuadd);
        MenuName = (EditText)findViewById(R.id.menu_name);
        MenuPrice = (EditText)findViewById(R.id.menu_price);
        MenuDescription = (EditText)findViewById(R.id.menu_description);
        MenuFilename = (EditText)findViewById(R.id.menu_filename);
        RegisterButton2= (Button)findViewById(R.id.registerButton2);
        UploaderButton = (Button)findViewById(R.id.upload_button);

        final String code = getIntent().getStringExtra("code");





        UploaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadMenuActivity.this,UploadImageActivity.class);
                startActivity(intent);
            }
        });

        RegisterButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menuname = MenuName.getText().toString();
                String menuprice = MenuPrice.getText().toString();
                String menudescription = MenuDescription.getText().toString();
                String menufilename = MenuFilename.getText().toString();
                String menucompanyid = code;


                InsertData take = new InsertData();
                take.execute(menuname,menuprice,menudescription,menucompanyid,menufilename);

                MenuName.setText("");
                MenuPrice.setText("");
                MenuDescription.setText("");
                MenuFilename.setText("");
            }
        });




    }
     class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog1;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog1 = ProgressDialog.show(UploadMenuActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog1.dismiss();
            RegisterButton2.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String menuname = (String)params[0];
            String menuprice = (String)params[1];
            String menudescription = (String)params[2];
            String menucompanyid = (String)params[3];
            String menufilename = (String)params[4];

            String serverURL = "http://sola0722.cafe24.com/UploadMenu.php";
            String postParameters = "&menuname=" + menuname + "&price=" + menuprice + "&description=" + menudescription + "&companyid=" + menucompanyid + "&filename=" + menufilename ;


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
