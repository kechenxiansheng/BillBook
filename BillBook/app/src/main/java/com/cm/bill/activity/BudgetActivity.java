package com.cm.bill.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cm.bill.R;
import com.cm.bill.database.MySQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 预算(activity_budget)对应的activity
 */
public class BudgetActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText_budget;
    //数据库
    private MySQLiteHelper mySQLiteHelper;
    private final String tag ="BB_BudgetActivity";
    // 存储数据的数组列表
    ArrayList<HashMap<String, Object>> listData = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        mySQLiteHelper = new MySQLiteHelper(this,"table_budget",null,1);

        //点后退图片finish此activity
        ImageView image_view_back = (ImageView) this.findViewById(R.id.image_view_back_budget);
        editText_budget = (EditText) this.findViewById(R.id.edit_text_budget);   //金额editText
        Button button_sure = this.findViewById(R.id.button_sure);       //确定按钮

        image_view_back.setOnClickListener(this);
        button_sure.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_back_budget:
                this.finish();
                break;
            case R.id.button_sure:
                //预算保存
                String budget_value = editText_budget.getText().toString();
                Log.d(tag,"budget_value:"+budget_value);
                SQLiteDatabase database = mySQLiteHelper.getReadableDatabase();
                mySQLiteHelper.saveBudget(database,budget_value);
                Toast.makeText(BudgetActivity.this,"操作完成！",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}
