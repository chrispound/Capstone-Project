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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Vector;

import io.poundcode.androidgithubapiwrapper.repository.GitHubRepository;
import io.poundcode.gitdo.BuildConfig;
import io.poundcode.gitdo.R;
import io.poundcode.gitdo.data.repositories.RepositoryContract;
import io.poundcode.gitdo.test.TestData;
import io.poundcode.gitdo.utils.Utils;


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

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
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
        saveAndUpdateUi(null);
    }

    private void saveAndUpdateUi(List<GitHubRepository> repositoryList) {
        if ((repositoryList == null || repositoryList.size() == 0) && BuildConfig.DEBUG) {
            Log.d(TAG, "saveAndUpdateUi: testData");
            saveAndUpdateUi(TestData.getGitHubRepositoryTestData());
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
