package liberus.tarot.os.widget;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import liberus.tarot.os.R;
import liberus.tarot.os.R.id;
import liberus.tarot.os.R.layout;
import liberus.tarot.os.activity.CardForTheDayActivity;
import liberus.tarot.interpretation.BotaInt;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.RemoteViews;

public class TarotBotMediumWidget extends AppWidgetProvider
{    
	RemoteViews remoteViews;
	AppWidgetManager appWidgetManager;
	ComponentName thisWidget;
	DateFormat format = SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM,
	Locale.getDefault());

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
		int[] appWidgetIds) {
		this.appWidgetManager = appWidgetManager;
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.individual);
		thisWidget = new ComponentName(context, TarotBotMediumWidget.class);
		
		//remoteViews.setImageViewResource(R.id.activecard, BotaInt.getCardForTheDay());
		/*if (BotaInt.randomReversed(context)) {			
			//Toast.makeText(this, "reversed", Toast.LENGTH_SHORT).show();
			Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), BotaInt.getCardForTheDay(context));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postRotate(180);
			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			remoteViews.setImageViewBitmap(R.id.activecard, rotatedBMP);
		} else*/
			remoteViews.setImageViewResource(R.id.activecard, BotaInt.getCardForTheDay(context));
		
		
//		Intent i = new Intent(context,TarotBotActivity.class); 
//        //i.setClassName("liberus.tarot.os", "liberus.tarot.os.TarotBotActivity"); 
//        PendingIntent myPI = PendingIntent.getService(context, 0, i, 0); 
//        //intent to start service 
//
//        //attach the click listener for the service start command intent 
//        remoteViews.setOnClickPendingIntent(R.id.activecard, myPI); 
		
		//Intent defineIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
		Intent defineIntent = new Intent(context,CardForTheDayActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context,0,defineIntent,0);
        remoteViews.setOnClickPendingIntent(R.id.activecard, pendingIntent);
		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
			
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
		} else {
			super.onReceive(context, intent);
		}
	}
	
}

