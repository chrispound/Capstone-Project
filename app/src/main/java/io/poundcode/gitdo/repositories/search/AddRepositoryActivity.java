package io.poundcode.gitdo.repositories.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.test.TestData;
import io.poundcode.gitdo.utils.Extras;

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
        if(repositoriesToAdd != null && repositoriesToAdd.size() > 0) {
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
        // TODO: 5/19/2016 make network request
        mAdapter.setRepositories(TestData.getGitHubRepositoryTestData());
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void repositoryCheckChange(GitHubRepository repository, boolean checked) {
        if(checked) {
            repositoriesToAdd.put(repository.getId(), repository);
        } else {
            if (repositoriesToAdd.containsKey(repository.getId())) {
                repositoriesToAdd.remove(repository.getId());
            }
        }

        if(repositoriesToAdd.size() > 0) {
            btnSearch.setImageResource(R.drawable.ic_done);
        } else {
            btnSearch.setImageResource(R.drawable.ic_search);
        }
    }
}
