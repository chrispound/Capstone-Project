package io.poundcode.androidgithubapiwrapper.pullrequest;

public class GitHubPullRequest {

    private int id;
    private int number;
    private boolean state;
    private String title;

    public GitHubPullRequest(int id, int number, boolean state, String title) {
        this.id = id;
        this.number = number;
        this.state = state;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
