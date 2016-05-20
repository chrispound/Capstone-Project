package io.poundcode.gitdo.repositorydetails.comments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.poundcode.gitdo.R;
import io.poundcode.gitdo.test.TestData;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CommentsFragment extends Fragment {

    @Bind(R.id.rvComments)
    RecyclerView rvComments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, null, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommentsAdapter rvAdapter = new CommentsAdapter();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rvComments.setAdapter(rvAdapter);
        rvComments.setLayoutManager(manager);
        // TODO: 5/20/2016 fetch data
        rvAdapter.setComments(TestData.getGitHubCommentList());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
