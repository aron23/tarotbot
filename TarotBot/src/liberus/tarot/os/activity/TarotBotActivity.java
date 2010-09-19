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
import android.widget.ViewFlipper;


public class TarotBotActivity extends Activity  implements OnClickListener, View.OnClickListener, OnItemSelectedListener, OnDateChangedListener {
	//private DatePicker dp;
	private WheelView statusspin;
	private Button initbutton;
	
	private Querant aq;
	private boolean male = false;
	private boolean partnered = false;
	private boolean firstpass=true;

	public static int secondSetIndex=79;
	protected RelativeLayout secondlayout;

	private static final int SWIPE_MIN_DISTANCE = 80;
	private static final int SWIPE_MAX_OFF_PATH = 180;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final int MENU_SAVE = 0;
	private static final int MENU_SHARE = 1;
	private static final int MENU_LOAD = 2;
	private static final int MENU_HELP = 3;
	private static final int MENU_BROWSE = 4;
	private static final int MENU_NAVIGATE = 5;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	public static LayoutInflater inflater;
	protected View showing;
	private TextView infotext;
	protected TextView closure;

	private SharedPreferences querantPrefs;
	private SharedPreferences readingPrefs;
	private int statusselected;

	private ArrayList<Integer> type = new ArrayList<Integer>();
	public ArrayList<Integer> flipdex = new ArrayList<Integer>();
	private ArrayList<RelativeLayout> laidout = new ArrayList<RelativeLayout>();
	private static BotaInt myInt;
	private String saveTitle;
	private EditText input;
	private boolean infoDisplayed;
	private int myRandomCard;
	private CheckBox reversalCheck;
	private boolean sharing;

	private String saveResult;
	private boolean begun = false;
	private HashMap<String,HashMap<String,String>> savedReadings = new HashMap<String,HashMap<String,String>>();
	public boolean loaded = false;
	private boolean helping;
	private Spread mySpread;
	
	String whatUrl;
	String howUrl;
	String tarotUrl;
	String updatesUrl;
	String[] helpUrls;
	Resources res;
	private String[] chaosStar;
	
	private String[] celticCross;
	
	private boolean spreading = true;
	private String[] spreadLabels;

	private String style = "";
	private WheelView spreadspin;
	public static boolean cardfortheday = false;
	private SharedPreferences.Editor readingPrefsEd;
	private boolean init = false;
	private String[] single;
	private String[] timeArrow;
	private String[] dialectic;
	private String[] pentagram;
	private boolean browsing = false;
	private AlertDialog navigator;
	private ArrayList<String> savedList = new ArrayList<String>();
	private WheelView dp_month_spin;
	private WheelView dp_day_spin;
	private WheelView dp_year_spin;
	private boolean bota;
	private ArrayList<String> sortedSaved;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setFullscreen();
		setContentView(R.layout.tarotbotstart);
		inflater = LayoutInflater.from(this);
		readingPrefs = getSharedPreferences("tarotbot.reading", 0);
		readingPrefsEd = readingPrefs.edit();
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = getGestureListener(gestureDetector);
		//initDeck();
		initSaved();
		initText();		
		initSpreadChoice();		
	}
	
	private void initSaved() {
		try{
			File f = new File(Environment.getExternalStorageDirectory()+"/tarotbot.store");
			if (!f.exists())
				return;
			FileInputStream fileIS = new FileInputStream(f);
			BufferedReader buf = new BufferedReader(new InputStreamReader(fileIS));
			String readString = new String(); 
			   //just reading each line and pass it on the debugger
			
			while((readString = buf.readLine())!= null){
				HashMap<String,String> read = new HashMap<String,String>();
				String[] saved = readString.split(":::");
			    read.put("spread", saved[0]);
			    read.put("deck", saved[1]);
			    read.put("reversals", saved[2]);
			    read.put("label", saved[3]);
			    read.put("type", saved[4]);
			    if (saved.length > 5 &! saved[5].equals("dated"))
			    	read.put("date", saved[5]);
			    else
			    	read.put("date", "0000-00-00.00");
			    if (saved.length > 6)
			    	read.put("significator", saved[6]);
			    else if (saved[4].equals("bota"))
			    	continue;
			    else
			    	read.put("significator", "0");
			    System.err.println(saved[0]);
			    System.err.println(saved[1]);
			    savedReadings.put(read.get("date")+saved[0]+saved[1],read);
			    if (!savedList.contains(read.get("date")+saved[0]+saved[1]))
			    	savedList.add(read.get("date")+saved[0]+saved[1]);
			}
		} catch (FileNotFoundException e) {
		   e.printStackTrace();
		} catch (IOException e){
		   e.printStackTrace();
		}
	}

	private void reInit() {
		setContentView(R.layout.tarotbotstart);
		flipdex = new ArrayList<Integer>();
		inflater = LayoutInflater.from(this);
		readingPrefs = getSharedPreferences("tarotbot.reading", 0);
		readingPrefsEd = readingPrefs.edit();
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = getGestureListener(gestureDetector);
		begun = false;
		browsing = false;
		loaded = false;
		Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
		myInt = new BotaInt(new RiderWaiteDeck(), aq);
		//mySpread.myDeck = myInt.myDeck;
		
		initText();		
		initSpreadChoice();	
	}

	private void initText() {
		whatUrl = getString(R.string.whatUrl);
		howUrl = getString(R.string.howUrl);
		tarotUrl = getString(R.string.tarotUrl);
		updatesUrl = getString(R.string.updatesUrl);
		helpUrls = new String[]{whatUrl,howUrl,tarotUrl,updatesUrl};
		res = getResources();
		
		single = res.getStringArray(R.array.single);
		timeArrow = res.getStringArray(R.array.timeArrow);
		dialectic = res.getStringArray(R.array.dialectic);
		pentagram = res.getStringArray(R.array.pentagram);
		chaosStar = res.getStringArray(R.array.chaosStar);
		celticCross = res.getStringArray(R.array.celticCross);
	}

	private void initSpreadChoice() {
		init = true;
		spreadspin = (WheelView) findViewById(R.id.spreadspinner);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getResources().getStringArray(R.array.spread_array));
		
		spreadspin.setAdapter(adapter);
		spreadspin.setVisibleItems(3);
		spreadspin.setCurrentItem(readingPrefs.getInt("spread", 0));
		
