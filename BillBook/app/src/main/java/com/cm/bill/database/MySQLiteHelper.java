package com.cm.bill.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


public class MySQLiteHelper extends SQLiteOpenHelper {
    //字段：消费类型（type）、消费时间（time）、消费金额（money）、备注（note）、预算（budget）
    final String Create_Table_bills="create table table_bills(id integer PRIMARY KEY AUTOINCREMENT,type varchar(10),time varchar(20),money double,note varchar(100),budget varchar(10))";
    final String Create_Table_Budget="create table table_budget(id integer PRIMARY KEY AUTOINCREMENT,budget varchar(20))";
    private final String tag = "BB_SQL";

    @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.i("MySQLiteHelper", "创建记一笔选项的数据表");
            //执行创建数据库操作
            sqLiteDatabase.execSQL(Create_Table_bills);     //账单表
            sqLiteDatabase.execSQL(Create_Table_Budget);    //预算表

        }
        public MySQLiteHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        //当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("delete from table_bills");
            sqLiteDatabase.execSQL("update sqlite_sequence SET seq = 0 where name ='table_bills'");
            sqLiteDatabase.execSQL("delete from table_budget");
            sqLiteDatabase.execSQL("update sqlite_sequence SET seq = 0 where name ='table_budget'");
        }
        //存储数据
        public void WriteData(SQLiteDatabase sqLiteDatabase, ArrayList<String> dataList) {
            ContentValues contentValues=new ContentValues();
            contentValues.put("type",dataList.get(0));
            contentValues.put("time",dataList.get(1));
            contentValues.put("money",dataList.get(2));
            contentValues.put("note",dataList.get(3));
            Log.d(tag,"save_data 2 "+ contentValues.toString());
            sqLiteDatabase.insert("table_bills", null, contentValues);
            sqLiteDatabase.close();

}

        //保存预算
        public void saveBudget(SQLiteDatabase sqLiteDatabase,String new_budget){
            Log.d("write_name",new_budget);

            Cursor cursor = sqLiteDatabase.query("table_budget",null,null,null,null,null,null);
            Log.d(tag,"queryBudget:"+cursor.getCount());
            //先查询数据库是否有预算数据
            String db_budget="";
            while(cursor.moveToNext()){
                db_budget = cursor.getString(cursor.getColumnIndex("budget"));
            }
            cursor.close();
            //没有，直接保存
            if(db_budget.isEmpty()){
                ContentValues values = new ContentValues();
                values.put("budget",new_budget);
                sqLiteDatabase.insert("table_budget",null,values);//保存预算
            }else {
                //否则直接修改
                ContentValues values = new ContentValues();
                values.put("budget", new_budget);
                String[] budgetArr = {db_budget};
                sqLiteDatabase.update("table_budget", values, "budget=?", budgetArr);//数据库更新
            }
            sqLiteDatabase.close();
        }

}

