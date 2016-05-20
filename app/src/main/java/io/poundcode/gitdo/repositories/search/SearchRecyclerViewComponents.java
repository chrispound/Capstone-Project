package io.poundcode.gitdo.repositories.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.gitdo.R;

public class SearchRecyclerViewComponents extends RecyclerView.Adapter<SearchRecyclerViewComponents.SearchViewHolder> {

    List<GitHubRepository> repositories = new ArrayList<>();
    Map<String, GitHubRepository> repositoriesToAdd = new HashMap<>();
    MarkRepoToAddListener listener;

    public SearchRecyclerViewComponents(MarkRepoToAddListener listener) {
        this.listener = listener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View searchView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_repository_search_result, parent, false);
        return new SearchViewHolder(searchView, listener);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        GitHubRepository repository = repositories.get(position);
        holder.repoName.setText(repository.getName());
        holder.user.setText(repository.getOwner().getLogin());
        if (repositoriesToAdd.containsKey(repository.getId())) {
            holder.add.setChecked(true);
        } else {
            holder.add.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void setRepositories(List<GitHubRepository> repositories) {
        this.repositories = repositories;
        notifyDataSetChanged();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements CheckBox.OnCheckedChangeListener {

        @Bind(R.id.add)
        CheckBox add;
        @Bind(R.id.repoName)
        TextView repoName;
        @Bind(R.id.user)
        TextView user;
        MarkRepoToAddListener mListener;


        public SearchViewHolder(View itemView, MarkRepoToAddListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            add.setOnCheckedChangeListener(this);
            mListener = listener;
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
            GitHubRepository repository = repositories.get(getAdapterPosition());
            mListener.repositoryCheckChange(repository, checked);
            if (checked) {
                repositoriesToAdd.put(repository.getId(), repository);
            } else {
                if (repositoriesToAdd.containsKey(repository.getId())) {
                    repositoriesToAdd.remove(repository.getId());
                }
            }
        }

    }

    interface MarkRepoToAddListener {
        void repositoryCheckChange(GitHubRepository repository, boolean checked);
    }

}
