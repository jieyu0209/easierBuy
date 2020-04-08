package com.example.easierbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        Button sendRequestBtn = (Button) findViewById(R.id.send_history);
        final EditText wordHistory = findViewById(R.id.word_history);
        String idHist = wordHistory.getText().toString().trim();

        sendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent hprice = new Intent(History.this,HistoryPrice.class);
                Bundle bd = new Bundle();
                bd.putString("url", wordHistory.getText().toString());
                hprice.putExtras(bd);

                startActivity(hprice);
                finish();
            }
        });



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
