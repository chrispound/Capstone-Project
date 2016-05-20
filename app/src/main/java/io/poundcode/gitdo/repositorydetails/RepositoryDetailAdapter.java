package io.poundcode.gitdo.repositorydetails;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepositoryDetail;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.repositorydetails.comments.CommentsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

// TODO: 5/20/2016 add admob
public class RepositoryDetailAdapter extends RecyclerView.Adapter<RepositoryDetailAdapter.RepositoryDetailViewHolder> {

    List<GitHubRepositoryDetail> data;

    @Override
    public RepositoryDetailAdapter.RepositoryDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository_detail, parent, false);
        return new RepositoryDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryDetailAdapter.RepositoryDetailViewHolder holder, int position) {
        GitHubRepositoryDetail detail = data.get(position);
        holder.title.setText(detail.getTitle());
        holder.body.setText(detail.getBody());
        holder.status.setText(detail.getState());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<GitHubRepositoryDetail> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class RepositoryDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.title)
        public TextView title;
        @Bind(R.id.body)
        public TextView body;
        @Bind(R.id.status)
        public TextView status;

        public RepositoryDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // TODO: 5/20/2016 multipane support
            Intent intent = new Intent(view.getContext(), CommentsActivity.class);
            view.getContext().startActivity(intent);
        }
    }
}
