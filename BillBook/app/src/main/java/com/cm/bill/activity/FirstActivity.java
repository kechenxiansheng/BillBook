package com.cm.bill.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.cm.bill.R;
import com.cm.bill.application.MyApplication;
import com.cm.bill.util.FingerAuthenticateCallBack;

/**
 * 指纹解锁activity
 */
public class FirstActivity extends AppCompatActivity {
    //指纹管理类对象
    private FingerprintManagerCompat manager;
    private final String tag ="BB_FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        manager = FingerprintManagerCompat.from(this);
        if(!manager.isHardwareDetected()){
            Toast.makeText(FirstActivity.this,"当前设备不支持指纹或指纹功能异常！",Toast.LENGTH_SHORT).show();
            FingerAuthenticateCallBack.getInstance(true,FirstActivity.this)._startActivity();
            return;
        }
        if(!manager.hasEnrolledFingerprints()){
            Toast.makeText(FirstActivity.this,"没有指纹记录，请先设置指纹锁屏！",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e(tag,"version："+android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            manager.authenticate(null, 0, null, FingerAuthenticateCallBack.getInstance(true,FirstActivity.this), null);
        }
    }
}
