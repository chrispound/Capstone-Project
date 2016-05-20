package io.poundcode.gitdo.repositorydetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import io.poundcode.androidgithubapiwrapper.api.repository.GitHubRepositoryApi;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.data.analytics.AnalyticsIntentService;
import io.poundcode.gitdo.data.analytics.TrackedScreenView;
import io.poundcode.gitdo.utils.Extras;

public class RepositoryDetailsActivity extends AppCompatActivity implements TrackedScreenView {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private GitHubRepository mRepository;

    public static void startRepositoryDetailsActivityIntent(Context context, GitHubRepository repository) {
        Intent intent = new Intent(context, RepositoryDetailsActivity.class);
        intent.putExtra(Extras.REPOSITORY, repository);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        mRepository = (GitHubRepository) getIntent().getSerializableExtra(Extras.REPOSITORY);
        fireAnalytics();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DetailFragment.getInstance(mRepository, GitHubRepositoryApi.ISSUES), "Issues");
        adapter.addFragment(DetailFragment.getInstance(mRepository, GitHubRepositoryApi.PULLS), "Pull Requests");
        viewPager.setAdapter(adapter);
    }

    @Override
    public String getScreenName() {
        if (mRepository != null) {
            return mRepository.getName() + " - Details";
        }
        return "Repository Detail View";
    }

    @Override
    public void fireAnalytics() {
        startService(AnalyticsIntentService.getScreenViewIntent(this, getScreenName()));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}