package io.poundcode.gitdo.test;

import java.util.ArrayList;
import java.util.List;

import io.poundcode.androidgithubapiwrapper.comment.GitHubComment;
import io.poundcode.androidgithubapiwrapper.repository.GitHubIssue;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepositoryDetail;
import io.poundcode.androidgithubapiwrapper.user.User;

public class TestData {

    public static List<GitHubRepository> getGitHubRepositoryTestData() {
        List<GitHubRepository> data = new ArrayList<>();
        GitHubRepository fake = new GitHubRepository("1", "Main", "This is my main project1", 3);
        GitHubRepository fake1 = new GitHubRepository("1", "Main-1", "This is my main project2", 4);
        GitHubRepository fake2 = new GitHubRepository("3", "Main3", "This is my main project3", 5);
        GitHubRepository fake3 = new GitHubRepository("4", "Main4", "This is my main project4", 8);
        fake.setOwner(new User("owner1"));
        fake1.setOwner(new User("owner2"));
        fake2.setOwner(new User("owner3"));
        fake3.setOwner(new User("owner4"));
        data.add(fake);
        data.add(fake1);
        data.add(fake2);
        data.add(fake3);
        return data;
    }

    public static List<GitHubRepositoryDetail> getGitHubRepositoryIssueList() {
        List<GitHubRepositoryDetail> issues = new ArrayList<>();
        GitHubIssue issue = new GitHubIssue("1", "Issue-1", "0", "When I try to build a release version with proguard, it fail to build with the error from ViewBinder generated code.\\r\\nHave anyone seen this problem before? \\r\\n\\r\\n![screen shot 2016-05-19 at 2 35 57 pm](https://cloud.githubusercontent.com/assets/12553442/15386092/00739a80-1dd0-11e6-846b-0dc669ccd237.png)\\r\\n", "open", "https://api.github.com/repos/JakeWharton/butterknife/issues/583/comments");
        GitHubIssue issue1 = new GitHubIssue("2", "Issue-2", "0", "When I try to build a release version with proguard, it fail to build with the error from ViewBinder generated code.\\r\\nHave anyone seen this problem before? \\r\\n\\r\\n![screen shot 2016-05-19 at 2 35 57 pm](https://cloud.githubusercontent.com/assets/12553442/15386092/00739a80-1dd0-11e6-846b-0dc669ccd237.png)\\r\\n", "open", "https://api.github.com/repos/JakeWharton/butterknife/issues/583/comments");
        GitHubIssue issue2 = new GitHubIssue("3", "Issue-3", "0", "When I try to build a release version with proguard, it fail to build with the error from ViewBinder generated code.\\r\\nHave anyone seen this problem before? \\r\\n\\r\\n![screen shot 2016-05-19 at 2 35 57 pm](https://cloud.githubusercontent.com/assets/12553442/15386092/00739a80-1dd0-11e6-846b-0dc669ccd237.png)\\r\\n", "open", "https://api.github.com/repos/JakeWharton/butterknife/issues/583/comments");
        issues.add(issue);
        issues.add(issue1);
        issues.add(issue2);
        return issues;
    }

    public static List<GitHubComment> getGitHubCommentList() {
        List<GitHubComment> comments = new ArrayList<>();
        GitHubComment comment = new GitHubComment("1","Comments", new User("Sirchip"));
        comments.add(comment);
        return comments;
    }
}
