package com.cm.bill.test;

/**
 * 总结
 */
public class Conclusion {
    /**
     * 账单app 功能说明
     * 一、登陆界面
     * 1、包括用户名、密码功能
     *      1.1 用户名：首次启动需输入，以后无需输入
     *      1.2 密码：每次进入必须输入（毕竟账单是个人的重要信息）
     *
     * 开发结果：直接做了指纹验证
     *
     * 二、主界面
     * 1、首页，底部四个按钮，分别是账户、详情、记一笔、设置。
     *      1.1 账户：显示本月花费总额
     *      1.2 详情：显示本月在各个消费类型中分别花费了多少额度
     *      1.3 记一笔：包括 记录、账单、预算等选项
     *          1.3.1 记录：显示当前消费 需要记录的时间、类型、消费金额
     *          1.3.2 账单：消费的类型，时间，金额 的列表
     *          1.3.3 预算：本月计划消费最高额度
     *      1.4 设置：修改昵称、修改密码、其他功能待补充
     *
     * 开发结果：因为不再需要修改密码，设置页替换为了说明页
     *
     * 2、打开时默认显示账户界面
     */


    /**
     * 本Demo使用到的知识：
     * 1、viewpager：页面的滑动切换
     *      1.1、FragmentPagerAdapter：碎片页面的适配器（适合碎片较少时使用，会将每个页面数据缓存在内存中）
     * 2、fragment：碎片。单个的页面
     * 3、FingerprintManagerCompat：指纹识别验证
     * 4、BroadcastReceiver：广播接收器，用于屏幕状态监听
     * 5、SQLiteOpenHelper：数据进行表存储、操作
     * 6、DecimalFormat：数字格式转化
     * 7、listView：使用的简单适配器 SimpleAdapter
     * 8、Spinner：下拉列表。使用了数组适配器 ArrayAdapter
     * 9、DatePickerDialog：日历控件
     * 10、View.OnCreateContextMenuListener：为 listView 添加长按事件监听
     *
     * 以及常用的activity，TextView，ImageView，ImageButton，LinearLayout，RelativeLayout，Dialog等等
     *
     *
     *
     * 整个应用布局较为相似微信，不过微信的单个页面布局肯定没有简单的，最起码单个页面就用到了自定义布局
     *
     *
     */
}
