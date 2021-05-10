package com.example.sqldatabaseapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    RegistrationAdapter adapter_ob;
    RegistrationOpenHelper helper_ob;
    ListView nameList;
    Button registerBtn;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameList = (ListView) findViewById(R.id.lv_name);
        registerBtn = (Button) findViewById(R.id.btn_register);
        adapter_ob = new RegistrationAdapter(this);
        String[] from = {helper_ob.FNAME, helper_ob.LNAME};
        int[] to = {R.id.tv_fname, R.id.tv_lname};
        cursor = adapter_ob.queryName();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);
        nameList.setAdapter(cursorAdapter);
        nameList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                Bundle passdata = new Bundle();
                Cursor listCursor = (Cursor) arg0.getItemAtPosition(arg2);
                int nameId = listCursor.getInt(listCursor.getColumnIndex(helper_ob.KEY_ID));
                passdata.putInt("keyid", nameId);
                Intent passIntent = new Intent(MainActivity.this, EditActivity.class);
                passIntent.putExtras(passdata);
                startActivity(passIntent);
            }
        });
        registerBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent registerIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        cursor.requery();
    }
}