//		WheelView city = (WheelView) findViewById(R.id.city);
//        String cities[] = new String[] {"New York", "Washington", "Chicago",
//        		"Los Angeles", "Atlanta", "Boston", "Miami", "Orlando"};
//        city.setAdapter(new ArrayWheelAdapter<String>(cities));
//        city.setVisibleItems(7);
//        city.setCurrentItem(5);
		
		reversalCheck = (CheckBox)this.findViewById(R.id.reversalcheck);
		reversalCheck.setChecked(readingPrefs.getBoolean("reversal", false));
		((ImageView) this.findViewById(R.id.biglogo)).setBackgroundDrawable(getResources().getDrawable(R.drawable.biglogo));
		
		initbutton = (Button) this.findViewById(R.id.initspreadbutton);
		
		//spreadspin.setOnItemSelectedListener(this);
		reversalCheck.setOnClickListener(this);
		initbutton.setOnClickListener(this);
		
		init = false;
	}
	
	private void redisplaySpreadStart() {
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

	private OnTouchListener getGestureListener(final GestureDetector gd) {
		return new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gd.onTouchEvent(event)) {
					return true;
				}
				return false;
			} 
		};
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  if (spreading) {
		  redisplaySpreadStart();
		  
	  } else if (!begun) {
		  redisplayMain();
	  } else {
		  rotateDisplay();
		  if (Spread.circles.size() > 1) {
			  if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
					Toast.makeText(this, R.string.portraitnavigation, Toast.LENGTH_LONG).show();
				else
					Toast.makeText(this, R.string.landscapenavigation, Toast.LENGTH_LONG).show();
		  }  
	  }
	}

	

	private void rotateDisplay() {
		setContentView(R.layout.transition);
		
		ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);
		
		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((RelativeLayout) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		flipper.addView(activeView);

		if (BotaInt.myDeck.reversed[flipdex.get(flipper.indexOfChild(activeView))]) {	
			//Uri allTitles = Uri.parse("content://liberus.tarot.os.addon.riderwaiteprovider/cards/"+flipdex.get(flipper.indexOfChild(v)));
			//Cursor c = managedQuery(allTitles, null, null, null, "title desc");
			Bitmap bmp;
			//try {
				//bmp = BitmapFactory.decodeResource(getResources(), deckService.getCard(flipdex.get(flipper.indexOfChild(v))));
				bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(flipdex.get(secondSetIndex)));
				int w = bmp.getWidth();
				int h = bmp.getHeight();
				Matrix mtx = new Matrix();
				mtx.postRotate(180);
				Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
				BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
				divine.setImageDrawable(bmd);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	

		} else {
			//Uri allTitles = Uri.parse("content://liberus.tarot.os.addon.riderwaiteprovider/cards/"+flipdex.get(flipper.indexOfChild(v)));
		    //Cursor c = managedQuery(allTitles, null, null, null, null);
		    //c.moveToFirst();
//			try {
//				divine.setImageDrawable(getResources().getDrawable(deckService.getCard(flipdex.get(flipper.indexOfChild(v)))));
//			} catch (NotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			//divine.setImageDrawable(getResources().getDrawable(c.getInt(0)));
			divine.setImageDrawable(getResources().getDrawable(BotaInt.getCard(flipdex.get(secondSetIndex))));
		}
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		
		flipper.setAnimationCacheEnabled(false);
		flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
		flipper.setDrawingCacheEnabled(false);
		flipper.setDisplayedChild(secondSetIndex);

		
		
		
		
		
