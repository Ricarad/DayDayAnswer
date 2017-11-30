package com.ricarad.app.daydayanswer.moudle;


import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by dongdong on 2017/11/28.
 */

public class User extends BmobObject implements Serializable {
    private String usercount;
    private String username;
    private String role;
    private String password;

    public String getUsercount() {
        return usercount;
    }

    public void setUsercount(String usercount) {
        this.usercount = usercount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
