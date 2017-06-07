package com.lipy.android.data;

/**
 * 用户信息类
 */
public class UserData {

    private static UserData instance;

    // 用户操作类
    private UseInfo useInfo;

    private UserData() {
        useInfo = new UseInfo();
    }

    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }



    public UseInfo getUseInfo() {
        return useInfo;
    }



    public void clear(){
        UserData.getInstance().getUseInfo().clear();
    }
}
