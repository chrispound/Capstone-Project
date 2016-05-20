package io.poundcode.gitdo.repositorydetails.comments;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.GitHubApiContext;
import io.poundcode.androidgithubapiwrapper.api.repository.GitHubRepositoryApi;
import io.poundcode.androidgithubapiwrapper.api.search.GitHubSearchApi;
import io.poundcode.androidgithubapiwrapper.comment.GitHubComment;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepositoryDetail;
import io.poundcode.androidgithubapiwrapper.search.GitHubSearchRepositoryResult;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.data.analytics.AnalyticsIntentService;
import io.poundcode.gitdo.data.analytics.TrackedScreenView;
import io.poundcode.gitdo.utils.Extras;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity implements TrackedScreenView {

    GitHubRepository mRepository;
    GitHubRepositoryDetail mRepositoryDetail;
    CommentView commentView;
    private String mType;
    private static final String TAG = "CommentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommentsFragment fragment = new CommentsFragment();
        commentView = fragment;
        setContentView(R.layout.activity_comments);
        getFragmentManager()
            .beginTransaction()
            .add(R.id.container, fragment)
            .commit();
        if (getIntent() != null) {
            mRepository = (GitHubRepository) getIntent().getSerializableExtra(Extras.REPOSITORY);
            mRepositoryDetail = (GitHubRepositoryDetail) getIntent().getSerializableExtra(Extras.REPOSITORY_DETAIL);
            mType = getIntent().getStringExtra(Extras.DETAIL_TYPE);
        }
        fireAnalytics();
        loadData();
    }

    private void loadData() {
        String owner = mRepository.getOwner().getLogin();
        String repo = mRepository.getName();
        String id = mRepositoryDetail.getId();
        GitHubRepositoryApi api = GitHubApiContext.retrofit.create(GitHubRepositoryApi.class);
        Call<List<GitHubComment>> call = api.getDetailComments(owner, repo, mType, id);
        call.enqueue(new Callback<List<GitHubComment>>() {
            @Override
            public void onResponse(Call<List<GitHubComment>> call, Response<List<GitHubComment>> response) {
                commentView.updateAndDisplayComments(response.body());
            }

            @Override
            public void onFailure(Call<List<GitHubComment>> call, Throwable t) {
//                Snackbar.make(, R.string.err_search, Snackbar.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }


    @Override
    public String getScreenName() {
        if (mRepository != null && mType != null)
            return mRepository.getName() + " " + mType + " comments";
        return "comments";
    }


    @Override
    public void fireAnalytics() {
        startService(AnalyticsIntentService.getScreenViewIntent(this, getScreenName()));
    }
}
