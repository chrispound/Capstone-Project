package io.poundcode.gitdo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.data.repositories.RepositoryContract;

public class GitDoWidgetListService extends RemoteViewsService {

    private static final String TAG = "GitDoWidgetListService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GitDoWidgetViewsFactory(this.getApplicationContext(), intent);
    }

    class GitDoWidgetViewsFactory implements RemoteViewsFactory {

        private final Context mContext;
        private final int mAppWidgetId;
        private ArrayList<GitHubRepository> mRepos = new ArrayList<>();
        private boolean isEmptyView;

        public GitDoWidgetViewsFactory(Context mContext, Intent intent) {
            this.mContext = mContext;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            Cursor cursor = mContext.getContentResolver().query(RepositoryContract.BASE_URI, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    GitHubRepository repository = new GitHubRepository();
                    repository.setName(cursor.getString(RepositoryContract.COL_NAME));
                    repository.setOpenIssuesCount(cursor.getInt(RepositoryContract.COL_ISSUE_COUNT));
                    mRepos.add(repository);
                } while (cursor.moveToNext());
            }
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            mRepos.clear();
        }

        @Override
        public int getCount() {
            Log.d(TAG, "getCount: " + mRepos.size());

            int size = mRepos.size();
            if (size == 0) {
                isEmptyView = true;
                return 1;
            }
            isEmptyView = false;
            return size;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d(TAG, "getViewAt: " +isEmptyView);
            if (isEmptyView) {
                return new RemoteViews(mContext.getPackageName(), R.layout.empty_widget);
            }

            RemoteViews rv =new RemoteViews(mContext.getPackageName(), R.layout.item_widget_repo);
            GitHubRepository repository = mRepos.get(position);
            rv.setTextViewText(R.id.repoName, repository.getName());
            rv.setTextViewText(R.id.tvOpenIssues, getString(R.string.issue_count) + repository.getOpenIssuesCount());
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
