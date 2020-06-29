package com.cm.bill.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cm.bill.activity.BillsActivity;
import com.cm.bill.R;
import com.cm.bill.activity.BudgetActivity;
import com.cm.bill.activity.RecordActivity;
import com.cm.bill.database.MySQLiteHelper;

public class FragmentJYB extends Fragment implements View.OnClickListener {
    private LinearLayout linearLayout_jl;  //记录布局
    private LinearLayout linearLayout_zd;   //账单布局
    private LinearLayout linearLayout_ys;   //预算布局


    //数据库
    private MySQLiteHelper mMysql;
    private SQLiteDatabase mDataBase;

    //震动的类
    private Vibrator vibrator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_jyb, container, false);
        linearLayout_jl = (LinearLayout) view.findViewById(R.id.jyb_button_jl_1);  //记录
        linearLayout_zd = (LinearLayout) view.findViewById(R.id.jyb_button_zd_1);   //账单
        linearLayout_ys = (LinearLayout) view.findViewById(R.id.jyb_button_ys_1);   //预算

        linearLayout_jl.setOnClickListener(this);
        linearLayout_zd.setOnClickListener(this);
        linearLayout_ys.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jyb_button_jl_1:
                //跳转至记录activity
                Intent intent = new Intent(getActivity(), RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.jyb_button_zd_1:
                //跳转至账单activity
                startActivity(new Intent(getActivity(), BillsActivity.class));
                break;
            case R.id.jyb_button_ys_1:
                //跳转至预算activity
                startActivity(new Intent(getActivity(), BudgetActivity.class));
                break;
        }

    }

}
