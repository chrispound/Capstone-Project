package io.poundcode.androidgithubapiwrapper.comment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.poundcode.androidgithubapiwrapper.user.GitHubUser;

public class GitHubComment implements Serializable {

    private String id;
    private String body;
    @SerializedName("user")
    private GitHubUser user;

    public GitHubComment() {
    }

    public GitHubComment(String id, String body, GitHubUser user) {
        this.id = id;
        this.body = body;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public GitHubUser getGitHubUser() {
        return user;
    }

    public void setGitHubUser(GitHubUser gitHubUser) {
        this.user = gitHubUser;
    }
}
