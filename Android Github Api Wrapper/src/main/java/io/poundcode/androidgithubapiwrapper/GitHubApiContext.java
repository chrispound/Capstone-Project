package io.poundcode.androidgithubapiwrapper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubApiContext {

    private static final String baseUrl = "https://api.github.com/";

    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
}
