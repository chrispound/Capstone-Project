package io.poundcode.androidgithubapiwrapper.repository;

public class GitHubRepository {

    private String id;
    private String name;
    private String description;
    private int openIssuesCount;

    public GitHubRepository(String id, String name, String description, int open_issues_count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.openIssuesCount = open_issues_count;
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
        return openIssuesCount;
    }

    public void setOpenIssuesCount(int openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

}
