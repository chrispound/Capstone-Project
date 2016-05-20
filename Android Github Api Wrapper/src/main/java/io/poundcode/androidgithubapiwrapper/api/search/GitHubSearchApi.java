package io.poundcode.androidgithubapiwrapper.api.search;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.search.GitHubSearchRepositoryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubSearchApi {

    @GET("/search/repositories")
    Call<GitHubSearchRepositoryResult> searchRepositories(@Query("q") String query);
}
