package com.cm.bill.activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cm.bill.R;
import com.cm.bill.fragment.FragmentAccount;
import com.cm.bill.fragment.FragmentDetails;
import com.cm.bill.fragment.FragmentJYB;
import com.cm.bill.fragment.FragmentSetting;
import com.cm.bill.service.ScreenBroadcastReceiver;
import com.cm.bill.util.FingerAuthenticateCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录每一笔账单，钱都花在了哪里。
 * 律己或许是拯救自己！
 *
 * 1、ViewPager
 * 2、FragmentPagerAdapter（还有一个FragmentStatePagerAdapter，前者适合碎片较少的时候）
 */
public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private FragmentStatePagerAdapter fragmentStatePagerAdapter;
    private List<Fragment> listFragments = new ArrayList<Fragment>();
    private final String tag ="BB_MainActivity";
    /**
     * 底部四个按钮
     */
    private LinearLayout linearLayout_bills;
    private LinearLayout linearLayout_details;
    private LinearLayout linearLayout_jyb;
    private LinearLayout linearLayout_setting;

    private ImageButton button_bills;
    private ImageButton button_details;
    private ImageButton button_jyb;
    private ImageButton button_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //监听锁屏、解锁状态
        ScreenBroadcastReceiver.getInstance(this).begin(new ScreenBroadcastReceiver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                //亮屏
                Log.d(tag, "亮屏");
            }
            @Override
            public void onScreenOff() {
                // 锁屏
                Log.d(tag, "锁屏");
            }
            @Override
            public void onUserPresent() {
                // 解锁
                Log.d(tag, "解锁");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    //直接通过 指纹类 的 _startActivity跳转到 FirstActivity 进行相关验证
                    FingerAuthenticateCallBack.getInstance(false,MainActivity.this)._startActivity();
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.id_viewpager);

        //初始化linearLayout和button
        initView();
        //设置点击监听
        linearLayout_bills.setOnClickListener(this);
        linearLayout_details.setOnClickListener(this);
        linearLayout_jyb.setOnClickListener(this);
        linearLayout_setting.setOnClickListener(this);

        //FragmentPagerAdapter缓存机制问题，导致fragment数据不会实时刷新
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override   //获取有多少个fragment
            public int getCount() {
                return listFragments.size();
            }
            @Override   //设置fragment
            public Fragment getItem(int i) {
                Log.d(tag,"getItem:"+i);
                return listFragments.get(i);
            }
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
//        fragmentStatePagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public int getCount() {
//                return listFragments == null ? 0 : listFragments.size();
//            }
//            @Override
//            public Fragment getItem(int i) {
//                Log.d(tag,"getItem:"+i);
//                return listFragments.get(i);
//            }
//            @Override
//            public int getItemPosition(Object object) {
//                return POSITION_NONE;
//            }
//        };

        //设置适配器
        viewPager.setAdapter(fragmentPagerAdapter);
        //页面状态改变监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //设置当前fragment的样式
                resetTabBtn();
                switch (position) {
                    case 0:
                        button_bills.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange1));
                        break;
                    case 1:

                        button_details.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange1));
                        break;
                    case 2:
                        button_jyb.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange1));
                        break;
                    case 3:
                        button_setting.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange1));
                        break;

                }
            }

            @Override   //Scroll 滚动
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                //滑动状态监听
            }
        });
    }
    @Override
    public void onClick(View v) {
        //viewpager设置当前页面
        switch (v.getId()) {
            case R.id.button_bills_1:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.button_details_1:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.button_jyb_1:
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.button_setting_1:
                viewPager.setCurrentItem(3, true);
                break;
        }
    }

    private void initView() {
        Log.d(tag,"initView start");
        linearLayout_bills = (LinearLayout) findViewById(R.id.button_bills_1);
        linearLayout_details = (LinearLayout) findViewById(R.id.button_details_1);
        linearLayout_jyb = (LinearLayout) findViewById(R.id.button_jyb_1);
        linearLayout_setting = (LinearLayout) findViewById(R.id.button_setting_1);

        button_bills = (ImageButton)findViewById(R.id.button_bills_2);
        button_details = (ImageButton)findViewById(R.id.button_details_2);
        button_jyb = (ImageButton)findViewById(R.id.button_jyb_2);
        button_setting = (ImageButton)findViewById(R.id.button_setting_2);
        //默认首个选中
        button_bills.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange1));

        setListFragments();
        Log.d(tag,"initView end");
    }
    private void setListFragments(){
        Log.d(tag,"setListFragments 1");
        listFragments.clear();
        FragmentAccount fragmentAccount = new FragmentAccount();  //账户
        FragmentDetails fragmentDetails = new FragmentDetails();    //详情
        FragmentJYB fragmentJYB = new FragmentJYB();        //记一笔
        FragmentSetting fragmentSetting = new FragmentSetting();    //说明

        listFragments.add(fragmentAccount);
        listFragments.add(fragmentDetails);
        listFragments.add(fragmentJYB);
        listFragments.add(fragmentSetting);
        Log.d(tag,"setListFragments 2");
    }
    //重置imageButton的背景
    protected void resetTabBtn() {
        Log.d(tag,"resetTabBtn");
        button_bills.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange2));
        button_details.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange2));
        button_jyb.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange2));
        button_setting.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.colorOrange2));
    }

}
