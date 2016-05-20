package io.poundcode.gitdo.repositories.list;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import io.poundcode.androidgithubapiwrapper.GitHubApiContext;
import io.poundcode.androidgithubapiwrapper.api.user.GitHubUserApi;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.data.repositories.RepositoryContract;
import io.poundcode.gitdo.repositories.search.AddRepositoryActivity;
import io.poundcode.gitdo.repositorydetails.RepositoryDetailsActivity;
import io.poundcode.gitdo.test.TestData;
import io.poundcode.gitdo.utils.Extras;

import retrofit2.Call;

public class RepositoriesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SEARCH_CODE = 233;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.repositories)
    RecyclerView repositories;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private static final String TAG = "RepositoriesActivity";
    private RepositoriesAdapter mAdapter;
    private Cursor adapter;
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mAdapter = new RepositoriesAdapter();
        repositories.setLayoutManager(manager);
        repositories.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        adapter = getContentResolver().query(RepositoryContract.BASE_URI,
            null, null, null, null, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //repos were added reload data
            ArrayList<GitHubRepository> repositoryList = (ArrayList<GitHubRepository>) data.getSerializableExtra(Extras.REPOSITORIES);
            for (GitHubRepository repository : repositoryList) {
                ContentValues value = RepositoryContract.getContentValueForRepository(repository);
                getContentResolver().insert(RepositoryContract.BASE_URI, value);
            }
            getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @OnClick(R.id.fab)
    public void onClickFab(View view) {
        Intent intent = new Intent(this, AddRepositoryActivity.class);
        startActivityForResult(intent, SEARCH_CODE);
    }

    private void executeRepositorySearch(String user, final String repo) {

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(repo)) {
            //load specific user repo
        } else if (!TextUtils.isEmpty(user)) {
            //load all user repos
            GitHubUserApi api = GitHubApiContext.retrofit.create(GitHubUserApi.class);
            Call<List<GitHubRepository>> call = api.getUserRepositories(user);
            mAdapter.setRepositoryList(TestData.getGitHubRepositoryTestData());
//            call.enqueue(new Callback<List<GitHubRepository>>() {
//                @Override
//                public void onResponse(Call<List<GitHubRepository>> call, Response<List<GitHubRepository>> response) {
//                    if(response.isSuccessful()) {
//                        Log.d(TAG, "onResponseSuccess: " + response.message());
//                        List<GitHubRepository> repositories = response.body();
//                        mAdapter.setRepositoryList(repositories);
//                    } else  {
//                        Log.e(TAG, "onResponseErr: "+ response.message() );
//                    }
//                }

//                @Override
//                public void onFailure(Call<List<GitHubRepository>> call, Throwable t) {
//                    Log.e(TAG, "onFailure: ", t);
//                }
//            });
        } else {
            //search by repo name
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri content = RepositoryContract.BASE_URI;
        return new CursorLoader(this, content, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null) {
            Log.e(TAG, "onLoadFinished: cursor was null");
            return;
        }
        cursor.moveToFirst();
        List<GitHubRepository> repositoryList = new ArrayList<>(cursor.getCount());
        while (!cursor.isAfterLast()) {
            GitHubRepository repostiroy = new GitHubRepository();
            repostiroy.setId(cursor.getString(1));
            repostiroy.setName(cursor.getString(2));
            repostiroy.setDescription(cursor.getString(3));
            repostiroy.setId(cursor.getString(4));
            repositoryList.add(repostiroy);
            cursor.moveToNext();
        }

        mAdapter.setRepositoryList(repositoryList);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class RepositoriesAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {
        List<GitHubRepository> repositoryList = new ArrayList<>();

        @Override
        public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
            return new RepositoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RepositoryViewHolder holder, int position) {
            GitHubRepository repository = repositoryList.get(position);
            holder.tvRepoName.setText(repository.getName());
            if (TextUtils.isEmpty(repository.getDescription())) {
                holder.tvRepoDescription.setVisibility(View.GONE);
            }
            holder.tvRepoDescription.setText(repository.getDescription());
            // TODO: 5/18/2016 replace with R.string
            holder.tvOpenIssues.setText("Open Issues:" + String.valueOf(repository.getOpenIssuesCount()));
        }

        @Override
        public int getItemCount() {
            // TODO: 5/17/2016 replace with real data
            return repositoryList.size();
        }

        public void setRepositoryList(List<GitHubRepository> repositoryList) {
            this.repositoryList = repositoryList;
            notifyDataSetChanged();
        }
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.tvRepoName)
        TextView tvRepoName;
        @Bind(R.id.tvRepoDescription)
        TextView tvRepoDescription;
        @Bind(R.id.tvOpenPullRequests)
        TextView tvOpenPullRequests;
        @Bind(R.id.tvOpenIssues)
        TextView tvOpenIssues;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RepositoriesActivity.this, RepositoryDetailsActivity.class);
            RepositoriesActivity.this.startActivity(intent);
        }
    }
}
