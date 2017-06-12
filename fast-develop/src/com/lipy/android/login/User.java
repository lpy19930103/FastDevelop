package com.lipy.android.login;

import com.lipy.android.http.response.DataObject;

/**
 * Created by lipy on 2017/6/8.
 */

public class User implements DataObject {
    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
