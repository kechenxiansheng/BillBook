package com.cm.bill.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cm.bill.R;
import com.cm.bill.database.MySQLiteHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 账户（account）对应的fragment
 */
public class FragmentAccount extends Fragment {
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private TextView cur_pay_num;
    private TextView cur_month_pay_num;
    private TextView cur_month_budget;

    private final String tag ="BB_FragmentAccount";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag,"onCreateView");
        View view = inflater.inflate(R.layout.activity_account, container, false);

        cur_pay_num = (TextView)view.findViewById(R.id.cur_pay_num);        //总计金额
        cur_month_pay_num = (TextView)view.findViewById(R.id.cur_month_pay_num);    //当前月金额
        cur_month_budget = (TextView)view.findViewById(R.id.cur_month_budget);

        queryMoney();
        queryMoney2();
        queryBudget();
        return view;
    }
    //花费总金额
    private void queryMoney(){
        Log.d(tag,"queryMoney start");
        double resultPay = 0;
        mySQLiteHelper = new MySQLiteHelper(getActivity(), "table_bills", null, 1);
        sqLiteDatabase = mySQLiteHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select money from table_bills",null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                double money = cursor.getDouble(cursor.getColumnIndex("money"));
                //消费总额
                resultPay += money;
                cursor.moveToNext();
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        cur_pay_num.setText(String.valueOf(decimalFormat.format(resultPay)));
        cursor.close();
        sqLiteDatabase.close();
        mySQLiteHelper.close();
        Log.d(tag,"queryMoney end");
    }
    //当月花费金额
    private void queryMoney2(){
        Log.d(tag,"queryMoney2 start");
        double resultPay = 0;
        mySQLiteHelper = new MySQLiteHelper(getActivity(), "table_bills", null, 1);
        sqLiteDatabase = mySQLiteHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select money,time from table_bills",null);
        cursor.moveToFirst();
        int curYear =  Calendar.getInstance().get(Calendar.YEAR);       //当前年份
        int curMonth =  Calendar.getInstance().get(Calendar.MONTH)+1;   //当前月份
        Log.d(tag,"curYear:"+curYear+",curMonth:"+curMonth);

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {

                String date = cursor.getString(cursor.getColumnIndex("time"));  //本条记录的时间

                int cur_year = Integer.parseInt(date.split("-")[0]);    //本条记录的年
                int cur_month = Integer.parseInt(date.split("-")[1]);   //本条记录的月
//                Log.d(tag,"cur_year:"+cur_year+",cur_month:"+cur_month);

                //只查询当前月的记录
                if(cur_year == curYear && cur_month == curMonth){
                    double money = cursor.getDouble(cursor.getColumnIndex("money"));
                    //消费总额
                    resultPay += money;
                }
                cursor.moveToNext();
            }
        }
        //取阿拉伯数字及小数后两位，不存在显示为空
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        cur_month_pay_num.setText(String.valueOf(decimalFormat.format(resultPay)));
        cursor.close();
        sqLiteDatabase.close();
        mySQLiteHelper.close();
        Log.d(tag,"queryMoney2 end");
    }
    //预算
    private void queryBudget(){
        Log.d(tag,"queryBudget start");
        mySQLiteHelper = new MySQLiteHelper(getActivity(), "table_budget", null, 1);
        SQLiteDatabase database = mySQLiteHelper.getReadableDatabase();
        Cursor cursor = database.query("table_budget",null,null,null,null,null,null);
        System.out.println("queryBudget:"+cursor.getCount());
        String budget="";
        //存在数据为true
        while(cursor.moveToNext()){
            budget = cursor.getString(cursor.getColumnIndex("budget"));
        }
        cur_month_budget.setText(budget.isEmpty()?"0":budget);
        cursor.close();
        mySQLiteHelper.close();
        Log.d(tag,"queryBudget end");
    }
}
