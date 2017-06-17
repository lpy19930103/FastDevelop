package com.example;

/**
 * Created by lipy on 2017/6/14.
 */

public class User {
    private String name;
    private String[] skills;

    public User(String name, String[] skills) {
        this.name = name;
        this.skills = skills;

    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
