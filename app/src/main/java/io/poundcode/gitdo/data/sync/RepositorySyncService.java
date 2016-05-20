package io.poundcode.gitdo.data.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class RepositorySyncService extends Service {


    private static RepositorySyncAdapter repositorySyncAdapter = null;
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
        synchronized (sSyncAdapterLock) {
            if (repositorySyncAdapter == null) {
                repositorySyncAdapter = new RepositorySyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return repositorySyncAdapter.getSyncAdapterBinder();
    }
}
