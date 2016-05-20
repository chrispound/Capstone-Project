package io.poundcode.gitdo.repositorydetails.comments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.poundcode.androidgithubapiwrapper.comment.GitHubComment;
import io.poundcode.gitdo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    List<GitHubComment> comments = new ArrayList<>();

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        GitHubComment comment = comments.get(position);
        holder.user.setText(comment.getUser().getLogin());
        holder.body.setText(comment.getBody());

        // TODO: 5/20/2016  Picasso to load avatar
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void setComments(List<GitHubComment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.user)
        TextView user;
        @Bind(R.id.body)
        TextView body;
        @Bind(R.id.avatar)
        ImageView avatar;

        public CommentsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
