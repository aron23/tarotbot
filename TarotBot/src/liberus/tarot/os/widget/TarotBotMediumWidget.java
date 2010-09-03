package liberus.tarot.os.widget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import liberus.tarot.android.R;
import liberus.tarot.android.R.id;
import liberus.tarot.android.R.layout;
import liberus.tarot.os.activity.CardForTheDayActivity;
import liberus.tarot.interpretation.BotaInt;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;

public class TarotBotMediumWidget extends AppWidgetProvider
{    
	RemoteViews remoteViews;
	AppWidgetManager appWidgetManager;
	ComponentName thisWidget;
	private int seed;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
		int[] appWidgetIds) {
		
		SharedPreferences readingPrefs = context.getSharedPreferences("tarotbot.random", 0);
		Editor readingPrefsEd = readingPrefs.edit();
		
		seed = (readingPrefs.getInt("seed", BotaInt.getRandom(context).nextInt()));
		readingPrefsEd.putInt("seed", seed);
		
		this.appWidgetManager = appWidgetManager;
		thisWidget = new ComponentName(context, TarotBotMediumWidget.class);
		for (int appWidgetId : appWidgetIds) {
	        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.individual);
	        remoteView.setImageViewResource(R.id.activecard, BotaInt.getCardForTheDay(context,seed));
	        appWidgetManager.updateAppWidget(appWidgetId, remoteView);
	        Intent defineIntent = new Intent(context,CardForTheDayActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context,0,defineIntent,0);
	        remoteView.setOnClickPendingIntent(R.id.activecard, pendingIntent);
			appWidgetManager.updateAppWidget(thisWidget, remoteView);
	    }
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// v1.5 fix that doesn't call onDelete Action
		final String action = intent.getAction();
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getExtras().getInt(
			AppWidgetManager.EXTRA_APPWIDGET_ID,
			AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
			super.onReceive(context, intent);
		} else if (AppWidgetManager.ACTION_APPWIDGET_ENABLED.equals(action)) {
			super.onReceive(context, intent);
		} else {
			super.onReceive(context, intent);
		}
	}
	
}

