package io.poundcode.gitdo.test;

import java.util.ArrayList;
import java.util.List;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.user.Owner;

public class TestData {

    public static List<GitHubRepository> getGitHubRepositoryTestData() {
        List<GitHubRepository> data = new ArrayList<>();
        GitHubRepository fake = new GitHubRepository("1", "Main", "This is my main project1", 0);
        GitHubRepository fake1 = new GitHubRepository("1", "Main", "This is my main project2", 0);
        GitHubRepository fake2 = new GitHubRepository("1", "Main", "This is my main project3", 0);
        GitHubRepository fake3 = new GitHubRepository("1", "Main", "This is my main project4", 0);
        fake.setOwner(new Owner("owner1"));
        fake1.setOwner(new Owner("owner2"));
        fake2.setOwner(new Owner("owner3"));
        fake3.setOwner(new Owner("owner4"));
        data.add(fake);
        data.add(fake1);
        data.add(fake2);
        data.add(fake3);
        return data;
    }
}
