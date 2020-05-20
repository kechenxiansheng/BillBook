package com.cm.bill.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cm.bill.R;
import com.cm.bill.database.MySQLiteHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 详情页
 */
public class FragmentDetails extends Fragment{

    private final String tag = "BB_FragmentDetails";
    // 适配器
    private SimpleAdapter simpleAdapter;
    //数据库
    private MySQLiteHelper mySQLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    // 存储数据的数组列表
    ArrayList<HashMap<String, Object>> listData = new ArrayList<>();
    //存储消费类型 及金额的map
    HashMap<String, Object> map = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(tag,"onCreateView");
        View view = inflater.inflate(R.layout.activity_details, container, false);
        //列举数据的ListView
        ListView listView = (ListView) view.findViewById(R.id.details_list_view);
        //查询数据
        getData();
        simpleAdapter = new SimpleAdapter(getActivity(), listData, R.layout.layout_listview_details, new String[]{"type", "money"},
                new int[]{ R.id.details_type, R.id.details_money});
        //设置适配器，加载数据
        listView.setAdapter(simpleAdapter);
        return view;
    }

    //从数据库获得适配器数据
    public void getData() {
        Log.d(tag,"getData start");
        //每次启动需要清空
        listData.clear();
        map.clear();

        mySQLiteHelper = new MySQLiteHelper(getActivity(), "table_bills", null, 1);
        sqLiteDatabase = mySQLiteHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select time,type,money from table_bills order by id DESC ", null);
        cursor.moveToFirst();
        float mongey_yiwu = 0;
        float mongey_canshi = 0;
        float mongey_fangzu = 0;
        float mongey_richangchuxing = 0;
        float mongey_lvyou = 0;
        float mongey_huafei = 0;
        float mongey_shenghuoyongfei = 0;
        float mongey_yule = 0;
        float mongey_dianzishebei = 0;
        float mongey_qita = 0;

        int num = 0;    //计数器
        String curYear =  String.valueOf(Calendar.getInstance().get(Calendar.YEAR));       //当前年份
        int cMonth =  Calendar.getInstance().get(Calendar.MONTH) + 1;
        String curMonth = cMonth < 10 ? "0"+cMonth : String.valueOf(cMonth);    //当前月份
        Log.d(tag,"cur_date:"+curYear+"-"+curMonth);

        while (cursor.getCount() > num) {
            String date = cursor.getString(cursor.getColumnIndex("time"));  //本条记录的时间
            //只查询当前月的记录
            if(date.startsWith(curYear+"-"+curMonth)){
                String type = cursor.getString(cursor.getColumnIndex("type"));  //本条记录消费类型
                Float money =  Float.parseFloat(cursor.getString(cursor.getColumnIndex("money")));  //本条记录消费金额
                switch (type){
                    case  "衣物":
                        mongey_yiwu += money;
                        map.put(type,mongey_yiwu);
                        break;
                    case  "餐食":
                        mongey_canshi += money;
                        map.put(type,mongey_canshi);
                        break;
                    case  "房租":
                        mongey_fangzu += money;
                        map.put(type,mongey_fangzu);
                        break;
                    case  "日常出行":
                        mongey_richangchuxing += money;
                        map.put(type,mongey_richangchuxing);
                        break;
                    case  "旅游":
                        mongey_lvyou += money;
                        map.put(type,mongey_lvyou);
                        break;
                    case  "话费":
                        mongey_huafei += money;
                        map.put(type,mongey_huafei);
                        break;
                    case  "生活用费":
                        mongey_shenghuoyongfei += money;
                        map.put(type,mongey_shenghuoyongfei);
                        break;
                    case  "娱乐":
                        mongey_yule += money;
                        map.put(type,mongey_yule);
                        break;
                    case  "电子设备":
                        mongey_dianzishebei += money;
                        map.put(type,mongey_dianzishebei);
                        break;
                    case  "其他":
                        mongey_qita += money;
                        map.put(type,mongey_qita);
                        break;
                }
            }
            num++;
            cursor.moveToNext();
        }
        Log.d(tag,"getData,map:" + map);
        if(!map.isEmpty()){
            for (String key : map.keySet()) {
                HashMap<String, Object> hashMap = new HashMap<>();
                String val = String.valueOf(map.get(key));
                hashMap.put("type", key);
                hashMap.put("money", val);
                listData.add(hashMap);
            }
        }

        Log.d(tag,"getData,listData:" + listData);
        Log.d(tag,"getData end");
        cursor.close();
        sqLiteDatabase.close();
        mySQLiteHelper.close();
    }

}
