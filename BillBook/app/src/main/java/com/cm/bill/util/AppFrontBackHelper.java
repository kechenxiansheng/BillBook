package com.cm.bill.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * 应用前后台状态监听帮助类，仅在Application中使用
 *
 */

public class AppFrontBackHelper {

    private OnAppStatusListener mOnAppStatusListener;


    public AppFrontBackHelper() {

    }

    /**
     * 注册状态监听，仅在Application中使用
     * @param application
     * @param listener
     */
    public void register(Application application, OnAppStatusListener listener){
        mOnAppStatusListener = listener;
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public void unRegister(Application application){
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        private static final String TAG = "BillBook";
        //打开的Activity数量统计
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.d(TAG,"BillBook onActivityCreated，"+activity.getLocalClassName()+" 创建");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            //数值从0变到1说明是从后台切到前台
            if (activityStartCount == 1){
                //从后台切到前台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onFront();
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

            Log.d(TAG,"BillBook onActivityResumed，"+activity.getLocalClassName()+" 恢复");
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.d(TAG,"BillBook onActivityPaused，"+activity.getLocalClassName()+" 暂停");
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.d(TAG,"BillBook onActivityStopped，"+activity.getLocalClassName()+" 停止");
            activityStartCount--;
            //数值从1到0说明是从前台切到后台
            if (activityStartCount == 0){
                //从前台切到后台
                if(mOnAppStatusListener != null){
                    mOnAppStatusListener.onBack();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.d(TAG,"BillBook onActivitySaveInstanceState，"+activity.getLocalClassName()+" 保存实例");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.d(TAG,"BillBook onActivityDestroyed，"+activity.getLocalClassName()+" 销毁");
        }
    };

    public interface OnAppStatusListener{
        void onFront();
        void onBack();
    }

}