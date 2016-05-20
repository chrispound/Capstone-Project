package io.poundcode.gitdo.repositories.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import io.poundcode.androidgithubapiwrapper.GitHubApiContext;
import io.poundcode.androidgithubapiwrapper.api.repository.GitHubRepositoryApi;
import io.poundcode.androidgithubapiwrapper.api.search.GitHubSearchApi;
import io.poundcode.androidgithubapiwrapper.api.user.GitHubUserApi;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.search.GitHubSearchRepositoryResult;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.utils.Extras;
import io.poundcode.gitdo.utils.Utils;

public class AddRepositoryActivity extends AppCompatActivity implements SearchRecyclerViewComponents.MarkRepoToAddListener {

    @Bind(R.id.repoName)
    EditText etRepoName;
    @Bind(R.id.user)
    EditText etUser;
    @Bind(R.id.search)
    FloatingActionButton btnSearch;
    @Bind(R.id.results)
    RecyclerView rvResults;
    private SearchRecyclerViewComponents mAdapter;
    Map<String, GitHubRepository> repositoriesToAdd = new HashMap<>();
    InterstitialAd mInterstitialAd;
    private static final String TAG = "AddRepositoryActivity";
    private final Callback<List<GitHubRepository>> callback = new Callback<List<GitHubRepository>>() {
        @Override
        public void onResponse(Call<List<GitHubRepository>> call, Response<List<GitHubRepository>> response) {
            if (response.isSuccessful()) {
                Log.d(TAG, "onResponseSuccess: " + response.message());
                List<GitHubRepository> repositories = response.body();
                mAdapter.setRepositories(repositories);

            } else {
                Snackbar.make(btnSearch, R.string.err_search, Snackbar.LENGTH_SHORT).show();
                Log.e(TAG, "onResponseErr: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<GitHubRepository>> call, Throwable t) {
            Log.e(TAG, "onFailure: ", t);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_repository);
        ButterKnife.bind(this);
        mAdapter = new SearchRecyclerViewComponents(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvResults.setLayoutManager(manager);
        rvResults.setAdapter(mAdapter);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                finish();
            }
        });
        requestNewInterstitial();
    }

    @OnClick(R.id.search)
    public void onClickSearch(View view) {
        if (repositoriesToAdd != null && repositoriesToAdd.size() > 0) {
            ArrayList<GitHubRepository> repositories = new ArrayList<>(repositoriesToAdd.values());
            Intent intent = new Intent();
            intent.putExtra(Extras.REPOSITORIES, repositories);
            setResult(RESULT_OK, intent);
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                finish();
            }
            return;
        }
        String user = etUser.getText().toString();
        String repo = etRepoName.getText().toString();
        if (TextUtils.isEmpty(user) && TextUtils.isEmpty(repo)) {
            etUser.setError("");
            etRepoName.setError("");
            Snackbar.make(btnSearch, R.string.err_search_nothing_entered, Snackbar.LENGTH_SHORT).show();
            return;
        }
        executeRepositorySearch(etUser.getText().toString(), etRepoName.getText().toString());
        // TODO: 5/20/2016 enable for testing
//        mAdapter.setRepositories(TestData.getGitHubRepositoryTestData());
    }


    private void executeRepositorySearch(String user, final String repo) {

        if (!Utils.isNetworkConnected(this)) {
            Snackbar.make(rvResults, R.string.err_network, Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(repo)) {
            //load specific user repo
            GitHubRepositoryApi api = GitHubApiContext.retrofit.create(GitHubRepositoryApi.class);
            Call<GitHubRepository> call = api.getRepository(user, repo);
            call.enqueue(new Callback<GitHubRepository>() {
                @Override
                public void onResponse(Call<GitHubRepository> call, Response<GitHubRepository> response) {
                    ArrayList<GitHubRepository> repository = new ArrayList<>();
                    repository.add(response.body());
                    mAdapter.setRepositories(repository);
                }

                @Override
                public void onFailure(Call<GitHubRepository> call, Throwable t) {
                    Snackbar.make(btnSearch, R.string.err_search, Snackbar.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        } else if (!TextUtils.isEmpty(user)) {
            //load all user repos
            GitHubUserApi api = GitHubApiContext.retrofit.create(GitHubUserApi.class);
            Call<List<GitHubRepository>> call = api.getUserRepositories(user);
            call.enqueue(callback);
        } else {
            //search by repo name
            GitHubSearchApi api = GitHubApiContext.retrofit.create(GitHubSearchApi.class);
            Call<GitHubSearchRepositoryResult> call = api.searchRepositories(repo);
            call.enqueue(new Callback<GitHubSearchRepositoryResult>() {
                @Override
                public void onResponse(Call<GitHubSearchRepositoryResult> call, Response<GitHubSearchRepositoryResult> response) {
                    mAdapter.setRepositories(response.body().getRepositories());
                }

                @Override
                public void onFailure(Call<GitHubSearchRepositoryResult> call, Throwable t) {
                    Snackbar.make(btnSearch, R.string.err_search, Snackbar.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void repositoryCheckChange(GitHubRepository repository, boolean checked) {
        if (checked) {
            repositoriesToAdd.put(repository.getId(), repository);
        } else {
            if (repositoriesToAdd.containsKey(repository.getId())) {
                repositoriesToAdd.remove(repository.getId());
            }
        }

        if (repositoriesToAdd.size() > 0) {
            btnSearch.setImageResource(R.drawable.ic_done);
        } else {
            btnSearch.setImageResource(R.drawable.ic_search);
        }
    }
}
