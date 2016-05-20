package io.poundcode.androidgithubapiwrapper.repository;

import android.os.Parcelable;

import java.io.Serializable;

import io.poundcode.androidgithubapiwrapper.user.Owner;

public class GitHubRepository implements Serializable {

    private String id;
    private String name;
    private String description;
    private Owner owner;
    private int open_issues_count;

    public GitHubRepository() {
    }

    public GitHubRepository(String id, String name, String description, int open_issues_count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.open_issues_count = open_issues_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOpenIssuesCount() {
        return open_issues_count;
    }

    public void setOpenIssuesCount(int openIssuesCount) {
        this.open_issues_count = openIssuesCount;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
