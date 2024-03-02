package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LoginRequestMessage extends Message {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;



    public LoginRequestMessage() {
    }

    public LoginRequestMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
