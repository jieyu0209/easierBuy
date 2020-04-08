package com.example.easierbuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HistoryPrice extends AppCompatActivity {
    ListView slist;
    ImageView imageView;
    String jname;
    String jpic;
    ProgressDialog progDailog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_price);




        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String idUrl = bd.getString("url").toString();

        sendRequestWithHttpURLConnection(idUrl);

    }





    private void sendRequestWithHttpURLConnection(final String input) {
        // start a thread to send http request
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    String itemurl = input;
                    URL url = new URL("http://sapi.manmanbuy.com/searchAPI.ashx?method=searchapi_pricetread2019&AppKey=U1r5jWjuCliJGBZX&url="+itemurl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in,"gb2312"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String responseData = response.toString();
                    parseJSONWithJSONObject(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }



        private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject0 = new JSONObject(jsonData);

            String jstate = jsonObject0.getString("ok");
            if(Integer.parseInt(jstate) == 1) {

                String jData = jsonObject0.getString("result");
                JSONObject jsonObject1 = new JSONObject(jData);
                String jlist = jsonObject1.getString("listPrice");
                jpic = jsonObject1.getString("spPic");
                jname = jsonObject1.getString("spName");
                JSONArray json = new JSONArray(jlist);


                final List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

                for (int i = 0; i < json.length(); i++) {
                    JSONObject jsonObject = json.getJSONObject(i);
                    String tim = jsonObject.getString("sdt");
                    String pri = jsonObject.getString("oldPrice");

                    Map<String, Object> showItem = new HashMap<String, Object>();
                    showItem.put("psdt", tim);
                    showItem.put("pold", pri);
                    listItem.add(showItem);

                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        slist = findViewById(R.id.hlist);

                        TextView textView = findViewById(R.id.hist_name);
                        textView.setText(jname);

                        imageView = findViewById(R.id.hist_img);
                        new DownImgAsyncTask().execute(jpic);

                        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listItem,
                                R.layout.history_list_item, new String[]{"psdt", "pold"},
                                new int[]{R.id.hist_date, R.id.hist_price});
                        slist.setAdapter(myAdapter);
                    }
                });


            }
            else{
                Intent intent = new Intent(HistoryPrice.this,Error.class);
                startActivity(intent);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();

        return true;
    }


    class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {  //reference: learn from https://github.com/DickyQie/android-load-picture/tree/imageloading

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            progDailog = new ProgressDialog(HistoryPrice.this); //reference: https://stackoverflow.com/questions/9170228/android-asynctask-dialog-circle
            progDailog.setMessage("Loading...");
            progDailog.setIndeterminate(false);
            progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDailog.setCancelable(true);
            progDailog.show();

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Bitmap b = Util.getImageBitmap(params[0]);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result != null) {
                imageView.setImageBitmap(result);
            }

            progDailog.dismiss();
        }

    }




}
