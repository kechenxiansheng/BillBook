package com.cm.bill.application;

import android.app.Application;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;

import com.cm.bill.util.AppFrontBackHelper;

public class MyApplication extends Application {
    private final String TAG = "BB_Application";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"BillBook start");

//        AppFrontBackHelper helper = new AppFrontBackHelper();
//        helper.register(this, new AppFrontBackHelper.OnAppStatusListener() {
//            @Override
//            public void onFront() {
//                //应用切到前台处理
//                Log.d(TAG,"BillBook 被切到前台");
//            }
//
//            @Override
//            public void onBack() {
//                //应用切到后台处理
//                Log.d(TAG,"BillBook 被切到后台");
//            }
//        });
    }

}
