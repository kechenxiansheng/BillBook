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

//    public void ExportData() {
//
//        String outputFile = "finance.xls";
//        try {
//
//            // 判断是否存在SD卡
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            } else {
//                Toast.makeText(getActivity(), "sd卡不存在", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Finance/";
//            File file = new File(sdCardRoot + outputFile);
//
//            // 创建新的Excel 工作簿
//            HSSFWorkbook workbook = new HSSFWorkbook();
//
//            // 在Excel工作簿中建一工作表，其名为缺省值
//            // 如要新建一名为"效益指标"的工作表，其语句为：
//            // HSSFSheet sheet = workbook.createSheet("效益指标");
//            HSSFSheet sheet = workbook.createSheet("消费记录");
//            // 在索引0的位置创建行（最顶端的行）
//            HSSFRow row = sheet.createRow((short) 0);
//
//
//            //创建表头
//            HSSFCell IDCell = row.createCell(0);
//            IDCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            IDCell.setCellValue("ID(ID号)");
//
//            HSSFCell TypeCell = row.createCell(1);
//            TypeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            TypeCell.setCellValue("Type(类型)");
//
//            HSSFCell TimeCell = row.createCell(2);
//            TimeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            TimeCell.setCellValue("Time(时间)");
//
//            HSSFCell FeeCell = row.createCell(3);
//            FeeCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            FeeCell.setCellValue("Fee(费用)");
//
//            HSSFCell RemarksCell = row.createCell(4);
//            RemarksCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            RemarksCell.setCellValue("Remarks(备注)");
//
//            HSSFCell BudgetCell = row.createCell(5);
//            BudgetCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//            BudgetCell.setCellValue("Budget(收支)");
//
//
//
//
//            mMysql = new MySQLiteHelper(getActivity(), "finance.db", null, 1);
//            mDataBase = mMysql.getReadableDatabase();
//
//            Cursor cursor = mDataBase.rawQuery("select * from finance", null);
//            cursor.moveToFirst();
//            int columnsSize = cursor.getColumnCount();
//            int number = 0;
//            while (number < cursor.getCount()) {
//                String budget = cursor.getString(cursor.getColumnIndex("Budget"));
//                int ID = cursor.getInt(cursor.getColumnIndex("ID"));
//                Double Fee = cursor.getDouble(cursor.getColumnIndex("Fee"));
//                String Time = cursor.getString(cursor.getColumnIndex("Time"));
//                String Remarks = cursor.getString(cursor.getColumnIndex("Remarks"));
//                String Type = cursor.getString(cursor.getColumnIndex("Type"));
//                row = sheet.createRow(number + 1);
//                for (int i = 0; i < 6; i++) {
//                    IDCell = row.createCell(i);
//                    IDCell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                    switch (i) {
//                        case 0:
//                            IDCell.setCellValue(ID);
//                            break;
//                        case 1:
//                            IDCell.setCellValue(Type);
//                            break;
//                        case 2:
//                            IDCell.setCellValue(Time);
//                            break;
//                        case 3:
//                            IDCell.setCellValue(Fee);
//                            break;
//                        case 4:
//                            IDCell.setCellValue(Remarks);
//                            break;
//                        case 5:
//                            IDCell.setCellValue(budget);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//                cursor.moveToNext();
//                number++;
//            }
//            cursor.close();
//            mDataBase.close();
//            mMysql.close();
//
//            // 新建一输出文件流
//            FileOutputStream fOut = new FileOutputStream(sdCardRoot + outputFile);
//            // 把相应的Excel 工作簿存盘
//            workbook.write(fOut);
//            fOut.flush();
//            // 操作结束，关闭文件
//            fOut.close();
//            workbook.close();
//
//            //消息通知栏
//            //定义NotificationManager
//            String ns = Context.NOTIFICATION_SERVICE;
//            NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(ns);
//            //定义通知栏展现的内容信息
//            int icon = R.drawable.back;
//            CharSequence tickerText = "通知栏";
//            long when = System.currentTimeMillis();
//            Notification notification = new Notification(icon, tickerText, when);
//
//            //定义下拉通知栏时要展现的内容信息
//            Context context = getActivity().getApplicationContext();
//            CharSequence contentTitle = "已经导出数据";
//            CharSequence contentText = "点击查看文件";
//
//            File path = new File(Environment.getExternalStorageDirectory().getPath());
//
//            Intent notificationIntent = new Intent(Intent.ACTION_GET_CONTENT);
//            notificationIntent.setDataAndType(Uri.fromFile(file), "Finance/");
//
//            PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), 0,
//                    notificationIntent, 0);
//            notification.setLatestEventInfo(context, contentTitle, contentText,
//                    contentIntent);
//
//            //用mNotificationManager的notify方法通知用户生成标题栏消息通知
//            mNotificationManager.notify(1, notification);
//            vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//            long [] pattern = {100,400,100,400};   // 停止 开启 停止 开启
//            vibrator.vibrate(pattern, -1);
//
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "写入失败" + e, Toast.LENGTH_LONG).show();
//        } finally {
//            //  Toast.makeText(getActivity(), "关闭", Toast.LENGTH_LONG).show();
//        }
//    }
}