//			setContentView(R.layout.transition);
//			ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
//			laidout = new ArrayList<RelativeLayout>();
//			int counter = 0;
//			for (int index:Spread.circles) {			
//				View activeView = inflater.inflate(R.layout.reading, null);
//				
//				if (secondSetIndex > 0 && secondSetIndex < (Spread.circles.size()-1)) {
//					if (counter == (secondSetIndex-1) || counter == secondSetIndex || counter == (secondSetIndex+1)) {
//						preFlip(activeView);
//					}
//				} else if (secondSetIndex == 0 ) {
//					if (counter == (Spread.circles.size()-1) || counter == secondSetIndex || counter == (secondSetIndex+1)) {
//						preFlip(activeView);
//					}
//				} else if (secondSetIndex == 0 ) {
//					if (counter == (secondSetIndex-1) || counter == secondSetIndex || counter == 0) {
//						preFlip(activeView);
//					}
//				}
//					
//				counter++;
//				if (activeView.findViewById(R.id.secondsetlayout) != null)
//					laidout.add((RelativeLayout) activeView.findViewById(R.id.secondsetlayout));
//				//else continue;
//
//				ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
//				divine.setClickable(true);
//				divine.setOnTouchListener(gestureListener);
//				//divine.setOnClickListener(this);									
//
//				flipper.addView(activeView);
//				type.add(0);
//				flipdex.add(index);
//			}
//			
//			flipper.setClickable(true);
//			
//			flipper.setOnTouchListener(gestureListener);
//			View v = flipper.getChildAt(secondSetIndex);
//			preFlip(v);
//			
//			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//				showInfo(Configuration.ORIENTATION_LANDSCAPE);
//			
//			flipper.setAnimationCacheEnabled(false);
//			flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
//			flipper.setDrawingCacheEnabled(false);
//			flipper.setDisplayedChild(secondSetIndex);
//			flipper.forceLayout();
		}

	public void botaSpread() {
		setContentView(R.layout.botastart);		
		
		querantPrefs = getSharedPreferences("tarotbot", 0);
				
		sharing = false;
		
		init = true;
		statusspin = (WheelView) findViewById(R.id.statusspinner);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getResources().getStringArray(R.array.status_array));
		
		statusspin.setAdapter(adapter);
		statusspin.setVisibleItems(3);
		statusspin.setCurrentItem(querantPrefs.getInt("querantstatus", 0));
		
