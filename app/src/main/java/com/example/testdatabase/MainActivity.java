package com.example.testdatabase;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private MyDatabaseHelper helper;
    private SQLiteDatabase db;
    private ContentValues values;

    private Button mBtnCreate;
    private Button mBtnAdd;
    private Button mBtnUpdate;
    private Button mBtnDelete;
    private Button mBtnQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new MyDatabaseHelper(this,"BookStore.db",null,2);
        values = new ContentValues();

        mBtnCreate = findViewById(R.id.create);
        mBtnCreate.setOnClickListener(this);

        mBtnAdd = findViewById(R.id.add);
        mBtnAdd.setOnClickListener(this);

        mBtnUpdate = findViewById(R.id.update);
        mBtnUpdate.setOnClickListener(this);

        mBtnDelete = findViewById(R.id.delete);
        mBtnDelete.setOnClickListener(this);

        mBtnQuery = findViewById(R.id.query);
        mBtnQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.create:
                helper.getWritableDatabase();
                break;
            case R.id.add:
                db = helper.getWritableDatabase();

                values.clear();
                values.put("name","《第一行代码》");
                values.put("price",60.3);
                db.insert("Book",null,values);

                values.clear();
                values.put("name","《SQL必知必会》");
                values.put("price",16.99);
                db.insert("Book",null,values);

                Toast.makeText(this,"Add Succeeded!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.update:
                db = helper.getWritableDatabase();
                values.clear();
                values.put("price",42.5);
                db.update("Book",values,"id = ?",new String[]{"2"});

                Toast.makeText(this,"Update Succeeded!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                db = helper.getWritableDatabase();
                db.delete("Book","id = ?",new String[]{"2"});

                Toast.makeText(this,"Delete Succeeded!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.query:
                db = helper.getWritableDatabase();

                //查询 Book 表中所有的数据
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        //遍历 cursor 对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d(TAG, "************");
                        Log.d(TAG, "book name is "+name);
                        Log.d(TAG, "book price is "+price);
                    }while(cursor.moveToNext());
                    cursor.close();
                }
                break;
        }
    }
}