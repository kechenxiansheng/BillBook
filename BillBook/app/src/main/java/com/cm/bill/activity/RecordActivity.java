package com.cm.bill.activity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cm.bill.R;
import com.cm.bill.database.MySQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 记录 对应activity
 * Record 记录
 */
public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    private final String tag = "BB_RecordActivity";
    private Button button_save, button_cancel;
    private Spinner spinner_type;

    private EditText text_view_time,edit_text_money,edit_text_note;
    private RadioGroup radioGroup;

    private MySQLiteHelper mySQLiteHelper ;
    private ImageButton imageButtonBack;

    // 日历类
    private Calendar calendar;
    //日历控件
    private DatePickerDialog datePickerDialog;
    //保存类型数据
    private String choose_type, content_select_group;
    //存储填写的消费记录
    private ArrayList<String> _dataList = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        mySQLiteHelper = new MySQLiteHelper(this,"table_bills",null,1);

        //获得控件
        button_save  = (Button) findViewById(R.id.button_save);  //保存按钮
        button_cancel = (Button) findViewById(R.id.button_cancel);  //取消按钮
        text_view_time = (EditText) findViewById(R.id.text_view_cur_time);  //输入当前时间的view
        edit_text_money = (EditText) findViewById(R.id.edit_text_money);    //输入花费金额的view
        edit_text_note = (EditText) findViewById(R.id.edit_text_note);      //输入备注的view
        spinner_type = (Spinner) findViewById(R.id.spinner_type);           //消费类型的下拉列表
        imageButtonBack = (ImageButton) this.findViewById(R.id.imageButtonBack);    //返回按钮
        //设置Spinner的数据并操作
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_item,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        //将数据和spinner控件绑定在一起
        spinner_type.setAdapter(adapter);

        text_view_time.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);
        spinner_type.setOnItemSelectedListener(listener);
        button_save.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
    }

    //spinner控件的响应事件
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            //获取选择的类型
            choose_type = spinner_type.getItemAtPosition(pos).toString();
            Log.d(tag,"choose:"+choose_type);
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    };
    //组件点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                _dataList.clear();
                String time = text_view_time.getText().toString();
                String money = edit_text_money.getText().toString();
                String note = edit_text_note.getText().toString();
                if(time.isEmpty() || money.isEmpty()){
//                    System.out.println("check params: 参数为空！");
                    Toast.makeText(RecordActivity.this,"参数为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                _dataList.add(choose_type);
                _dataList.add(time);
                _dataList.add(money);
                _dataList.add(note);
                //[房租, 2020-03-14, 200, 房租]
                Log.d(tag,"save_data 1:"+ _dataList.toString());
                //存储数据

                SQLiteDatabase database = mySQLiteHelper.getReadableDatabase();
                mySQLiteHelper.WriteData(database,_dataList);
                Toast.makeText(RecordActivity.this,"操作完成",Toast.LENGTH_SHORT).show();

                break;
            case R.id.button_cancel:
                this.finish();
                break;
            case R.id.imageButtonBack:
                this.finish();
                break;
            case R.id.text_view_cur_time:
                calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                text_view_time.setText(DateFormat.format("yyy-MM-dd", calendar));
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
                break;
            default:
                break;
        }
    }
}