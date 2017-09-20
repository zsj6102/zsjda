package com.colpencil.tenement.Bean;

import java.io.Serializable;

public class UserInfo implements Serializable {


    /**
     * user_id : 7
     * nick_name : 员工
     */

    private int user_id;
    private int communityId;
    private String nickname;
    private String communityName;
    private String shortName;
    private String talkname;

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTalkname() {
        return talkname;
    }

    public void setTalkname(String talkname) {
        this.talkname = talkname;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
