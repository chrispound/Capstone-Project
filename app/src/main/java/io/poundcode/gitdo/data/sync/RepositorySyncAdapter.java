package io.poundcode.gitdo.data.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import io.poundcode.androidgithubapiwrapper.GitHubApiContext;
import io.poundcode.androidgithubapiwrapper.api.repository.GitHubRepositoryApi;
import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.androidgithubapiwrapper.user.GitHubUser;
import io.poundcode.gitdo.BuildConfig;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.data.repositories.RepositoryContract;
import io.poundcode.gitdo.test.TestData;
import io.poundcode.gitdo.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepositorySyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "RepositorySyncAdapter";
    private static final long SYNC_INTERVAL = 60 * 60;
    private static final long SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private final ContentResolver mContentResolver;

    public RepositorySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        if (Utils.isNetworkConnected(getContext())) {
            fetchData();
        }
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    public static void configurePeriodicSync(Context context, long syncInterval) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                syncPeriodic(syncInterval, SYNC_FLEXTIME).
                setSyncAdapter(account, authority).
                setExtras(new Bundle()).build();
            if (account != null)
                ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                authority, new Bundle(), syncInterval);
        }
    }

    private static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
            (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
            context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        if (newAccount == null) {
            Log.e(TAG, "getSyncAccount: account was null");
            return null;
        }

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, null, null)) {
                Log.e(TAG, "getSyncAccount: could not add account");
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            ContentResolver.setIsSyncable(newAccount, context.getString(R.string.content_authority), 1);
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        configurePeriodicSync(context, SYNC_INTERVAL);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void syncImmediately(Context context) {
        Account account = getSyncAccount(context);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(account,
            context.getString(R.string.content_authority), bundle);
    }

    private void fetchData() {
        // TODO: 5/20/2016 fetch data
        Log.d(TAG, "fetchData: ");
        //get current repos.
        Uri content = RepositoryContract.BASE_URI;

        Cursor cursor = getContext().getContentResolver().query(content, null, null, null, null);
        if (cursor == null) {
            return;
        }
        cursor.moveToFirst();
        List<GitHubRepository> olderRepositoryData = new ArrayList<>(cursor.getCount());
        while (!cursor.isAfterLast()) {
            //get all saved data
            GitHubRepository repostiroy = new GitHubRepository();
            repostiroy.setId(cursor.getString(RepositoryContract.COL_REPO_ID));
            repostiroy.setName(cursor.getString(RepositoryContract.COL_NAME));
            repostiroy.setDescription(cursor.getString(RepositoryContract.COL_DESCRIPTION));
            repostiroy.setOpenIssuesCount(cursor.getInt(RepositoryContract.COL_ISSUE_COUNT));
            GitHubUser gitHubUser = new GitHubUser(cursor.getString(RepositoryContract.COL_USER));
            repostiroy.setOwner(gitHubUser);
            olderRepositoryData.add(repostiroy);
            cursor.moveToNext();
        }
        //fetch updated data
        final List<GitHubRepository> freshRepositoryData = new ArrayList<>(olderRepositoryData.size());
        final int count = olderRepositoryData.size();
        final CountDownLatch latch = new CountDownLatch(count);
        for (GitHubRepository repository : olderRepositoryData) {
            GitHubRepositoryApi api = GitHubApiContext.retrofit.create(GitHubRepositoryApi.class);
            Call<GitHubRepository> call = api.getRepository(repository.getOwner().getLogin(), repository.getName());
            call.enqueue(new Callback<GitHubRepository>() {
                @Override
                public void onResponse(Call<GitHubRepository> call, Response<GitHubRepository> response) {
                    freshRepositoryData.add(response.body());
                    latch.countDown();
                    if (latch.getCount() == 0) {
                        saveAndUpdateUi(freshRepositoryData);
                    }
                }

                @Override
                public void onFailure(Call<GitHubRepository> call, Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });

        }
        cursor.close();
    }

    private void saveAndUpdateUi(List<GitHubRepository> repositoryList) {
        if ((repositoryList == null || repositoryList.size() == 0) && BuildConfig.DEBUG) {
            Log.d(TAG, "saveAndUpdateUi: testData");
            saveAndUpdateUi(TestData.getGitHubRepositoryTestData());
        }
        if (repositoryList == null) {
            Log.e(TAG, "saveAndUpdateUi: null list");
            repositoryList = new ArrayList<>();
        }

        Vector<ContentValues> values = new Vector<>(repositoryList.size());
        for (GitHubRepository repository : repositoryList) {
            ContentValues contentValues = RepositoryContract.getContentValueForRepository(repository);
            values.add(contentValues);
        }
        ContentValues[] dataToInsert = new ContentValues[values.size()];
        values.toArray(dataToInsert);
        int inserted = getContext().getContentResolver().bulkInsert(RepositoryContract.BASE_URI, dataToInsert);
        Log.d(TAG, "saveAndUpdateUi: " + inserted);
    }
}