//		statusspin = (Spinner) findViewById(R.id.statusspinner);
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//				this, R.array.status_array, android.R.layout.simple_spinner_item);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		statusspin.setAdapter(adapter);
//		statusspin.setOnItemSelectedListener(this);
//		statusspin.setSelection(3);
//		statusspin.setSelection(querantPrefs.getInt("querantstatus", 0));
		
		Calendar today = Calendar.getInstance();
		
		dp_month_spin = (WheelView) findViewById(R.id.birthmonth);
		ArrayWheelAdapter<String> dpmadapter = new ArrayWheelAdapter<String>(new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
		
		dp_month_spin.setAdapter(dpmadapter);
		dp_month_spin.setVisibleItems(3);

		dp_day_spin = (WheelView) findViewById(R.id.birthday);
		
		dp_day_spin.setAdapter(new NumericWheelAdapter(1, 31));
		dp_day_spin.setVisibleItems(3);
		
		dp_year_spin = (WheelView) findViewById(R.id.birthyear);
		
		dp_year_spin.setAdapter(new NumericWheelAdapter(1910, 2010));
		dp_year_spin.setVisibleItems(3);
		
		
		 
		if (querantPrefs.contains("birthyear")) {
			dp_year_spin.setCurrentItem(querantPrefs.getInt("birthyear", today.get(Calendar.YEAR))-1910);
			dp_month_spin.setCurrentItem(querantPrefs.getInt("birthmonth", today.get(Calendar.MONTH)));
			dp_day_spin.setCurrentItem(querantPrefs.getInt("birthday", today.get(Calendar.DAY_OF_MONTH))-1);     
		}else {
			dp_month_spin.setCurrentItem(querantPrefs.getInt("birthmonth", today.get(Calendar.MONTH)));
			dp_day_spin.setCurrentItem(querantPrefs.getInt("birthday", today.get(Calendar.DAY_OF_MONTH))-1);
			dp_year_spin.setCurrentItem(querantPrefs.getInt("birthyear", today.get(Calendar.YEAR))-1910);
		}

		//changeQuerant();		
		
		if (canBeRestored()) {
			myInt = new BotaInt(new RiderWaiteDeck(), aq);
			restoreMe();
			restoreSecondStage();
		} else {
			secondSetIndex = 0;
			laidout.add((RelativeLayout) this.findViewById(R.id.mainlayout));
			myRandomCard = getRandomCard();
			((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(myRandomCard));
			
	
			initbutton = (Button) this.findViewById(R.id.initbotabutton);
			initbutton.setOnClickListener(this);
			Toast.makeText(this, R.string.questionprompt, Toast.LENGTH_LONG).show(); 
		}
	}
	
	public void seqSpread() {
		setContentView(R.layout.seqstart);		
		
		querantPrefs = getSharedPreferences("tarotbot", 0);
		
		
		sharing = false;

		reversalCheck = (CheckBox)this.findViewById(R.id.reversalcheck);
		
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			} 
		};
				
		
		if (canBeRestored()) {
			myInt = new BotaInt(new RiderWaiteDeck(), aq);
			restoreMe();
			restoreSecondStage();
		} else {
			secondSetIndex = 0;
			laidout.add((RelativeLayout) this.findViewById(R.id.mainlayout));
			myRandomCard = getRandomCard();
			((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(myRandomCard));
			
	
			initbutton = (Button) this.findViewById(R.id.initseqbutton);
			initbutton.setOnClickListener(this);
		}
	}
	
	private void restoreMe() {
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<Integer> positions = new ArrayList<Integer>();
		ArrayList<Boolean> reversals = new ArrayList<Boolean>();
		keys.addAll(readingPrefs.getAll().keySet());
		Collections.sort(keys);
		for (int i = 0; i < (keys.size()-78); i++)
			positions.add(readingPrefs.getInt(keys.get(i),-1));
		for (int i = positions.size(); i < keys.size(); i++)
			reversals.add(readingPrefs.getBoolean(keys.get(i),false));
		BotaSpread.working = positions;
		BotaInt.myDeck.reversed = reversals.toArray(new Boolean[0]);
		secondSetIndex = querantPrefs.getInt("activeCard", 0);
	}

		
	@Override

	public boolean onPrepareOptionsMenu(Menu menu) {

		menu.clear();
		
		menu.add(0, MENU_LOAD, 0, R.string.load_menu).setIcon(android.R.drawable.ic_menu_set_as);
	    menu.add(0, MENU_SAVE, 1, R.string.save_menu).setIcon(android.R.drawable.ic_menu_save);
	    menu.add(0, MENU_SHARE, 2, R.string.share_menu).setIcon(android.R.drawable.ic_menu_share);
	    menu.add(0, MENU_HELP, 3, R.string.help_menu).setIcon(android.R.drawable.ic_menu_help);
	    menu.add(0, MENU_BROWSE, 4, R.string.browse_menu).setIcon(android.R.drawable.ic_menu_gallery);
	    menu.add(0, MENU_NAVIGATE, 5, R.string.navigate_menu).setIcon(android.R.drawable.ic_menu_mapmode);
	
		if(!begun) {
			menu.findItem(MENU_SAVE).setEnabled(false);
			if (loaded || browsing) {
				menu.findItem(MENU_SHARE).setEnabled(false);
				menu.findItem(MENU_NAVIGATE).setEnabled(true);
			} else {
				menu.findItem(MENU_NAVIGATE).setEnabled(false);
				menu.findItem(MENU_SHARE).setEnabled(false);
			}
		} else {
			menu.findItem(MENU_SAVE).setEnabled(true);
			menu.findItem(MENU_SHARE).setEnabled(true);
			menu.findItem(MENU_NAVIGATE).setEnabled(true);
		}
	
		return super.onPrepareOptionsMenu(menu);

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
	
	private void navigate() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.navigate_prompt));
		builder.setView(mySpread.navigate(inflater.inflate(mySpread.getLayout(), null), this, getApplicationContext()));
		navigator = builder.create();
		navigator.show();
	}

	private void launchBrowse() {
		browsing = true;
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

	private void launchHelp() {
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

	private void displaySaved() {
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

	private String reversalsToString() {
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

	private void save(boolean share) {
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

	private void changeQuerant() {

					
			if (statusspin.getAdapter().getItem(statusspin.getCurrentItem()).toString().contains(getString(R.string.status_sm))) {
				male = true;
				partnered=false;
			} else if (statusspin.getAdapter().getItem(statusspin.getCurrentItem()).toString().contains(getString(R.string.status_pf))) {
				male=false;
				partnered = true;    	
			} else if (statusspin.getAdapter().getItem(statusspin.getCurrentItem()).toString().contains(getString(R.string.status_pm))) {
				male = true;
				partnered = true;
			} else {
				male = false;
				partnered = false;
			}

		//Toast.makeText(this, dp.getMonth(), Toast.LENGTH_SHORT).show();
		aq = new Querant(male,partnered,new GregorianCalendar(dp_year_spin.getCurrentItem()+1910,dp_month_spin.getCurrentItem(),dp_day_spin.getCurrentItem()+1));
		SharedPreferences.Editor ed = querantPrefs.edit();
		ed.putInt("birthyear", aq.birth.get(Calendar.YEAR));
		ed.putInt("birthmonth", aq.birth.get(Calendar.MONTH));
		ed.putInt("birthday", aq.birth.get(Calendar.DAY_OF_MONTH));
		ed.putInt("querantstatus", statusspin.getCurrentItem());		
		ed.commit();
	}
	private void showInfo(int type) {
		ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);

		if (type == Configuration.ORIENTATION_PORTRAIT) {
			infoDisplayed = true;
			int i = Spread.circles.get(secondSetIndex);
			String interpretation = mySpread.getInterpretation(i,getApplicationContext());
			showing = inflater.inflate(R.layout.interpretation, null);
			infotext = (TextView) showing.findViewById(R.id.interpretation);		
			if (mySpread.myLabels[secondSetIndex] != null)
				infotext.setText(Html.fromHtml("<br/><i>"+mySpread.myLabels[secondSetIndex]+"</i><br/><br/>"+interpretation));			
			else
				infotext.setText(Html.fromHtml(interpretation));
			ArrayList<View> toShow = new ArrayList<View>();
			toShow.add(showing);
			View v = flipper.getCurrentView();
			((RelativeLayout)v.findViewById(R.id.secondsetlayout)).addView(showing);
		} else if (type == Configuration.ORIENTATION_LANDSCAPE) {
			int i = Spread.circles.get(secondSetIndex);
			String interpretation = mySpread.getInterpretation(i,getApplicationContext());
			View v = flipper.getCurrentView();
			infotext = (TextView)v.findViewById(R.id.interpretation);		
			
			if (mySpread.myLabels[secondSetIndex] != null)
				infotext.setText(Html.fromHtml("<br/><i>"+mySpread.myLabels[secondSetIndex]+"</i><br/><br/>"+interpretation));
			else
				infotext.setText(Html.fromHtml(interpretation));
		}			
	}

	protected void setFullscreen() { 
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	}  

	private void incrementSecondSet(int index) {		
		if (index >= (Spread.circles.size()-1)) 
			secondSetIndex=0; 
		else 
			secondSetIndex++;
		
		ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);

		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((RelativeLayout) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		preFlip(activeView);
		flipper.addView(activeView);
		flipper.startFlipping();
		
		flipper.setInAnimation(inFromRightAnimation());
	    flipper.setOutAnimation(outToLeftAnimation());	        
	    flipper.showNext();
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
			
		
		postFlip(flipper.getChildAt(0));
		flipper.stopFlipping();
	}


	private void decrementSecondSet(int index) {		
		if (index-1 < 0) {
			secondSetIndex = (Spread.circles.size()-1);
		}  else
			secondSetIndex--;
		
		
		ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);

		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((RelativeLayout) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		preFlip(activeView);
		flipper.addView(activeView);
		flipper.startFlipping();

		if (flipper != null) {
			flipper.setInAnimation(inFromLeftAnimation());
	        flipper.setOutAnimation(outToRightAnimation());
	        flipper.showPrevious();
		}
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		
		postFlip(flipper.getChildAt(0));
		flipper.stopFlipping();
	}
	private void beginSecondStage() {
		
		firstpass=false;
		secondSetIndex=0;
		if (!reversalCheck.isChecked() &! loaded) {
			BotaInt.myDeck.reversed = BotaInt.myDeck.noreversal;
		} else if (!loaded) {
			BotaInt.myDeck.establishReversal();
		}
		mySpread.operate(getApplicationContext(), loaded);
		
		displaySecondStage(secondSetIndex);	
		if (Spread.circles.size() > 1) {
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				Toast.makeText(this, R.string.portraitnavigation, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this, R.string.landscapenavigation, Toast.LENGTH_LONG).show();
		}
	}
	
	private void restoreSecondStage() {
		mySpread.operate(getApplicationContext(), loaded);
		Toast.makeText(this, String.valueOf(secondSetIndex), 60).show();
		displaySecondStage(secondSetIndex);
	}

	private void displaySecondStage(int indexin) {
		setContentView(R.layout.transition);
		
		
		for (int index:Spread.circles) {					
			//flipper.addView(null);
			type.add(0);
			flipdex.add(index);
		}
		
		ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);
		
		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((RelativeLayout) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		flipper.addView(activeView);

		
		if (BotaInt.myDeck.reversed[flipdex.get(flipper.indexOfChild(activeView))]) {	
			//Uri allTitles = Uri.parse("content://liberus.tarot.os.addon.riderwaiteprovider/cards/"+flipdex.get(flipper.indexOfChild(v)));
			//Cursor c = managedQuery(allTitles, null, null, null, "title desc");
			Bitmap bmp;
			//try {
				//bmp = BitmapFactory.decodeResource(getResources(), deckService.getCard(flipdex.get(flipper.indexOfChild(v))));
				bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(flipdex.get(flipper.indexOfChild(activeView))));
				int w = bmp.getWidth();
				int h = bmp.getHeight();
				Matrix mtx = new Matrix();
				mtx.postRotate(180);
				
				Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
				BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
				divine.setImageDrawable(bmd);


		} else {
			divine.setImageDrawable(getResources().getDrawable(BotaInt.getCard(flipdex.get(flipper.indexOfChild(activeView)))));
		}
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		
		flipper.setAnimationCacheEnabled(false);
		flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
		flipper.setDrawingCacheEnabled(false);
		flipper.setDisplayedChild(0);
		
		
	}

	private void redisplaySecondStage(int indexin) {
		setContentView(R.layout.transition);
		 ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		
		int counter = 1;  
		for (int index:Spread.circles) {			
			View activeView = inflater.inflate(R.layout.reading, null);
			if (activeView.findViewById(R.id.secondsetlayout) != null)
				laidout.add((RelativeLayout) activeView.findViewById(R.id.secondsetlayout));
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
		//View oldV = oldFlipper.getChildAt(indexin);		
//		ImageView divine = (ImageView) v.findViewById(R.id.divine);
//		if (BotaInt.myDeck.reversed[flipdex.get(flipper.indexOfChild(v))]) {			
//				Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(flipdex.get(flipper.indexOfChild(v))));
//				int w = bmp.getWidth();
//				int h = bmp.getHeight();
//				Matrix mtx = new Matrix();
//				mtx.postRotate(180);
//				Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
//				BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
//				divine.setImageDrawable(bmd);
//
//		} else {
//			divine.setImageDrawable(getResources().getDrawable(BotaInt.getCard(flipdex.get(flipper.indexOfChild(v)))));
//		}
		
		preFlip(v);
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		
		flipper.setAnimationCacheEnabled(false);
		flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
		flipper.setDrawingCacheEnabled(false);
		flipper.setDisplayedChild(indexin);
		flipper.forceLayout();
	}
	
	private void redisplayMain() {
		View activeView = inflater.inflate(R.layout.botastart, null);
		setContentView(activeView);
		statusspin = (WheelView) findViewById(R.id.statusspinner);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getResources().getStringArray(R.array.status_array));
		
		statusspin.setAdapter(adapter);
		statusspin.setVisibleItems(3);
		statusspin.setCurrentItem(querantPrefs.getInt("querantstatus", 0));
		statusspin.forceLayout();
//		statusspin = (Spinner) findViewById(R.id.statusspinner);
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//				this, R.array.status_array, android.R.layout.simple_spinner_item);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		statusspin.setAdapter(adapter);
//		statusspin.setOnItemSelectedListener(this);
//		statusspin.setSelection(3);
//		statusspin.setSelection(querantPrefs.getInt("querantstatus", 0));

		Calendar today = Calendar.getInstance();
		
		dp_month_spin = (WheelView) findViewById(R.id.birthmonth);
		ArrayWheelAdapter<String> dpmadapter = new ArrayWheelAdapter<String>(new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});
		
		dp_month_spin.setAdapter(dpmadapter);
		dp_month_spin.setVisibleItems(3);
		dp_month_spin.forceLayout();
		
		dp_day_spin = (WheelView) findViewById(R.id.birthday);
		
		dp_day_spin.setAdapter(new NumericWheelAdapter(1, 31));
		dp_day_spin.setVisibleItems(3);
		dp_day_spin.forceLayout();
		
		dp_year_spin = (WheelView) findViewById(R.id.birthyear);
		
		dp_year_spin.setAdapter(new NumericWheelAdapter(1910, 2010));
		dp_year_spin.setVisibleItems(3);
		dp_year_spin.forceLayout();
		
		
		 
		if (querantPrefs.contains("birthyear")) {
			dp_year_spin.setCurrentItem(querantPrefs.getInt("birthyear", today.get(Calendar.YEAR))-1910);
			dp_month_spin.setCurrentItem(querantPrefs.getInt("birthmonth", today.get(Calendar.MONTH)));
			dp_day_spin.setCurrentItem(querantPrefs.getInt("birthday", today.get(Calendar.DAY_OF_MONTH))-1);     
		}else {
			dp_month_spin.setCurrentItem(querantPrefs.getInt("birthmonth", today.get(Calendar.MONTH)));
			dp_day_spin.setCurrentItem(querantPrefs.getInt("birthday", today.get(Calendar.DAY_OF_MONTH))-1);
			dp_year_spin.setCurrentItem(querantPrefs.getInt("birthyear", today.get(Calendar.YEAR))-1910);
		}
		
		
		
			secondSetIndex = 0;
			laidout.add((RelativeLayout) this.findViewById(R.id.mainlayout));			
			((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(myRandomCard));
			
	
			initbutton = (Button) this.findViewById(R.id.initbotabutton);
			initbutton.setOnClickListener(this);
		
	}
	


	private int getRandomCard() {
		return BotaInt.getCard((int)(Math.random() * 78));
	}


	public void onClick(View v) {	
		if (v.equals(this.findViewById(R.id.initbotabutton))) {
			begun = true;
			browsing = false;
			changeQuerant();
			myInt = new BotaInt(new RiderWaiteDeck(), aq);
			mySpread = new BotaSpread(myInt);
			beginSecondStage();	
		} else if (v.equals(this.findViewById(R.id.initspreadbutton))) {
			readingPrefsEd.putInt("spread", spreadspin.getCurrentItem());
			readingPrefsEd.commit();
			switch (spreadspin.getCurrentItem()) {
			case 0: {
				//seqSpread();
				spreadLabels = single;
				style = "single";
				break;
			}
			case 1: {
				//seqSpread();
				spreadLabels = timeArrow;
				style = "arrow";
				break;
			}
			case 2: {
				//seqSpread();
				spreadLabels = dialectic;
				style = "dialectic";
				break;
			}
			case 3: {
				//seqSpread();
				spreadLabels = pentagram;
				style = "pentagram";
				break;
			}
			case 4: {
				//seqSpread();
				spreadLabels = chaosStar;
				style = "chaos";
				break;
			}
			case 5: {
				//seqSpread();
				spreadLabels = celticCross;
				style = "celtic";
				break;
			}
			case 6: {
				botaSpread();		
				style = "bota";
				spreading=false;
				return;
			}
		}
			spreading=false;
			
			secondSetIndex = 0;
			
			begun = true;
			browsing = false;
			myInt = new BotaInt(new RiderWaiteDeck(), aq);
			if (style.equals("arrow"))
				mySpread = new ArrowSpread(myInt,timeArrow);
			else if (style.equals("dialectic"))
				mySpread = new DialecticSpread(myInt,dialectic);
			else if (style.equals("pentagram"))
				mySpread = new PentagramSpread(myInt,pentagram);
			else if (style.equals("chaos"))
				mySpread = new ChaosSpread(myInt,chaosStar);
			else if (style.equals("celtic"))
				mySpread = new CelticSpread(myInt,celticCross);
			else
				mySpread = new SeqSpread(myInt,spreadLabels);
			beginSecondStage();
			return;	
		} else if (v.equals(this.findViewById(R.id.reversalcheck))) {
			if (!init) {
				readingPrefsEd.putBoolean("reversal", reversalCheck.isChecked());
				readingPrefsEd.commit();
			}
		} else if (v instanceof ImageView && v.getId() > -1) {
			if (navigator != null) {
				navigator.dismiss();
				navigator = null;
			}
			if (browsing) {
				if (Interpretation.getCardIndex(v.getId()) > 0)
					secondSetIndex = Interpretation.getCardIndex(v.getId())-1;
				else
					secondSetIndex = Spread.working.size();
			} else {
				if (v.getId() == 0)
					secondSetIndex = Spread.working.size();
				else
					secondSetIndex = v.getId()-1;
					
			}
			incrementSecondSet(secondSetIndex);
			
		}
			//incrementSecondSet(secondSetIndex);
	}
	protected void redisplay() {
		infoDisplayed = false;
		ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		View v = flipper.getCurrentView();
		View toRemove = v.findViewById(R.id.readinglayout);
		if (toRemove != null) {
			RelativeLayout lay = (RelativeLayout)v.findViewById(R.id.secondsetlayout);
			lay.removeView(toRemove);
		}
		
	}

	private Animation inFromRightAnimation() {	
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromRight.setDuration(250);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}
	private Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoLeft.setDuration(250);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	private Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromLeft.setDuration(250);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}
	private Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoRight.setDuration(250);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}

	public void onClick(DialogInterface dialog, int which) {
		
		if (dialog instanceof AlertDialog) {
				switch (which) {
				case -1: { 															
					saveTitle = input.getText().toString();
					//saveTitle = saveTitle.replaceAll("\\s+", "+"); 
					HashMap<String,String> read = new HashMap<String,String>();
				    read.put("spread", spreadToString());
				    read.put("deck", deckToString());
				    read.put("reversals", reversalsToString());
				    read.put("label", saveTitle);
				    read.put("type", style);
				    if (style.equals("bota"))
				    	read.put("significator", String.valueOf(BotaSpread.getSignificator()));
				    else
				    	read.put("significator", String.valueOf(0));
//				    if (saved.length > 5)
//				    	read.put("date", saved[5]);
//				    else
				    Calendar cal = Calendar.getInstance();
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.hh:mm");
				    read.put("date", sdf.format(cal.getTime()));
				    savedReadings.put(read.get("date")+read.get("spread")+read.get("deck"),read);
				    if (!savedList.contains(read.get("date")+read.get("spread")+read.get("deck")))
				    	savedList.add(read.get("date")+read.get("spread")+read.get("deck"));
					
					try {
						Environment.getExternalStorageState();
					    File root = Environment.getExternalStorageDirectory();
					    
					    //if (root.canWrite()){
					        File gpxfile = new File(root, "tarotbot.store");
					        FileWriter gpxwriter = new FileWriter(gpxfile);
					        BufferedWriter out = new BufferedWriter(gpxwriter);
					        for (String reader: savedList) {
					        	out.write(savedReadings.get(reader).get("spread")+":::"+savedReadings.get(reader).get("deck")+":::"+savedReadings.get(reader).get("reversals")+":::"+savedReadings.get(reader).get("label")+":::"+savedReadings.get(reader).get("type")+":::"+savedReadings.get(reader).get("date")+":::"+savedReadings.get(reader).get("significator")+"\n");
					        }
					        
					        out.close();
					    //}
					} catch (IOException e) {
					    //Log.e(TAG, "Could not write file " + e.getMessage());
					}
					break; 
				}
				case -2: { 
					//saveResult = WebUtils.saveTarotBot(spreadToString(),deckToString(),reversalsToString(),saveTitle,style,getApplicationContext());
					
					HashMap<String,String> read = new HashMap<String,String>();
				    read.put("spread", spreadToString());
				    read.put("deck", deckToString());
				    read.put("reversals", reversalsToString());
				    read.put("label", saveTitle);
				    read.put("type", style);
				    Calendar cal = Calendar.getInstance();
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.hh:mm");
				    read.put("date", sdf.format(cal.getTime()));
				    if (style.equals("bota"))
				    	read.put("significator", String.valueOf(BotaSpread.getSignificator()));
				    else
				    	read.put("significator", String.valueOf(0));
				    savedReadings.put(read.get("date")+read.get("spread")+read.get("deck"),read);
				    if (!savedList.contains(read.get("date")+read.get("spread")+read.get("deck")))
				    	savedList.add(read.get("date")+read.get("spread")+read.get("deck"));
					
					try {
						Environment.getExternalStorageState();
					    File root = Environment.getExternalStorageDirectory();
					    
					    //if (root.canWrite()){
					        File gpxfile = new File(root, "tarotbot.store");
					        FileWriter gpxwriter = new FileWriter(gpxfile);
					        BufferedWriter out = new BufferedWriter(gpxwriter);
					        for (String reader: savedList) {
					        	out.write(savedReadings.get(reader).get("spread")+":::"+savedReadings.get(reader).get("deck")+":::"+savedReadings.get(reader).get("reversals")+":::"+savedReadings.get(reader).get("label")+":::"+savedReadings.get(reader).get("type")+":::"+savedReadings.get(reader).get("date")+":::"+savedReadings.get(reader).get("significator")+"\n");
					        }
					        
					        out.close();
					    //}
					} catch (IOException e) {
					    //Log.e(TAG, "Could not write file " + e.getMessage());
					}
					break; 
				}
				default: {
					if (helping) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(helpUrls[which]));
						startActivityForResult(intent,1);
						break;
					} else {
						Spread.circles = new ArrayList<Integer>();
						Spread.working = new ArrayList<Integer>();
						flipdex = new ArrayList<Integer>();
						//ArrayList<String[]> deck = WebUtils.loadTarotBotReading(getApplicationContext(),Integer.valueOf(savedReadings.get(which)[0]));
						ArrayList<String[]> deck = new ArrayList<String[]>();
				    	ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
				    	ArrayList<Integer> working = new ArrayList<Integer>();
				    	int significator = Integer.valueOf(savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("significator"));
				    	int count = 0;
				    	String[] reversed = savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("reversals").split(",");
				    	ArrayList<String> spreaded = new ArrayList<String>(Arrays.asList(savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("spread").split(",")));
				    	for(String card: savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("deck").split(",")) {
				    		String rev = "0";
				    		String spr = "0";
				    		if (reversed[count].equals("R")) {
				    			reversals.add(true);
				    			rev = "1";
				    		} else
				    			reversals.add(false);
				    		
				    		if (spreaded.contains(card)) {
				    			spr = "1";
				    			working.add(Integer.valueOf(card)-100);
				    		}
				    		
				    		
				    			
				    		
				    		deck.add(new String[] {card,String.valueOf(count+1),rev,spr});
				    		count++;
				    	}
				    	loaded=true;
				    	//BotaInt.myDeck = ;	
				    	myInt = new BotaInt(new RiderWaiteDeck(reversals.toArray(new Boolean[0])),aq);
				    	Spread.working = working;
				    	BotaInt.loaded = true;
				    	if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("bota")) {
				    		BotaInt.setMyQuerant(new Querant(significator));
				    		mySpread = new BotaSpread(myInt);				    		
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("celtic")) {
				    		mySpread = new CelticSpread(myInt,celticCross);
				    		Spread.circles = Spread.working;
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("chaos")) {
				    		Spread.circles = Spread.working;
				    		mySpread = new ChaosSpread(myInt,chaosStar);
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("single")) {
				    		mySpread = new SeqSpread(myInt,single);
				    		Spread.circles = Spread.working;
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("arrow")) {
				    		mySpread = new ArrowSpread(myInt,timeArrow);
				    		Spread.circles = Spread.working;				    		
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("dialectic")) {
				    		Spread.circles = Spread.working;
				    		mySpread = new DialecticSpread(myInt,dialectic);
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("pentagram")) {
				    		Spread.circles = Spread.working;
				    		mySpread = new PentagramSpread(myInt,pentagram);
				    	}
				    	//new BotaInt(new RiderWaiteDeck(reversals.toArray(new Boolean[0])),new Querant(significator),working);
				    	beginSecondStage();
				    	break;
					}
				}
			}
				//Toast.makeText(this, saveResult, Toast.LENGTH_oLONG);
				if (sharing) {
					saveResult = WebUtils.saveTarotBot(spreadToString(),deckToString(),reversalsToString(),saveTitle,style,getApplicationContext());
					String url = WebUtils.bitly(saveResult);
					share(getString(R.string.share_subject),WebUtils.bitly(saveResult));
				}
		} 
	}
	

	public void onItemSelected(AdapterView<?> spinnerAdapter, View spinner, int arg2,
			long arg3) {
		if (spinnerAdapter.equals(this.findViewById(R.id.statusspinner))) {//.getContentDescription().equals("querant")
			statusselected = statusspin.getCurrentItem();
			changeQuerant();
		} else if (spinnerAdapter.equals(this.findViewById(R.id.spreadspinner))) {
			if (!init && spreadspin != null) {
				readingPrefsEd.putInt("spread", spreadspin.getCurrentItem());
				readingPrefsEd.commit();
			}
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		changeQuerant();

	}
	

	protected void onStop() {
		super.onStop();
		if (!sharing) {
			
			
			Spread.circles = new ArrayList<Integer>();
			Spread.working = new ArrayList<Integer>();
				
		/*if (flipper != null) {
			for (int i = 0; i < flipper.getChildCount(); i++) {
				View v = flipper.getChildAt(i);
				v.destroyDrawingCache();
				v = null;
			}
			flipper.removeAllViews();
		}*/
		//BotaInt.nullify();
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

	
	public void preFlip(View v) {
				
				ImageView divine = (ImageView) v.findViewById(R.id.divine);
				if (BotaInt.myDeck.reversed[flipdex.get(secondSetIndex)]) {			
						Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(flipdex.get(secondSetIndex)));
						int w = bmp.getWidth();
						int h = bmp.getHeight();
						Matrix mtx = new Matrix();
						mtx.postRotate(180);
						Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
						BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
						divine.setImageDrawable(bmd);

				} else {

					divine.setImageDrawable(getResources().getDrawable(BotaInt.getCard(flipdex.get(secondSetIndex))));
				}			
				
	}

		
	public void postFlip(View v) {
		ViewFlipper flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		flipper.removeView(v);

	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return 1;
	}
	
	private boolean canBeRestored() {
		if (getLastNonConfigurationInstance()!=null) {
			return true;
		}
		return false;
	}
	

}