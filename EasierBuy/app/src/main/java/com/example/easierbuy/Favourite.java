package com.example.easierbuy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easierbuy.R;

import java.util.LinkedList;

public class Favourite extends AppCompatActivity {
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite);

        final ListView list_test = (ListView) findViewById(R.id.flist);

        final LinkedList<Block> aData = new LinkedList<Block>();


        //select data from db to display
        dbHelper = new DatabaseHelper(this, "blocks.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor=db.query("blocks", null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                //iterate the cursor, obtain the data
                String url =cursor.getString(cursor.getColumnIndex("block_url"));
                String name =cursor.getString(cursor.getColumnIndex("block_name"));
                String price =cursor.getString(cursor.getColumnIndex("block_price"));
                String brand =cursor.getString(cursor.getColumnIndex("block_brand"));
                String site =cursor.getString(cursor.getColumnIndex("block_site"));

                aData.add(new Block(url,name,price,brand,site));

                list_test.setAdapter(new MyAdapter(aData, Favourite.this));


            }while(cursor.moveToNext());
        }
        cursor.close();


        list_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(MainActivity.this, "BOOM",Toast.LENGTH_SHORT).show();

                TextView key = (TextView) view.findViewById(R.id.fitemurl);
                String temp = key.getText().toString();

                Intent inside = new Intent(Favourite.this,HistoryPrice.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", temp);
                inside.putExtras(bundle);
                startActivity(inside);
            }
        });



        list_test.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //Toast.makeText(MainActivity.this, "You long clicked it!",Toast.LENGTH_SHORT).show();

                TextView key = (TextView) view.findViewById(R.id.fitemurl);
                String tempKey = key.getText().toString();

                final String whereClause = "block_url=?";

                final String[] whereArgs = new String[] { tempKey };




                AlertDialog.Builder builder = new AlertDialog.Builder(Favourite.this);
                builder.setTitle("ARE YOU SURE?");
                builder.setMessage("THIS ACTION CAN BE DANGEROUS");
                builder.setPositiveButton("PRETTY SURE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Favourite.this, "FINE",Toast.LENGTH_SHORT).show();

                        db.delete("blocks", whereClause,whereArgs);
                        aData.remove(i);
                        list_test.setAdapter(new MyAdapter(aData, Favourite.this));
                    }
                });
                builder.setNeutralButton("I AM NOT SURE NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Favourite.this,"THINK ABOUT IT~",Toast.LENGTH_SHORT).show();
                    }
                });


                builder.show();

                return true;
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        setContentView(R.layout.favorite);

        final ListView list_test = (ListView) findViewById(R.id.flist);

        final LinkedList<Block> aData = new LinkedList<Block>();


        //select data from db to display
        dbHelper = new DatabaseHelper(this, "blocks.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor=db.query("blocks", null,null,null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                //iterate the cursor, obtain the data
                String url =cursor.getString(cursor.getColumnIndex("block_url"));
                String name =cursor.getString(cursor.getColumnIndex("block_name"));
                String price =cursor.getString(cursor.getColumnIndex("block_price"));
                String brand =cursor.getString(cursor.getColumnIndex("block_brand"));
                String site =cursor.getString(cursor.getColumnIndex("block_site"));

                aData.add(new Block(url,name,price,brand,site));

                list_test.setAdapter(new MyAdapter(aData, Favourite.this));


            }while(cursor.moveToNext());
        }
        cursor.close();


        list_test.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //Toast.makeText(MainActivity.this, "BOOM",Toast.LENGTH_SHORT).show();

                TextView key = (TextView) view.findViewById(R.id.fitemurl);
                String temp = key.getText().toString();

                Intent inside = new Intent(Favourite.this,HistoryPrice.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", temp);
                inside.putExtras(bundle);
                startActivity(inside);
            }
        });



        list_test.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //Toast.makeText(MainActivity.this, "You long clicked it!",Toast.LENGTH_SHORT).show();

                TextView key = (TextView) view.findViewById(R.id.fitemurl);
                String tempKey = key.getText().toString();

                final String whereClause = "block_url=?";

                final String[] whereArgs = new String[] { tempKey };




                AlertDialog.Builder builder = new AlertDialog.Builder(Favourite.this);
                builder.setTitle("ARE YOU SURE?");
                builder.setMessage("THIS ACTION CAN BE DANGEROUS");
                builder.setPositiveButton("PRETTY SURE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Favourite.this, "FINE",Toast.LENGTH_SHORT).show();

                        db.delete("blocks", whereClause,whereArgs);
                        aData.remove(i);
                        list_test.setAdapter(new MyAdapter(aData, Favourite.this));
                    }
                });
                builder.setNeutralButton("I AM NOT SURE NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Favourite.this,"THINK ABOUT IT~",Toast.LENGTH_SHORT).show();
                    }
                });


                builder.show();

                return true;
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
