package com.cm.bill.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cm.bill.R;
import com.cm.bill.database.MySQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 账单(activity_bills)对应的activity
 */
public class BillsActivity extends AppCompatActivity implements View.OnClickListener {

    // 适配器
    private SimpleAdapter simpleAdapter;
    //数据库
    private MySQLiteHelper mMysql;
    private SQLiteDatabase mDataBase;

    private final String tag = "BB_BillsActivity";
    // 存储数据的数组列表
    ArrayList<HashMap<String, Object>> listData = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        //列举数据的ListView
        ListView listView = (ListView) this.findViewById(R.id.list_view_bills);
        //点后退图片finish此activity
        ImageView image_view_back = (ImageView) this.findViewById(R.id.image_view_back);
        image_view_back.setOnClickListener(this);

        //查询数据
        getData();

        simpleAdapter = new SimpleAdapter(this, listData, R.layout.layout_listview, new String[]{"time", "type", "money", "note"},
                new int[]{R.id.show_time, R.id.show_type, R.id.show_money, R.id.show_note}
        );
        //填充数据
        listView.setAdapter(simpleAdapter);

        //长按响应
        listView.setOnCreateContextMenuListener(listviewLongPress);

//        listView_bill.setOnTouchListener(onTouchListener);

    }

//    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
//        float x, y, ux, uy;
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            // TODO Auto-generated method stub
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    x = event.getX();
//                    y = event.getY();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    ux = event.getX();
//                    uy = event.getY();
//                    int p2 = ((ListView)v).pointToPosition((int) ux, (int) uy);
//
//                    return false;
//            }
//
//            return false;
//        }
//
//    };

//    AdapterView.OnItemLongClickListener listviewLongClick = new AdapterView.OnItemLongClickListener() {
//        @Override
//        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//            return false;
//        }
//    };
//
//    AdapterView.OnItemClickListener listviewClick = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            Toast.makeText(getApplicationContext(), "weffwe", Toast.LENGTH_SHORT).show();
//        }
//    };

    //从数据库获得适配器数据
    public void getData() {
        mMysql = new MySQLiteHelper(this, "table_bills", null, 1);
        mDataBase = mMysql.getReadableDatabase();

        Cursor cursor = mDataBase.rawQuery("select * from table_bills order by id DESC ", null);
        cursor.moveToFirst();
        int columnsSize = cursor.getColumnCount();
        int number = 0;

        while (number < cursor.getCount()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", cursor.getString(cursor.getColumnIndex("id")));
            map.put("type", cursor.getString(cursor.getColumnIndex("type")));
            map.put("time", cursor.getString(cursor.getColumnIndex("time")));
            map.put("money", cursor.getString(cursor.getColumnIndex("money")));
            map.put("note", cursor.getString(cursor.getColumnIndex("note")));

            cursor.moveToNext();
            listData.add(map);
            number++;
            System.out.println(tag+",getData,listData: " + listData);
        }
        System.out.println(tag+"，getData end");
        cursor.close();
        mDataBase.close();
        mMysql.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_back:
                this.finish();
                break;
            default:
                break;
        }

    }

    // 长按事件响应
    View.OnCreateContextMenuListener listviewLongPress = new  View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // TODO

            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            new AlertDialog.Builder(BillsActivity.this)
                    .setTitle("删除当前数据")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("确定删除当前记录")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    // 获取所按位置索引
                                    int map_position = info.position;
                                    // 获取对应HashMap中的数据内容
                                    HashMap<String, Object> map = listData.get(map_position);
                                    // 获取id
                                    int id = Integer.valueOf((map.get("id").toString()));
                                    // 获取数组具体值后,可以对数据进行相关的操作,例如更新数据
                                    String[] arr = new String[]{String.valueOf(id)};
                                    //获取当前数据库
                                    mMysql = new MySQLiteHelper(BillsActivity.this, "table_bills", null, 1);
                                    mDataBase = mMysql.getReadableDatabase();
                                    try {
                                        mDataBase.delete("table_bills", "id=?", arr);
                                        listData.remove(map_position);
                                        //适配器更新数据
                                        simpleAdapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        Log.e(tag,"删除出错了");
                                    } finally {
                                        mDataBase.close();
                                        mMysql.close();
                                    }
                                }
                            }
                    ).setNegativeButton("取消", null)
                    .show();

        }
    };
}
