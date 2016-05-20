package io.poundcode.androidgithubapiwrapper.repository;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GitHubRepositoryDetail implements Serializable {

    @SerializedName("number")
    protected String id;
    protected String title;
    protected String repositoryId;
    protected String body;
    protected String state;
    protected String comments_url;

    public GitHubRepositoryDetail() {
    }

    public GitHubRepositoryDetail(String id, String title, String repositoryId,
                       String body, String state, String comments_url) {
        this.id = id;
        this.title = title;
        this.repositoryId = repositoryId;
        this.body = body;
        this.state = state;
        this.comments_url = comments_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }


}
