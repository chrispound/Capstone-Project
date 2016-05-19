package io.poundcode.androidgithubapiwrapper.api.user;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUserApi {

    @GET("users/{owner}/repos")
    Call<List<GitHubRepository>> getUserRepositories(@Path("owner") String owner);
}
