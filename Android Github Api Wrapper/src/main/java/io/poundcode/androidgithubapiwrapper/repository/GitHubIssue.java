package io.poundcode.androidgithubapiwrapper.repository;

import java.io.Serializable;

public class GitHubIssue extends GitHubRepositoryDetail {

    public GitHubIssue(String id, String title, String repositoryId,
                                  String body, String state, String comments_url) {
        super.id = id;
        this.title = title;
        this.repositoryId = repositoryId;
        this.body = body;
        this.state = state;
        this.comments_url = comments_url;
    }
}
