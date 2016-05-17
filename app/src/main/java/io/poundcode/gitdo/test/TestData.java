package io.poundcode.gitdo.test;

import java.util.ArrayList;
import java.util.List;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;

public class TestData {

    public static List<GitHubRepository> getGitHubRepositoryTestData() {
        List<GitHubRepository> data = new ArrayList<>();
        GitHubRepository fake = new GitHubRepository("1", "Main", "This is my main project", 0);
        GitHubRepository fake1 = new GitHubRepository("1", "Main", "This is my main project", 0);
        GitHubRepository fake2 = new GitHubRepository("1", "Main", "This is my main project", 0);
        GitHubRepository fake3 = new GitHubRepository("1", "Main", "This is my main project", 0);
        data.add(fake);
        data.add(fake1);
        data.add(fake2);
        data.add(fake3);
        return data;
    }
}
