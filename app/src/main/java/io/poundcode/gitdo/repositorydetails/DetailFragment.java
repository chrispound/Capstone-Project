package io.poundcode.gitdo.repositorydetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.GitHubApiContext;
import io.poundcode.androidgithubapiwrapper.api.repository.GitHubRepositoryApi;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepositoryDetail;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.utils.Extras;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {


    @Bind(R.id.rvData)
    RecyclerView rvData;
    private RepositoryDetailAdapter mAdapter;
    private GitHubRepository mRepository;
    private static final String TAG = "DetailFragment";
    private String mType;

    public static DetailFragment getInstance(GitHubRepository repository, String type) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.REPOSITORY, repository);
        bundle.putSerializable(Extras.DETAIL_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRepository = (GitHubRepository) getArguments().getSerializable(Extras.REPOSITORY);
            mType = getArguments().getString(Extras.DETAIL_TYPE, GitHubRepositoryApi.ISSUES);
        }
    }

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
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(), 2);
        rvData.setAdapter(mAdapter);
        rvData.setLayoutManager(manager);
        fetchData();
//        mAdapter.setData(TestData.getGitHubRepositoryIssueList());

    }

    private void fetchData() {
        GitHubRepositoryApi api = GitHubApiContext.retrofit.create(GitHubRepositoryApi.class);
        Call<List<GitHubRepositoryDetail>> call = api.getRepoDetail(mRepository.getOwner().getLogin(), mRepository.getName(), mType);
        call.enqueue(new Callback<List<GitHubRepositoryDetail>>() {
            @Override
            public void onResponse(Call<List<GitHubRepositoryDetail>> call, Response<List<GitHubRepositoryDetail>> response) {
                // TODO: 5/20/2016 empty view
                mAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<GitHubRepositoryDetail>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
