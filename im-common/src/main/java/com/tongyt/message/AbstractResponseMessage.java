package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class AbstractResponseMessage extends Message {

    /**
     * 请求处理状态
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String reason;



    public AbstractResponseMessage() {
    }

    public AbstractResponseMessage(boolean success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
