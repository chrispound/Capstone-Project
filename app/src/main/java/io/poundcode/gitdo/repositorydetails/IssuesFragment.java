package io.poundcode.gitdo.repositorydetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.poundcode.gitdo.R;
import io.poundcode.gitdo.test.TestData;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IssuesFragment extends Fragment {


    @Bind(R.id.rvData)
    RecyclerView rvData;
    private RepositoryDetailAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository_detail, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new RepositoryDetailAdapter();
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        rvData.setAdapter(mAdapter);
        rvData.setLayoutManager(manager);

        //todo get real data.
        mAdapter.setData(TestData.getGitHubRepositoryIssueList());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
