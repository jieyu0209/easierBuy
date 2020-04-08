package com.example.easierbuy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class Search extends AppCompatActivity {
    ListView slist;
    String jpic;

    private DatabaseHelper dbHelper = new DatabaseHelper(this, "blocks.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        final ListView slist =  findViewById(R.id.slist);

        Button search = findViewById(R.id.reuqest_s);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText wordInput = findViewById(R.id.search_input);
                final String input = wordInput.getText().toString().trim();
                sendRequestWithHttpURLConnection(input);

            }
        });


    }

    private void sendRequestWithHttpURLConnection(final String in) { // reference: https://developer.android.com/reference/java/net/HttpURLConnection
        // start a thread to send http request
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    final String input = URLEncoder.encode(in, "gb2312");
                    URL url = new URL("http://sapi.manmanbuy.com/Search.aspx?AppKey=U1r5jWjuCliJGBZX&Key="
                            +input+"&Class=0&Brand=0&Site=0&PriceMin=0&PriceMax=0&PageNum=1&PageSize=30&OrderBy=score&ZiYing=false&ExtraParameter=1");
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
            String jstate = jsonObject0.getString("State");

            if(Integer.parseInt(jstate) == 1000) {
                String jcount = jsonObject0.getString("SearchCount");
                if(Integer.parseInt(jcount) != 0) {


                    JSONArray json = jsonObject0.getJSONArray("SearchResultList");

                    final List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject jsonObject = json.getJSONObject(i);
                        String tim = jsonObject.getString("spname");
                        String pri = jsonObject.getString("spprice");
                        String brand = jsonObject.getString("brandName");
                        String site = jsonObject.getString("siteName");
                        String itemurl = jsonObject.getString("spurl");
                        jpic = jsonObject.getString("sppic");


                        Map<String, Object> showItem = new HashMap<String, Object>();
                        showItem.put("pname", tim);
                        showItem.put("pprice", pri);
                        showItem.put("pbrand", brand);
                        showItem.put("psite", site);
                        showItem.put("itemurl", itemurl);

                        listItem.add(showItem);

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            slist = findViewById(R.id.slist);

                            SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listItem,
                                    R.layout.search_list_item, new String[]{"pname", "pprice", "pbrand", "psite", "itemurl"},
                                    new int[]{R.id.text_spname, R.id.text_spprice, R.id.text_spbrand, R.id.text_spsite, R.id.itemurl});
                            slist.setAdapter(myAdapter);

                            //get history prices
                            slist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    //Toast.makeText(MainActivity.this, "BOOM",Toast.LENGTH_SHORT).show();

                                    TextView key = (TextView) view.findViewById(R.id.itemurl);
                                    String temp = key.getText().toString();

                                    Intent inside = new Intent(Search.this, HistoryPrice.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url", temp);
                                    inside.putExtras(bundle);

                                    startActivity(inside);
                                }
                            });

                            slist.setAdapter(myAdapter);

                            //add to "favourite" database
                            slist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    TextView keyUrl = (TextView) view.findViewById(R.id.itemurl);
                                    String tempUrl = keyUrl.getText().toString();
                                    TextView keyName = (TextView) view.findViewById(R.id.text_spname);
                                    String tempName = keyName.getText().toString();
                                    TextView keyPrice = (TextView) view.findViewById(R.id.text_spprice);
                                    String tempPrice = keyPrice.getText().toString();
                                    TextView keyBrand = (TextView) view.findViewById(R.id.text_spbrand);
                                    String tempBrand = keyBrand.getText().toString();
                                    TextView keySite = (TextView) view.findViewById(R.id.text_spsite);
                                    String tempSite = keySite.getText().toString();

                                    SQLiteDatabase db = dbHelper.getWritableDatabase(); //获取db对象
                                    ContentValues values = new ContentValues();
                                    //start inserting data
                                    values.put("block_url", tempUrl);
                                    values.put("block_name", tempName);
                                    values.put("block_price", tempPrice);
                                    values.put("block_brand", tempBrand);
                                    values.put("block_site", tempSite);
                                    db.insert("blocks", null, values);

                                    values.clear();//new


                                    Toast.makeText(Search.this, "You made it!", Toast.LENGTH_SHORT).show();

                                    return true;
                                }
                            });


                            slist.setAdapter(myAdapter);
                        }
                    });

                }
                else {
                    Intent intent = new Intent(Search.this,Error.class);
                    startActivity(intent);
                }

            }
            else {
                    Intent intent = new Intent(Search.this,Error.class);
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





}
