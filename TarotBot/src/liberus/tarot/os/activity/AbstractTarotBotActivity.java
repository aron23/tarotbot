package liberus.tarot.os.activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

import kankan.wheel.widget.ArrayWheelAdapter;
import kankan.wheel.widget.NumericWheelAdapter;
import kankan.wheel.widget.WheelView;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.deck.RiderWaiteDeck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.querant.Querant;
import liberus.tarot.spread.ArrowSpread;
import liberus.tarot.spread.BotaSpread;
import liberus.tarot.spread.BrowseSpread;
import liberus.tarot.spread.CelticSpread;
import liberus.tarot.spread.ChaosSpread;
import liberus.tarot.spread.DialecticSpread;
import liberus.tarot.spread.PentagramSpread;
import liberus.tarot.spread.SeqSpread;
import liberus.tarot.spread.Spread;
import liberus.utils.WebUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public abstract class AbstractTarotBotActivity extends Activity  implements OnClickListener, View.OnClickListener, OnItemSelectedListener, OnDateChangedListener {
	//protected DatePicker dp;
	protected WheelView statusspin;
	protected Button initbutton;
	
	protected Querant aq;
	protected boolean male = false;
	protected boolean partnered = false;
	protected boolean firstpass=true;

	public static int secondSetIndex=79;
	protected RelativeLayout secondlayout;

	protected static final int SWIPE_MIN_DISTANCE = 80;
	protected static final int SWIPE_MAX_OFF_PATH = 180;
	protected static final int SWIPE_THRESHOLD_VELOCITY = 200;
	protected static final int MENU_SAVE = 0;
	protected static final int MENU_SHARE = 1;
	protected static final int MENU_LOAD = 2;
	protected static final int MENU_HELP = 3;
	protected static final int MENU_BROWSE = 4;
	protected static final int MENU_NAVIGATE = 5;
	protected GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	public static LayoutInflater inflater;
	protected View showing;
	protected TextView infotext;
	protected TextView closure;

	protected SharedPreferences querantPrefs;
	protected SharedPreferences readingPrefs;
	protected int statusselected;

	protected ArrayList<Integer> type = new ArrayList<Integer>();
	public ArrayList<Integer> flipdex = new ArrayList<Integer>();
	protected ArrayList<ViewGroup> laidout = new ArrayList<ViewGroup>();
	protected static Interpretation myInt;
	protected String saveTitle;
	protected EditText input;
	protected boolean infoDisplayed;
	protected int myRandomCard;
	protected CheckBox reversalCheck;
	protected boolean sharing;

	protected String saveResult;
	protected boolean begun = false;
	protected HashMap<String,HashMap<String,String>> savedReadings = new HashMap<String,HashMap<String,String>>();
	public boolean loaded = false;
	protected boolean helping;
	protected Spread mySpread;
	
	String whatUrl;
	String howUrl;
	String tarotUrl;
	String updatesUrl;
	String[] helpUrls;
	Resources res;
	protected String[] chaosStar;
	
	protected String[] celticCross;
	
	protected boolean spreading = true;
	protected String[] spreadLabels;

	protected String style = "";
	protected WheelView spreadspin;
	public static boolean cardfortheday = false;
	protected SharedPreferences.Editor readingPrefsEd;
	protected boolean init = false;
	protected String[] single;
	protected String[] timeArrow;
	protected String[] dialectic;
	protected String[] pentagram;
	protected boolean browsing = false;
	protected AlertDialog navigator;
	protected ArrayList<String> savedList = new ArrayList<String>();
	protected WheelView dp_month_spin;
	protected WheelView dp_day_spin;
	protected WheelView dp_year_spin;
	protected boolean bota;
	protected ArrayList<String> sortedSaved;
	
	abstract void initSaved();

	abstract void reInit();

	abstract void initText();

	abstract void initSpreadChoice();
	
	protected void redisplaySpreadStart() {
		init = true;
		setContentView(R.layout.tarotbotstart);
		spreadspin = (WheelView) findViewById(R.id.spreadspinner);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getResources().getStringArray(R.array.spread_array));
		
		spreadspin.setAdapter(adapter);
		spreadspin.setCurrentItem(readingPrefs.getInt("spread", 0));
		spreadspin.setVisibleItems(3);
		spreadspin.forceLayout();
		reversalCheck = (CheckBox)this.findViewById(R.id.reversalcheck);
		((ImageView) this.findViewById(R.id.biglogo)).setBackgroundDrawable(getResources().getDrawable(R.drawable.biglogo));
		
		
		initbutton = (Button) this.findViewById(R.id.initspreadbutton);
		initbutton.setOnClickListener(this);
		spreadspin.forceLayout();
		init = false;
	}

	protected OnTouchListener getGestureListener(final GestureDetector gd) {
		return new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gd.onTouchEvent(event)) {
					return true;
				}
				return false;
			} 
		};
	}


	protected void rotateDisplay() {
		setContentView(R.layout.transition);
		
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);
		
		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		flipper.addView(activeView);

		if (Interpretation.myDeck.reversed[flipdex.get(flipper.indexOfChild(activeView))]) {	
			//Uri allTitles = Uri.parse("content://liberus.tarot.os.addon.riderwaiteprovider/cards/"+flipdex.get(flipper.indexOfChild(v)));
			//Cursor c = managedQuery(allTitles, null, null, null, "title desc");
			Bitmap bmp;
			//try {
				//bmp = BitmapFactory.decodeResource(getResources(), deckService.getCard(flipdex.get(flipper.indexOfChild(v))));
				bmp = BitmapFactory.decodeResource(getResources(), Interpretation.getCard(flipdex.get(secondSetIndex)));
				int w = bmp.getWidth();
				int h = bmp.getHeight();
				Matrix mtx = new Matrix();
				mtx.postRotate(180);
				Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
				BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
				divine.setImageDrawable(bmd);

		} else {
			divine.setImageDrawable(getResources().getDrawable(Interpretation.getCard(flipdex.get(secondSetIndex))));
		}
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		flipper.setDisplayedChild(secondSetIndex);
		}

		
	


	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case MENU_SAVE:
	        save(false);
	        return true;
	    case MENU_SHARE:
	    	save(true);
	        return true;
	    case MENU_LOAD:
	        displaySaved();
	        return true;
	    case MENU_HELP:
	        launchHelp();
	        return true;
	    case MENU_BROWSE:
	        launchBrowse();
	        return true;
	    case MENU_NAVIGATE:
	        navigate();
	        return true;
	    }
	    return false;
	}
	
	protected void navigate() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//builder.setTitle(getString(R.string.navigate_prompt));
		builder.setView(mySpread.navigate(inflater.inflate(mySpread.getLayout(), null), this, getApplicationContext()));
		navigator = builder.create();
		navigator.show();
	}

	protected void launchBrowse() {
		browsing = true;
		spreading = false;
		ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
    	for(int card: RiderWaiteDeck.cards) {
    		reversals.add(false);
    	}
    	loaded=true;
    	BotaInt.myDeck = new RiderWaiteDeck(reversals.toArray(new Boolean[0]));	
    	BotaInt.loaded = true;
    	
		mySpread = new BrowseSpread(myInt);
		Spread.working = new ArrayList<Integer>(Arrays.asList(RiderWaiteDeck.cards));
		Spread.circles = Spread.working;
		
		flipdex = new ArrayList<Integer>();
		beginSecondStage();
	}

	protected void launchHelp() {
		helping = true;
		String what = getString(R.string.helpwhat);
		String how = getString(R.string.helphow);
		String tarot = getString(R.string.helptarot);
		String updates = getString(R.string.helpupdate);
		String[] items = new String[]{what,how,tarot,updates};				
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.helpprompt));
		builder.setItems(items, this);
		AlertDialog alert = builder.create();
		alert.show();		
	}

	public void share(String subject,String text) {
		System.out.println(Spread.circles.size());
		sharing = true;
		 Intent intent = new Intent(Intent.ACTION_SEND);
		 intent.setType("text/plain");
		 intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		 intent.putExtra(Intent.EXTRA_TEXT, text);
		 startActivityForResult(Intent.createChooser(intent, getString (R.string.share_intent)),1);
		 System.out.println(Spread.circles.size());
		}

	protected void displaySaved() {
		browsing = false;
		//savedReadings = WebUtils.loadTarotBot(getApplicationContext());			
		ArrayList<String> readingLabels = new ArrayList<String>();
		sortedSaved = new ArrayList<String>(savedList);
		Collections.sort(sortedSaved);
		Collections.reverse(sortedSaved);
		for (String reader: sortedSaved) {
			HashMap<String,String> reading = savedReadings.get(reader);
			if (reading.get("label").length() > 0)
				readingLabels.add(reading.get("date")+": "+reading.get("label")+" - "+reading.get("type"));
			else
				readingLabels.add(reading.get("date")+": "+reading.get("type"));
		}
		String[] items = readingLabels.toArray(new String[0]);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.loadprompt));
		builder.setItems(items, this);
		AlertDialog alert = builder.create();
		alert.show();
	}

	protected String reversalsToString() {
		String toReturn = "";
		
	    for (boolean reversal: BotaInt.myDeck.reversed) {
	    	String represent;
	    	if (reversal)
	    		represent = "R";
	    	else
		        represent = "U";
		      toReturn += represent+",";
	    }
	    return toReturn;
	}
	
	public String spreadToString(){
	    String toReturn = "";
	    for (int card: Spread.working) {
	      String represent = String.valueOf(card);
	      if (represent.length() < 2)
	    	  represent = "10"+represent;
	      else
	    	  represent = "1"+represent;
	      toReturn += represent+",";
	    }
	    return toReturn;
	  }
	
	public String deckToString(){
	    String toReturn = "";
	    if (style == "bota") {
		    for (int card: BotaSpread.yod) {
		      String represent = String.valueOf(card);
		      if (represent.length() < 2)
		    	  represent = "10"+represent;
		      else
		    	  represent = "1"+represent;
		      toReturn += represent+",";
		    }
		    for (int card: BotaSpread.heh1) {
			     String represent = String.valueOf(card);
			     if (represent.length() < 2)
			    	 represent = "10"+represent;
			     else
			    	 represent = "1"+represent;
			     toReturn += represent+",";
			}
		    for (int card: BotaSpread.vau) {
			      String represent = String.valueOf(card);
			      if (represent.length() < 2)
			    	  represent = "10"+represent;
			      else
			    	  represent = "1"+represent;
			      toReturn += represent+",";
			    }
			for (int card: BotaSpread.heh2) {
				String represent = String.valueOf(card);
				if (represent.length() < 2)
					 represent = "10"+represent;
				else
					 represent = "1"+represent;
				toReturn += represent+",";
			}
	    }
		else {
			for (int card: Deck.shuffled) {
			      String represent = String.valueOf(card);
			      if (represent.length() < 2)
			    	  represent = "10"+represent;
			      else
			    	  represent = "1"+represent;
			      toReturn += represent+",";
			    }
			
		}
	    return toReturn;
	  }
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!spreading) {
				spreading=true;
				reInit();
				return true;
			}
		}
		return super.onKeyDown(keyCode,event);		
	}

	protected void save(boolean share) {
		sharing = share;
		saveTitle = "";
		saveResult = "";
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(getString(R.string.save_label));
		//alert.setMessage("Message");

		// Set an EditText view to get user input 
		input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton(getString(R.string.save_label_ok), this);
		alert.setNegativeButton(getString(R.string.save_label_no), this); 
			 
		alert.show(); 	
	}

	
	abstract void showInfo(int type);

	protected void setFullscreen() { 
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	}  

	protected void incrementSecondSet(int index) {		
		if (index >= (Spread.circles.size()-1)) 
			secondSetIndex=0; 
		else 
			secondSetIndex++;
		
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);

		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		preFlip(activeView);
		flipper.addView(activeView);
		//flipper.startFlipping();
		
		flipper.setInAnimation(inFromRightAnimation());
	    flipper.setOutAnimation(outToLeftAnimation());	        
	    flipper.showNext();
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
			
		
		
		postFlip(flipper.getChildAt(0));
		//flipper.stopFlipping();
	}


	protected void decrementSecondSet(int index) {		
		if (index-1 < 0) {
			secondSetIndex = (Spread.circles.size()-1);
		}  else
			secondSetIndex--;
		
		
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);

		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		preFlip(activeView);
		flipper.addView(activeView);
		//flipper.startFlipping();

		if (flipper != null) {
			flipper.setInAnimation(inFromLeftAnimation());
	        flipper.setOutAnimation(outToRightAnimation());
	        flipper.showPrevious();
		}
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		
		postFlip(flipper.getChildAt(0));
		//flipper.stopFlipping();
	}
	protected void beginSecondStage() {
		
		firstpass=false;
		secondSetIndex=0;
		if (!reversalCheck.isChecked() &! loaded) {
			BotaInt.myDeck.reversed = BotaInt.myDeck.noreversal;
		} else if (!loaded) {
			BotaInt.myDeck.establishReversal();
		}
		mySpread.operate(getApplicationContext(), loaded);
		
		if (style == "bota") {
			int end = Spread.circles.size();
			spreadLabels = new String[end];
			for (int i = 0; i < end; i++) {
				spreadLabels[i]="position "+i;
			}
		}
		
		displaySecondStage(secondSetIndex);	
	}
	
	protected void restoreSecondStage() {
		mySpread.operate(getApplicationContext(), loaded);
		//Toast.makeText(this, String.valueOf(secondSetIndex), 60).show();
		displaySecondStage(secondSetIndex);
	}

	protected void displaySecondStage(int indexin) {
		setContentView(R.layout.transition);
		
		
		for (int index:Spread.circles) {					
			//flipper.addView(null);
			type.add(0);
			flipdex.add(index);
		}
		
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);
		
		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		flipper.addView(activeView);
		
		divine = placeImage(flipper.indexOfChild(activeView),divine,getApplicationContext(),flipdex);
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		
		flipper.setAnimationCacheEnabled(false);
		flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
		flipper.setDrawingCacheEnabled(false);
		flipper.setDisplayedChild(0);
		
		toastSpread();
	}

	protected void redisplaySecondStage(int indexin) {
		setContentView(R.layout.transition);
		 ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		
		int counter = 1;  
		for (int index:Spread.circles) {			
			View activeView = inflater.inflate(R.layout.reading, null);
			if (activeView.findViewById(R.id.secondsetlayout) != null)
				laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
			//else continue;
			counter++;
			ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
			divine.setClickable(true);
			divine.setOnTouchListener(gestureListener);
			//divine.setOnClickListener(this);									

			flipper.addView(activeView);
			type.add(0);
			flipdex.add(index);
		}
		
		flipper.setClickable(true);
		//flipper.setOnClickListener(this);
		flipper.setOnTouchListener(gestureListener);
		View v = flipper.getChildAt(indexin);
		
		preFlip(v);
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		
		flipper.setAnimationCacheEnabled(false);
		flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
		flipper.setDrawingCacheEnabled(false);
		flipper.setDisplayedChild(indexin);
		flipper.forceLayout();
	}
	

	protected int getRandomCard() {
		return Interpretation.getCard((int)(Math.random() * 78));
	}
	
	protected void redisplay() {
		infoDisplayed = false;
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		View v = flipper.getCurrentView();
		View toRemove = v.findViewById(R.id.readinglayout);
		if (toRemove != null) {
			RelativeLayout lay = (RelativeLayout)v.findViewById(R.id.secondsetlayout);
			lay.removeView(toRemove);
		}
		
	}

	protected Animation inFromRightAnimation() {	
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromRight.setDuration(250);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}
	protected Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoLeft.setDuration(250);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	protected Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromLeft.setDuration(250);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}
	protected Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoRight.setDuration(250);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}	

	protected void onStop() {
		super.onStop();
		if (!sharing) {	
			Spread.circles = new ArrayList<Integer>();
			Spread.working = new ArrayList<Integer>();
			laidout = null;
			inflater=null;
			gestureDetector = null;
			gestureListener = null;
			//flipper = null;
			myInt = null;
			aq=null;
			System.gc();
			finish();
		}
	}

	protected void onPause() {
		super.onPause();		
	}

	
	protected void onResume() {
		super.onResume();	
		
	}
	
	class MyGestureDetector extends SimpleOnGestureListener implements OnGestureListener {

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			//try {
			if (Math.abs(e1.getY() - e2.getY()) < SWIPE_MAX_OFF_PATH) {
				// right to left swipe
				if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					/*if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE && laidout.get(secondSetIndex+1).findViewById(R.id.interpretation) != null)						
						redisplay();*/
					incrementSecondSet(secondSetIndex);
					return true;
					// left to right swipe
				}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					/*if (laidout.get(secondSetIndex+1).findViewById(R.id.interpretation) != null)						
						redisplay();*/
					decrementSecondSet(secondSetIndex);
					return true;
				}
			}

			if (Math.abs(e1.getX() - e2.getX()) < SWIPE_MAX_OFF_PATH) {
				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					if (!infoDisplayed)
						showInfo(getResources().getConfiguration().orientation);
					else
						redisplay();		
				}
				
				return true;		         
			}
			//} catch (Exception e) {
			// System.out.println(e.getMessage());
			//}
			return false;
		}

	}
	
	public ImageView placeImage(int index, ImageView toPlace, Context con, ArrayList<Integer> flipdex) {
		Bitmap bmp;
		BitmapFactory.Options options;
		options=new BitmapFactory.Options();
		//if (browsing || Runtime.getRuntime().maxMemory() < 20165824)// && 
			//options.inSampleSize = 2;
		options.inPurgeable = true;
		
		bmp = BitmapFactory.decodeResource(con.getResources(), BotaInt.getCard(flipdex.get(index)),options);
		int w = bmp.getWidth();
		int h = bmp.getHeight();
		Matrix mtx = new Matrix();
		int diff = h-w;
		if (diff < (h/4)*-1) {
			mtx.postRotate(90);
		}
		
		bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
		
		if (BotaInt.myDeck.reversed[flipdex.get(index)]) {		
			mtx = new Matrix();
			mtx.postRotate(180);
			bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
		} 
		
		BitmapDrawable bmd = new BitmapDrawable(bmp);			
		toPlace.setImageDrawable(bmd);
		return toPlace;
	}

	
	public void preFlip(View v) {
				
				ImageView divine = (ImageView) v.findViewById(R.id.divine);
				
				divine = placeImage(secondSetIndex,divine,getApplicationContext(),flipdex);
				
	}

		
	public void postFlip(View v) {
		toastSpread();
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		flipper.removeView(v);
		v.destroyDrawingCache();
		((ImageView) v.findViewById(R.id.divine)).getDrawable().setCallback(null);
		((ImageView) v.findViewById(R.id.divine)).setImageDrawable(null);
		v=null;
		System.gc();
	}
	
	protected void toastText(String text) {
		if (spreadLabels != null && spreadLabels.length >= secondSetIndex && Spread.circles.size() <78 && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && spreadLabels[secondSetIndex] != null && spreadLabels[secondSetIndex].length() > 0) {
			Toast toast = new Toast(getApplicationContext());
			View toaster = inflater.inflate(R.layout.gothic_toast,(ViewGroup) findViewById(R.id.toast_layout_root));
			toast.setView(toaster);
			((TextView)toaster.findViewById(R.id.toast_text)).setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);			
			toast.show();
		}
	}
	
	protected void toastSpread() {
		if (spreadLabels != null && spreadLabels.length >= secondSetIndex && Spread.circles.size() <78 && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && spreadLabels[secondSetIndex] != null && spreadLabels[secondSetIndex].length() > 0) {
			Toast toast = new Toast(getApplicationContext());
			View toaster = inflater.inflate(R.layout.gothic_toast,(ViewGroup) findViewById(R.id.toast_layout_root));
			toast.setView(toaster);
			((TextView)toaster.findViewById(R.id.toast_text)).setText(spreadLabels[secondSetIndex]);			
			toast.setDuration(Toast.LENGTH_SHORT);			
			toast.show();
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return 1;
	}
	
	protected boolean canBeRestored() {
		if (getLastNonConfigurationInstance()!=null) {
			return true;
		}
		return false;
	}
	

}