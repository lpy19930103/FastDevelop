package com.lipy.android.data;


import java.io.Serializable;

/**
 *
 */
public class Business implements Serializable {

    private static Business currentBiz;
    private String name;
    private String classpath;
    private String url;
    private boolean needLogin;
    private String tag;
    private String icon;
    private String pressedIcon;
    private String color;
    private String pressedColor;
    private String textColor;
    private String textPressedColor;

    public static Business getCurrentBiz() {
        return currentBiz;
    }

    public static void setCurrentBiz(Business currentBiz) {
        Business.currentBiz = currentBiz;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getClasspath() {
        return classpath;
    }


    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(String needLogin) {
        this.needLogin = Boolean.valueOf(needLogin);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPressedIcon() {
        return pressedIcon;
    }

    public void setPressedIcon(String pressedIcon) {
        this.pressedIcon = pressedIcon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPressedColor() {
        return pressedColor;
    }

    public void setPressedColor(String pressedColor) {
        this.pressedColor = pressedColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTextPressedColor() {
        return textPressedColor;
    }

    public void setTextPressedColor(String textPressedColor) {
        this.textPressedColor = textPressedColor;
    }

    @Override
    public String toString() {
        return "Business [name=" + name + ", classpath=" + classpath + ", url=" + url + "]";
    }


}
