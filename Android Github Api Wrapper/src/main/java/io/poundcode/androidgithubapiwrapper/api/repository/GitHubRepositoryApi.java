package io.poundcode.androidgithubapiwrapper.api.repository;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.comment.GitHubComment;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepositoryDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubRepositoryApi {

    String PULLS = "pulls";
    String ISSUES = "issues";

    @GET("repos/{owner}/{repo}/{type}")
    Call<List<GitHubRepositoryDetail>> getRepoDetail(@Path("owner") String owner, @Path("repo") String repo,
                                                     @Path("type") String type);

    @GET("repos/{owner}/{repo}/{type}/{id}/comments")
    Call<List<GitHubComment>> getDetailComments(@Path("owner") String owner, @Path("repo") String repo,
                                                @Path("type") String type, @Path("id") String id);

    @GET("repos/{owner}/{repo}")
    Call<GitHubRepository> getRepository(@Path("owner") String owner, @Path("repo") String repoName);

}
