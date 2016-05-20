package io.poundcode.gitdo.data.analytics;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.poundcode.gitdo.GitDoApplication;
import io.poundcode.gitdo.utils.Extras;

public class AnalyticsIntentService extends IntentService {

    public static final String SCREEN_VIEW_ACTION ="io.poundcode.gitdo.SCREENVIEW";
    private static final String TAG = "AnalyticsIntentService";
    private Tracker mTracker;


    public AnalyticsIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            Log.e(TAG, "onHandleIntent: null Action");
            return;
        }
        switch (action) {
            case SCREEN_VIEW_ACTION:
                String name = intent.getStringExtra(Extras.SCREEN);
                GitDoApplication application = (GitDoApplication) getApplication();
                mTracker = application.getDefaultTracker();
                mTracker.setScreenName(name);
                mTracker.send(new HitBuilders.ScreenViewBuilder().build());
                break;
            default:
                Log.d(TAG, "onHandleIntent: Action not found");
                break;
        }

    }

    public static Intent getScreenViewIntent(Context context, String screen) {
        Intent intent = new Intent(context, AnalyticsIntentService.class);
        intent.putExtra(Extras.SCREEN, screen);
        intent.setAction(SCREEN_VIEW_ACTION);
        return intent;
    }
}
