package com.cm.bill.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.cm.bill.activity.FirstActivity;
import com.cm.bill.activity.MainActivity;

/**
 * 自定义子类继承指纹管理类
 */
public class FingerAuthenticateCallBack extends FingerprintManagerCompat.AuthenticationCallback {
    private static final String TAG = "BB_finger";
    public static int residue_degree = 5;     //本次指纹识别剩余次数
    public static boolean isFirst = false;     //是否是FirstActivity
    public static Activity cur_activity;       //当前activity
    private static FingerAuthenticateCallBack authenticateCallBack;

    public static FingerAuthenticateCallBack getInstance(boolean _isFirstAct,Activity _curActivity){

        if(authenticateCallBack==null){
            authenticateCallBack = new FingerAuthenticateCallBack();
        }
        isFirst = _isFirstAct;
        cur_activity = _curActivity;
        return authenticateCallBack;
    }
    // 5次机会都失败后回调此函数，errString是错误信息
    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Log.e(TAG, "onAuthenticationError，errMsgId: " + errMsgId + ",errString:" + errString);
    }

    // 当指纹验证失败的时候会回调此函数，失败之后谷歌允许5次机会尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
    // 第5次会调用 onAuthenticationError(int errMsgId, CharSequence errString)方法，此时errMsgId==7。
    @Override
    public void onAuthenticationFailed() {
        residue_degree--;
        Toast.makeText(cur_activity, "指纹识别失败，还有" + residue_degree + "次解锁机会！", Toast.LENGTH_SHORT).show();
        Log.e(TAG, "onAuthenticationFailed: " + "验证失败，还有" + residue_degree + "次解锁机会！");
    }

    @Override   //出现的可以恢复的异常时回调（比如手指移动太快，只扫描到部分）
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Log.e(TAG, "onAuthenticationHelp: " + helpString);
        Toast.makeText(cur_activity, "手指移动太快，请重试", Toast.LENGTH_SHORT).show();
    }

    // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
    @Override
    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
        residue_degree = 5;
        Log.d(TAG, "onAuthenticationSucceeded: " + "验证成功");
        _startActivity();
    }

    //activity跳转
    public void _startActivity(){
        Log.d(TAG,"cur_activity:"+cur_activity.getLocalClassName());
        Intent intent = new Intent(cur_activity,isFirst ? MainActivity.class : FirstActivity.class);
        cur_activity.startActivity(intent);
        cur_activity.finish();
    }
}