package io.poundcode.androidgithubapiwrapper.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;

public class GitHubSearchRepositoryResult {

    @SerializedName("total_count")
    int totalCount;
    @SerializedName("items")
    List<GitHubRepository> repositories;

    public GitHubSearchRepositoryResult() {
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<GitHubRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<GitHubRepository> repositories) {
        this.repositories = repositories;
    }
}
