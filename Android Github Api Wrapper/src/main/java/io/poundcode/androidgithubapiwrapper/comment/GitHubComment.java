package io.poundcode.androidgithubapiwrapper.comment;

import java.io.Serializable;

import io.poundcode.androidgithubapiwrapper.user.GitHubUser;

public class GitHubComment implements Serializable {

    private String id;
    private String body;
    private GitHubUser gitHubUser;

    public GitHubComment() {
    }

    public GitHubComment(String id, String body, GitHubUser gitHubUser) {
        this.id = id;
        this.body = body;
        this.gitHubUser = gitHubUser;
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
        return gitHubUser;
    }

    public void setGitHubUser(GitHubUser gitHubUser) {
        this.gitHubUser = gitHubUser;
    }
}
