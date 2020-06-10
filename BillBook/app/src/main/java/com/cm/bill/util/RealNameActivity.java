package com.cm.bill.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RealNameActivity extends Activity{

	private static RealNameActivity _instance;
	private final String tag ="BB_RealNameActivity";
	private static RealNameCallBack callback;
	TextView toastView;	//提示view
	EditText nameText;	//姓名view
	EditText numText;	//身份证号view
	Button button;		//确定按钮
	String name;		//姓名
	String num;			//身份证号

	Handler handler;

	
	public static RealNameActivity getInstance(){
		if(_instance == null){
			_instance = new RealNameActivity();
		}
		return _instance;
	}
	//设置回调
	public static void setCallBack(RealNameCallBack realNameCallBack) {
		callback = realNameCallBack;
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//填充布局
		setContentView(getResourceID(this,"R.layout.cp_real_name"));
		
		toastView = (TextView)findViewById(getResourceID(this, "R.id.game_geduan_1"));
		nameText = (EditText)findViewById(getResourceID(this, "R.id.cp_real_name_2"));
		numText = (EditText)findViewById(getResourceID(this, "R.id.cp_real_name_num_2"));
		button = (Button)findViewById(getResourceID(this, "R.id.cp_real_name_button"));

		System.out.println("real_name_activity：onCreate");
		
		Bundle bundle = this.getIntent().getExtras();
		final String uid = bundle.getString("uid");
		
		Log.d(tag,"real_name_activity："+"uid:"+uid);
		
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if(uid==null||uid.trim().length()==0){
					Log.d(tag,"real_name_activity：请传入uid.");
					return;
				}
				name = nameText.getText().toString().trim();
				num = numText.getText().toString().trim();
				if(name==null||name.length()==0 || num==null||num.length()==0){
					toastView.setText("姓名或身份证号为空！");
					return;
				}
				
				Map<String,String> map = new HashMap<String, String>();
				map.put("realName",name);
				map.put("cardNum",num);
				map.put("uid",uid);
				Log.d(tag,"real_name_activity：start post 1");
				checkResult();
				postToServer("http://xxxx/verifyRealName",map);

			}
		});
		
	}
	//弹出实名认证时，禁止按返回键退出当前activity
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			return true;
		}
		return false;
	}

	private void checkResult(){
		Log.d(tag,"real_name_activity：checkResult1");
		handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message message) {
				try {
					String res = (String)message.obj;
					JSONObject resultJson = new JSONObject(res);
					int state = resultJson.getInt("state");
					String msg = resultJson.getString("msg");
					if(state == 1){
						//认证成功
						toastView.setText("认证完成！请重新启动游戏！");
						toastView.setTextColor(Color.GREEN);

						Log.d(tag,"real_name_activity：checkResult2");

					}else if (state == -2) {
						//认证失败
						toastView.setText(String.valueOf("认证失败！"+msg));
						Log.d(tag,"real_name_activity：checkResult4");
					}else{
						//其他错误
						toastView.setText(String.valueOf("认证失败！"));
						Log.d(tag,"real_name_activity：checkResult3");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
	}
	//请求服务器，并获取返回结果
	private void postToServer(final String url,final Map<String,String> mapParams){
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("real_name_activity：start post 2");
				//没有导u8jar包，暂时注释
//				String result = U8HttpUtils.httpPost(url,mapParams);
//				Message message = new Message();
//				message.obj = result;
//				handler.sendMessage(message);
			}
		}).start();
	}
	
	
	//跳转游戏activity
//	public void startNextActivity(){
//		try {
//            Class<?> mainClass = Class.forName(GamePackageName + ".MainActivity");
//            Intent intent = new Intent(this, mainClass);
//            startActivity(intent);
//            //跳转之后关闭自己
//            this.finish();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//	}
	//获取资源id
	public static int getResourceID(Context context, String paramString){
		try{
			if(paramString != null){
				String[] splits = paramString.split("\\.", 3);
				if(splits.length == 3){
					//context.getResources().getIdentifier() 获取指定应用包下指定的资源id
					//第一个参数:ID名，第二个:资源属性是ID或者是Drawable，第三个:包名。
					return context.getResources().getIdentifier(splits[2], splits[1], context.getPackageName());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public interface RealNameCallBack{
		void result(Boolean boo);
	}
}
