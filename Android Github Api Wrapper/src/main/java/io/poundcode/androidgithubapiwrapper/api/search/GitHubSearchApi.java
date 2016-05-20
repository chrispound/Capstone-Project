package io.poundcode.androidgithubapiwrapper.api.search;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubSearchApi {

    @GET("/search/repositories?q={query}")
    List<GitHubRepository> searchRepositories(@Path("query") String query);
}
