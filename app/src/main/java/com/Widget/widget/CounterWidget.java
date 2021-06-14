package com.Widget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CounterWidgetConfigureActivity CounterWidgetConfigureActivity}
 */
public class CounterWidget extends AppWidgetProvider {

    public static String ADD = "buttonplus";
    public static String SUB = "buttonsub";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence countText = CounterWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews countview = new RemoteViews(context.getPackageName(), R.layout.counter_widget);
        countview.setTextViewText(R.id.count, countText);

        Intent add = new Intent(context, CounterWidget.class);
        add.putExtra("Number", appWidgetId);
        add.setAction(ADD);
        countview.setOnClickPendingIntent(R.id.pos, PendingIntent.getBroadcast(context,appWidgetId,add,PendingIntent.FLAG_UPDATE_CURRENT));

        Intent sub = new Intent(context, CounterWidget.class);
        sub.putExtra("Number", appWidgetId);
        sub.setAction(SUB);
        countview.setOnClickPendingIntent(R.id.neg, PendingIntent.getBroadcast(context,appWidgetId,sub,PendingIntent.FLAG_UPDATE_CURRENT));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, countview);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            CounterWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    void updateWidget(Context context){
        AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName localcomponentname = new ComponentName(context, CounterWidget.class);
        onUpdate(context,localAppWidgetManager, localAppWidgetManager.getAppWidgetIds(localcomponentname));
    }

    public void onReceive(Context context, Intent intent){
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (ADD.equals(action)){
            String plus = CounterWidgetConfigureActivity.loadTitlePref(context,intent.getIntExtra("Number",-1));
            int newValue = Integer.parseInt(plus)+1;
            CounterWidgetConfigureActivity.saveTitlePref(context,intent.getIntExtra("Number",-1),""+newValue);
            updateWidget(context);
        }
        if (SUB.equals(action)){
            String plus = CounterWidgetConfigureActivity.loadTitlePref(context,intent.getIntExtra("Number",-1));
            int newValue = Integer.parseInt(plus)-1;
            CounterWidgetConfigureActivity.saveTitlePref(context,intent.getIntExtra("Number",-1),""+newValue);
            updateWidget(context);
        }
    }
}