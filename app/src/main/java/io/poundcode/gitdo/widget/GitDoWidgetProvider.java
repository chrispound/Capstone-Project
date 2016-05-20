package io.poundcode.gitdo.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import io.poundcode.gitdo.Constants;
import io.poundcode.gitdo.R;

public class GitDoWidgetProvider extends AppWidgetProvider {

    public GitDoWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction() != null) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context.getPackageName(), GitDoWidgetListService.class.getName()));
            if (intent.getAction().equalsIgnoreCase(Constants.SYNC_FINISHED)) {
                for (int i = 0; i < appWidgetIds.length; i++) {
                    Intent collectionService = new Intent(context, GitDoWidgetListService.class);
                    collectionService.setData(Uri.parse(collectionService.toUri(Intent.URI_INTENT_SCHEME)));
                    collectionService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
                    RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.appwidget);
                    rv.setRemoteAdapter(R.id.repositories, collectionService);
                    appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
                }
            }
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //update each widget user has created
        for (int appWidgetId : appWidgetIds) {
            Intent collectionService = new Intent(context, GitDoWidgetListService.class);
            collectionService.setData(Uri.parse(collectionService.toUri(Intent.URI_INTENT_SCHEME)));
            collectionService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            rv.setRemoteAdapter(R.id.repositories, collectionService);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }
}
