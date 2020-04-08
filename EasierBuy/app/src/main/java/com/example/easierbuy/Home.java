package com.example.easierbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);

        Button btnSearch = findViewById(R.id.btn_search);
        Button btnFavor = findViewById(R.id.btn_favor);
        Button btnHistory = findViewById(R.id.btn_history);
        Button btnTest = findViewById(R.id.testtt);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jumpS = new Intent(Home.this, Search.class);
                startActivity(jumpS);
            }
        });

        btnFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jumpF = new Intent(Home.this, Favourite.class);
                startActivity(jumpF);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jumpH = new Intent(Home.this, History.class);
                startActivity(jumpH);
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jumpH = new Intent(Home.this, Detail.class);
                startActivity(jumpH);
            }
        });

    }
}
