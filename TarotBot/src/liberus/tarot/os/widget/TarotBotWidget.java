package liberus.tarot.os.widget;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import liberus.tarot.android.R;
import liberus.tarot.android.R.id;
import liberus.tarot.android.R.layout;
import liberus.tarot.os.activity.CardForTheDayActivity;
import liberus.tarot.interpretation.BotaInt;
import liberus.utils.TarotBotManager;
import liberus.utils.WebUtils;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.RemoteViews;

public abstract class TarotBotWidget extends AppWidgetProvider
{    
	

	private class MyTime extends TimerTask {
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		ComponentName thisWidget;
		Context context;
		private int seed;
        public MyTime(Context ctx, AppWidgetManager appWidgetManager) {
        	context = ctx;
        	SharedPreferences readingPrefs = context.getSharedPreferences("tarotbot.random", 0);
            Editor readingPrefsEd = readingPrefs.edit();
            seed = (readingPrefs.getInt("seed", BotaInt.getRandom(context).nextInt()));
            readingPrefsEd.putInt("seed", seed);
            
            this.appWidgetManager = appWidgetManager;
            thisWidget = new ComponentName(context, getWidgetClass());
            //for (int appWidgetId : appWidgetIds) {
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.individual);
            remoteViews.setImageViewResource(R.id.activecard, BotaInt.getCardForTheDay(context,seed));
            //remoteViews = placeImage(BotaInt.getCardIndexForTheDay(context,BotaInt.getRandom(context).nextInt(78)), remoteViews, context, getPath());
            appWidgetManager.updateAppWidget(thisWidget, remoteViews);
            Class myActivityClass = getActivityClass();
            Intent defineIntent = new Intent(context,myActivityClass);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,0,defineIntent,0);
            remoteViews.setOnClickPendingIntent(R.id.activecard, pendingIntent);
                    appWidgetManager.updateAppWidget(thisWidget, remoteViews);    		
        }
        @Override
        public void run() {        	
    		//remoteViews = placeImage(BotaInt.getCardIndexForTheDay(context,BotaInt.getRandom(context).nextInt(78)), remoteViews, context, getPath());
        	remoteViews.setImageViewResource(R.id.activecard, BotaInt.getCardForTheDay(context,seed));
            appWidgetManager.updateAppWidget(thisWidget, remoteViews); 
        }
 }
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
		int[] appWidgetIds) {		
		Timer timer = new Timer();
	    timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 60000);
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
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
	
	public RemoteViews placeImage(int index, RemoteViews toPlace, Context con, String path) {
		Configuration conf =con.getResources().getConfiguration();
		Bitmap bmp = null;
		
		//if ((conf.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) != Configuration.SCREENLAYOUT_SIZE_LARGE) {
			
			try {
				File toRead = new File(Environment.getExternalStorageDirectory()+"/"+path);
	            if (toRead.exists() && TarotBotManager.hasEnoughMemory(32,con)) {
					FileInputStream raw = new FileInputStream(toRead);
		            ZipInputStream myZip = new ZipInputStream(raw);
		            ZipEntry myEntry;
		            
			            //BotaInt.getCard(flipdex.get(index))
			        int offset = 0;
			        byte buf[];
			            // on all the files from the zip.
			        while (null != (myEntry = myZip.getNextEntry())) {
			        	if (myEntry.getName().matches(BotaInt.getCardName(index))) {
			        		buf = new byte[(int)myEntry.getSize()];
			
			        		int off = 0;             // start writing
			        		int len = buf.length;    // number of byte to write
			        		int read = 0;            // number of read elements
			
			        		while ( (len>0) && (read = myZip.read(buf, off, len))>0 ) {
			        			off += read;
			        			len -= read;
			        		}
			        		bmp = BitmapFactory.decodeByteArray(buf, 0,buf.length);
			        		
			        		break;
			            }
		            }	
			        if (bmp == null) {
			        	toRead.delete();
			        }
	    		}      
	            
		    } catch (Exception e) {
		    	    			
		    }
		    
		    if (bmp == null) {
	        	BitmapFactory.Options options;
	        	options=new BitmapFactory.Options();
			//if (browsing || Runtime.getRuntime().maxMemory() < 20165824)// && 
				//options.inSampleSize = 2;		
			
	        	bmp = BitmapFactory.decodeResource(con.getResources(), BotaInt.getCard(index),options);
	        }
		int w = bmp.getWidth();
        int h = bmp.getHeight();
        Matrix mtx = new Matrix();
        int diff = h-w;
        if (diff < (h/4)*-1) {
        	mtx.postRotate(90);
        }
		
        bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
		
        w = bmp.getWidth();
        h = bmp.getHeight();
				
		toPlace.setImageViewBitmap(R.id.activecard, bmp);
		return toPlace;
	}
	public abstract String getPath();
	public abstract Class getWidgetClass();
	public abstract Class getActivityClass();
}

