package com.jarry.chat.model.request;

/**
 * Description:
 * User: Jarry
 * Date: 2018-01-28
 * Time: 17:26
 */
public class LoginParam {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
