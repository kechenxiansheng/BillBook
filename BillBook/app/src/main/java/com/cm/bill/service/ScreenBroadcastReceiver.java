package com.cm.bill.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * 屏幕（screen）状态广播接收者
 * 监听锁屏，解锁等
 */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    //本类实例
    private static ScreenBroadcastReceiver receiver;
    //应用上下文
    private static Context context;
    //爱定义接口回调
    private static ScreenStateListener screenStateListener;


    public static ScreenBroadcastReceiver getInstance(Context mcontext){
        context = mcontext;
        if(receiver==null){
            receiver = new ScreenBroadcastReceiver();
        }
        return receiver;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        //intent 就是注册时传入的intent
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            //屏幕开启，亮屏
            screenStateListener.onScreenOn();
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            //屏幕关闭，变黑
            screenStateListener.onScreenOff();
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            //屏幕解锁
            screenStateListener.onUserPresent();
        }
    }

    /**
     * 开始监听screen状态
     */
    public void begin(ScreenStateListener listener) {
        screenStateListener = listener;
        //添加屏幕状态
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);      //屏幕开启，亮屏
        filter.addAction(Intent.ACTION_SCREEN_OFF);     //屏幕关闭，变黑
        filter.addAction(Intent.ACTION_USER_PRESENT);   //屏幕解锁
        context.registerReceiver(receiver, filter);     //注册广播接收器
        //获取屏幕状态
        getScreenState();
    }

    /**
     * 获取screen状态
     */
    private void getScreenState() {
        PowerManager manager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(manager == null){
            return;
        }
        // isScreenOn 其实是描述的电源状态（内部直接调用的isInteractive），api20 之后，已替换为 isInteractive 方法，
        if (manager.isScreenOn()) {
            if (screenStateListener != null) {
                screenStateListener.onScreenOn();
            }
        } else  {
            if (screenStateListener != null) {
                screenStateListener.onScreenOff();
            }
        }
    }

    /**
     * 停止screen状态监听
     */
    public void unregisterListener() {
        context.unregisterReceiver(receiver);
    }


    //状态回调接口
    public interface ScreenStateListener {
        void onScreenOn();
        void onScreenOff();
        void onUserPresent();
    }

}