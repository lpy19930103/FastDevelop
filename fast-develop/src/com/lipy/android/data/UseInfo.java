package com.lipy.android.data;

import android.content.Context;
import android.content.Intent;

/**
 * 用户操作类
 */
public class UseInfo {


    // Tab切换
    private int mainIndex = 0;
    // 预跳转
    private Class<?> nextClass;
    // 预跳转 需要传递数据了增加intent
    private Intent intent;



    public int getMainIndex() {
        return mainIndex;
    }

    public void setMainIndex(int mainIndex) {
        this.mainIndex = mainIndex;
    }


    /**
     * 跳转到首页
     *
     * @param context   activity
     * @param mainIndex 首页flag
     */
    public void go2Main(Context context, int mainIndex) {
        try {
            Intent intent = new Intent(context,
                    Class.forName("com.lipy.android.MainActivity"));
            UserData.getInstance().getUseInfo().setMainIndex(mainIndex);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void clear() {

    }
}
