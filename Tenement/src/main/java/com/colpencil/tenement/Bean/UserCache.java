package com.colpencil.tenement.Bean;

import java.io.Serializable;

/**
 * @Description: 聊天记录的缓存
 * @author 汪 亮
 * @Email  DramaScript@outlook.com
 * @date 2016/8/30
 */
public class UserCache implements Serializable {

    private int flag;
    private String userId;
    private String content;
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserCache{" +
                "flag=" + flag +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
