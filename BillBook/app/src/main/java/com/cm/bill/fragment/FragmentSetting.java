package com.cm.bill.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cm.bill.R;

/**
 * 说明页
 */
public class FragmentSetting extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);

        //列举数据的ListView
        ListView listView = view.findViewById(R.id.list_view_set);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.set_description));
        //填充数据
        listView.setAdapter(adapter);

        return view;
    }

}
