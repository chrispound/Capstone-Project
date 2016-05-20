package io.poundcode.androidgithubapiwrapper.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    String login;
    @SerializedName("avatar_url")
    String avatarUrl;

    public User(){}

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
