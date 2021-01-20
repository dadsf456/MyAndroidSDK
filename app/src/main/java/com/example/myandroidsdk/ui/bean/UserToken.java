package com.example.myandroidsdk.ui.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fxb on 2020/6/20.
 */
public class UserToken {
    private long id;
    @SerializedName("access_token")
    private String token;
    private String nickname;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("is_distributor")
    private int isDistributor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getIsDistributor() {
        return isDistributor;
    }

    public void setIsDistributor(int isDistributor) {
        this.isDistributor = isDistributor;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", isDistributor=" + isDistributor +
                '}';
    }
}
