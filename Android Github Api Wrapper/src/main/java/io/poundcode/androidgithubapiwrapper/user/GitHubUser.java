package io.poundcode.androidgithubapiwrapper.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GitHubUser implements Serializable {
    String login;
    @SerializedName("avatar_url")
    String avatarUrl;

    public GitHubUser(){}

    public GitHubUser(String login) {
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
