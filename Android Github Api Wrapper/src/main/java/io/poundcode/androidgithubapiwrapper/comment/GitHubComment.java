package io.poundcode.androidgithubapiwrapper.comment;

import java.io.Serializable;

import io.poundcode.androidgithubapiwrapper.user.User;

public class GitHubComment implements Serializable {

    private String id;
    private String body;
    private User user;

    public GitHubComment() {
    }

    public GitHubComment(String id, String body, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
