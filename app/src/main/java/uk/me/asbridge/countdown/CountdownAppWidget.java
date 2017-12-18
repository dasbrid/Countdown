package uk.me.asbridge.countdown;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

import uk.me.asbridge.countdown.Helpers.LogHelper;
import uk.me.asbridge.countdown.Helpers.SharedPrefs;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

/**
 * Implementation of App Widget functionality.
 * Extends AppWidgetProvider. It is a bradcast receiver
 * A widget has the same runtime restrictions as a normal broadcast receiver...
 * ... it has only 5 seconds to finish its processing.
 * The update frequency for the widget is defined in
 * in the @xml/countdown_app_widget_info.xml file
 * Set to the smallest interval = 30 minutes
 */
public class CountdownAppWidget extends AppWidgetProvider {

    /**
     * Static local method specific for our widget implementation
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        LogHelper.i("Widget", "today = "+ today.toString());

        Calendar target = Calendar.getInstance();
        target.set(Calendar.YEAR, SharedPrefs.getYear(context, appWidgetId));
        target.set(Calendar.MONTH, SharedPrefs.getMonth(context, appWidgetId));
        target.set(Calendar.DAY_OF_MONTH, SharedPrefs.getDay(context, appWidgetId));
        target.set(Calendar.HOUR_OF_DAY, 0);
        target.set(Calendar.MINUTE, 0);
        target.set(Calendar.SECOND, 0);
        target.set(Calendar.MILLISECOND, 0);
        LogHelper.i("Widget", "target = "+ target.toString());
        long diffInSecs = (target.getTimeInMillis() - today.getTimeInMillis()) / 1000 ;

        long diffInDays = (target.getTimeInMillis() - today.getTimeInMillis()) / 1000 / 60 / 60 /24;

        LogHelper.i("Widget", "diff = " + Long.toString(target.getTimeInMillis()) + " - " + Long.toString(today.getTimeInMillis()));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.countdown_app_widget);
        String message = SharedPrefs.getMessageBefore(context, appWidgetId);

        if (diffInDays < 0) {
            message = SharedPrefs.getMessageAfter(context, appWidgetId);
            diffInDays = -diffInDays;
        }
        views.setTextViewText(R.id.appwidget_number, Long.toString(diffInDays) + " Days");
        views.setTextViewText(R.id.appwidget_text, message);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * Called for every update of the widget.
     * Contains the ids of appWidgetIds for which an update is needed.
     * Note that this may be all of the AppWidget instances for this provider, or just a subset of them, as stated in the method’s JavaDoc.
     * For example, if more than one widget is added to the home screen, only the last one changes (until reinstall).
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    /**
     * Called the first time an instance of your widget is added to the home screen.
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    /**
     * Called once the last instance of your widget is removed from the home screen.
     * @param context
     */
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static class UpdateWidgetService extends IntentService {
        public UpdateWidgetService() {
            // only for debug purpose
            super("UpdateWidgetService");

        }

        @Override
        protected void onHandleIntent(Intent intent) {
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(UpdateWidgetService.this);

            int incomingAppWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID,
                    INVALID_APPWIDGET_ID);

            if (incomingAppWidgetId != INVALID_APPWIDGET_ID) {
                try {
                    updateNewsAppWidget(appWidgetManager, incomingAppWidgetId,
                            intent);
                } catch (NullPointerException e) {
                }

            }

        }

        public void updateNewsAppWidget(AppWidgetManager appWidgetManager,
                                        int appWidgetId, Intent intent) {
            Log.v("String package name", this.getPackageName());
            RemoteViews views = new RemoteViews(this.getPackageName(),
                    R.layout.countdown_app_widget);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}

