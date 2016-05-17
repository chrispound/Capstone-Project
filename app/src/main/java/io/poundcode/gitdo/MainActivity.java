package io.poundcode.gitdo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.gitdo.test.TestData;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.repositories)
    RecyclerView repositories;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
            });
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        RepositoriesAdapter adapter = new RepositoriesAdapter();
        repositories.setLayoutManager(manager);
        repositories.setAdapter(adapter);
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

    private class RepositoriesAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {
        List<GitHubRepository> repositoryList = TestData.getGitHubRepositoryTestData();


        @Override
        public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.item_repo, null);
            return new RepositoryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RepositoryViewHolder holder, int position) {
            GitHubRepository repository = repositoryList.get(position);
            holder.tvRepoName.setText(repository.getName());
            holder.tvRepoDescription.setText(repository.getDescription());
            holder.tvOpenIssues.setText(String.valueOf(repository.getOpenIssuesCount()));
        }

        @Override
        public int getItemCount() {
            // TODO: 5/17/2016 replace with real data
            return repositoryList.size();
        }
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {

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
        }
    }
}
