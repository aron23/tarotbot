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
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import liberus.tarot.deck.Deck;
import liberus.tarot.deck.FullTarotDeck;
import liberus.tarot.deck.TarotTrumpDeck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.android.noads.R;
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
import liberus.tarot.spread.gothic.GothicSpread;
import liberus.utils.EfficientAdapter;
import liberus.utils.MyProgressDialog;
import liberus.utils.TarotBotManager;
import liberus.utils.WebUtils;
import liberus.utils.ZipUtils;
import liberus.utils.color.ColorDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Layout;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;


public abstract class AbstractTarotBotActivity extends Activity implements OnItemClickListener, OnClickListener, View.OnClickListener, OnItemSelectedListener, OnDateChangedListener, OnKeyListener {
	//protected DatePicker dp;
	protected Spinner statusspin;
	protected Button initbutton;
	protected ProgressDialog downloadProgress;
	protected Querant aq;
	protected boolean male = false;
	protected boolean partnered = false;
	protected boolean firstpass=true;
	protected MediaPlayer mp;
	public int secondSetIndex=79;
	protected RelativeLayout secondlayout;

	public static final int SWIPE_MIN_DISTANCE = 80;
	public static final int SWIPE_MAX_OFF_PATH = 180;
	public static final int SWIPE_THRESHOLD_VELOCITY = 200;
	protected static final int MENU_SAVE = 0;
	protected static final int MENU_SHARE = 1;
	protected static final int MENU_LOAD = 2;
	protected static final int MENU_HELP = 3;
	protected static final int MENU_BROWSE = 4;
	protected static final int MENU_NAVIGATE = 5;
	protected GestureDetector gestureDetector;
	protected View.OnTouchListener gestureListener;
	public LayoutInflater inflater;
	protected View showing;
	protected TextView infotext;
	protected TextView closure;
	
	public boolean muted = false;

	protected SharedPreferences querantPrefs;
	protected SharedPreferences readingPrefs;
	public SharedPreferences displayPrefs;
	protected SharedPreferences deckPrefs;
	protected int statusselected;

	protected ArrayList<Integer> type = new ArrayList<Integer>();
	public ArrayList<Integer> flipdex = new ArrayList<Integer>();
	protected ArrayList<ViewGroup> laidout = new ArrayList<ViewGroup>();
	protected static Interpretation myInt;
	protected String saveTitle;
	protected EditText input;
	public boolean infoDisplayed;
	protected int myRandomCard;
	protected ToggleButton muteToggle;
	protected ToggleButton reverseToggle;
	protected boolean sharing;
	protected ToggleButton trumpToggle;
	protected ToggleButton deckToggle;
	protected Editor displayPrefsEd;

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
	
	protected boolean spreading = false;
	protected String[] spreadLabels;

	protected String style = "";
	
	public static boolean cardfortheday = false;
	protected SharedPreferences.Editor readingPrefsEd;
	protected boolean init = false;
	protected String[] single;
	protected String[] timeArrow;
	protected String[] dialectic;
	protected String[] pentagram;
	protected boolean browsing = false;
	protected Dialog navigator;
	protected ArrayList<String> savedList = new ArrayList<String>();

	protected boolean bota;
	protected ArrayList<String> sortedSaved;
	
	public String state = "";
	public String priorstate = "";
	
	protected static String[] mainmenu;
	protected static String[] secondmenu;
	protected String[] spreadmenu;
	protected ListView myMenuList;
	protected DatePicker dp;
	protected boolean isBig=false;
	
	private static final int HIGHRES=32;
	private static final int MIDRES=28;

	public abstract long getHiResZipSize();
	public abstract String getMyType();
	public abstract String getMyFolder();
	public abstract Spread getMySeqSpread(Interpretation myInt, String[] labels, boolean cardOfTheDay,boolean trumpsOnly);
	public abstract Spread getMyBrowseSpread(Interpretation in, boolean trumps);
	abstract void reInit();

	private String getPath() {
		
		return WebUtils.md5(getMyFolder())+"/"+WebUtils.md5(getMyType());
	}
	protected void reInitSpread(int spreadlayout) {
		begun = false;
		browsing = false;
		loaded = false;
		spreading = true;
		Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
		if (readingPrefs.getBoolean("trumps.only", false) || isTrumpsOnly())
			myInt = new BotaInt(new TarotTrumpDeck(), aq);
		else
			myInt = new BotaInt(new FullTarotDeck(), aq);
		state = "spreadmenu";
		establishMenu(spreadlayout);
		
		myMenuList = (ListView) this.findViewById(R.id.spreadmenulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,spreadmenu,R.layout.listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("spreadmenu");
//		reverseToggle = (ToggleButton) this.findViewById(R.id.reversal_button);
//		reverseToggle.setChecked(readingPrefs.getBoolean("reversal", false));
//		reverseToggle.setClickable(true);
//		reverseToggle.setOnClickListener(this);
	}
	
	public boolean isTrumpsOnly() {
		// TODO Auto-generated method stub
		return false;
	}
	protected void initSaved(String saveas) {
		try{
			File f = new File(Environment.getExternalStorageDirectory()+"/"+saveas);
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
		} catch (Exception e){
		   e.printStackTrace();
		}
	}
	
	protected void initHighRes() {
		if (!TarotBotManager.hasEnoughMemory(32,getApplicationContext()))
			return;
		File d = new File(Environment.getExternalStorageDirectory()+"/"+WebUtils.md5(getMyFolder()));
		if (!d.exists())
			d.mkdir();
		final File f = new File(Environment.getExternalStorageDirectory()+"/"+WebUtils.md5(getMyFolder())+"/"+WebUtils.md5(getMyType()));
		final Context c = this.getApplicationContext();
		if ((!f.exists() || !isHighResComplete(f))) {
			Looper.prepare();
			WebUtils.Download("https://s3.amazonaws.com/tarotbot-high-resolution/"+getMyType()+".zip", f.getPath(),c,downloadProgress);			
			ZipUtils.unzipArchive(f, f.getPath());
			f.delete();
		} else if (!new File(f.getPath()+".ex").exists()) {
			ZipUtils.unzipArchive(f, f.getPath());
			f.delete();
		}
	}
	
	
	
	private boolean isHighResComplete(File f) {
		long length = f.length();
        if (getHiResZipSize()  > length)
        	return false;
        return true;
	}

	abstract void initText();
	
	protected void redisplaySpreadStart() {
		init = true;
		setContentView(R.layout.tarotbotstart);

		//reversalCheck = (CheckBox)this.findViewById(R.id.reversalcheck);
		((ImageView) this.findViewById(R.id.biglogo)).setBackgroundDrawable(getResources().getDrawable(R.drawable.biglogo));
		
		
		initbutton = (Button) this.findViewById(R.id.initspreadbutton);
		initbutton.setOnClickListener(this);

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
		ImageView menu = (ImageView) activeView.findViewById(R.id.menukey);
		menu.setClickable(true);
		menu.setOnClickListener(this);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ImageView book = (ImageView) activeView.findViewById(R.id.interpretationkey);
			book.setClickable(true);
			book.setOnClickListener(this);
		}

		ImageView spread = (ImageView) activeView.findViewById(R.id.spreadkey);
		if (Spread.circles.size() > 1) {
			spread.setClickable(true);
			spread.setOnClickListener(this);
		} else {
			spread.setAlpha(0);
		}
		
		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		flipper.addView(activeView);

		divine = placeImage(secondSetIndex,divine,getApplicationContext(),flipdex);
		
		
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		flipper.setDisplayedChild(secondSetIndex);
		}
	
	public void navigate() {
		preNavigate();
		priorstate = state;
		state = "navigate";
		
		//new MyProgressDialog(this);
		//MyProgressDialog.show(this,null,null,true,false,null);
		View v = mySpread.navigate(inflater.inflate(mySpread.getLayout(), null), this, getApplicationContext());
		setBackground(v);
		setContentView(v);
		//MyProgressDialog.dismissed();
	}

	protected abstract void launchBrowse();

	
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
		
		state = "spreadmenu";
		setContentView(R.layout.mainmenu);
		
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,items,R.layout.load_listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("loadmenu");
		
	}

	
	protected String reversalsToString() {
		String toReturn = "";
		
	    for (boolean reversal: myInt.myDeck.reversed) {
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
			for (int card: Deck.cards) {
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

	
	public abstract void showInfo(int type);

	protected void setFullscreen() {
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); 
	}  

	public void incrementSecondSet(int index) {		
		if (index >= (Spread.circles.size()-1)) 
			secondSetIndex=0; 
		else 
			secondSetIndex++;
		
		if (secondSetIndex >= Spread.circles.size())
			secondSetIndex=0;
		
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);

		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
		//else continue;
		ImageView menu = (ImageView) activeView.findViewById(R.id.menukey);
		menu.setClickable(true);
		menu.setOnClickListener(this);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ImageView book = (ImageView) activeView.findViewById(R.id.interpretationkey);
			book.setClickable(true);
			book.setOnClickListener(this);
		}


		ImageView spread = (ImageView) activeView.findViewById(R.id.spreadkey);
		if (Spread.circles.size() > 1) {
			spread.setClickable(true);
			spread.setOnClickListener(this);
		} else {
			spread.setAlpha(0);
		}
		
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


	public void decrementSecondSet(int index) {		
		if (index-1 < 0) {
			secondSetIndex = (Spread.circles.size()-1);
		}  else
			secondSetIndex--;
		
		while (secondSetIndex >= Spread.circles.size())
			secondSetIndex--;
		
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);

		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
		//else continue;
		ImageView menu = (ImageView) activeView.findViewById(R.id.menukey);
		menu.setClickable(true);
		menu.setOnClickListener(this);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ImageView book = (ImageView) activeView.findViewById(R.id.interpretationkey);
			book.setClickable(true);
			book.setOnClickListener(this);
		}
		

		ImageView spread = (ImageView) activeView.findViewById(R.id.spreadkey);
		if (Spread.circles.size() > 1) {
			spread.setClickable(true);
			spread.setOnClickListener(this);
		} else {
			spread.setAlpha(0);
		}
		
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
		type = new ArrayList<Integer>();
		flipdex = new ArrayList<Integer>();
		if (!state.equals("loaded")) {
			myInt.myDeck.establishReversal();
			mySpread.myDeck.reversed = myInt.myDeck.noreversal;
		}
		if (browsing || style.equals("single") || state.equals("loaded")) {}
		else if (!readingPrefs.getBoolean("reversal", false) &! loaded) {
			mySpread.myDeck.reversed = myInt.myDeck.noreversal;
		} else if (!loaded) {
			mySpread.myDeck.reversed = Deck.establishReversal(mySpread.myDeck);
		}
		mySpread.operate(getApplicationContext(), loaded);
		
		if (style == "bota") {
			int end = Spread.circles.size();
			spreadLabels = new String[end];
			for (int i = 0; i < end; i++) {
				spreadLabels[i]="position "+i;
			}
		}
		if (!browsing || style.equals("single"))
			displaySecondStage(secondSetIndex);
		else
			navigate();
	}
	
	protected void restoreSecondStage() {
		mySpread.operate(getApplicationContext(), loaded);
		//Toast.makeText(this, String.valueOf(secondSetIndex), 60).show();
		displaySecondStage(secondSetIndex);
	}
	


	protected void displaySecondStage(int indexin) {
		
		setContentView(R.layout.transition);
		
		if (mySpread != null && mySpread.myview != null)
			mySpread.clearView(mySpread.myview);
		
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		for (int i = 0; i < flipper.getChildCount(); i++) {
			View v = flipper.getChildAt(i);
			flipper.removeView(v);
			v.destroyDrawingCache();
			ImageView im = ((ImageView) v.findViewById(R.id.divine));
			if (im.getDrawable() instanceof BitmapDrawable)
				((BitmapDrawable)im.getDrawable()).getBitmap().recycle();
			im.getDrawable().setCallback(null);
			im.setImageDrawable(null);
			im.getResources().flushLayoutCache();
			im.destroyDrawingCache();
			im = null;
			v=null;
		}
		System.gc();
		for (int index:Spread.circles) {					
			//flipper.addView(null);
			type.add(0);
			flipdex.add(index);
		}
				
		flipper.setClickable(true);
		flipper.setOnTouchListener(gestureListener);
		
		View activeView = inflater.inflate(R.layout.reading, null);
		if (activeView.findViewById(R.id.secondsetlayout) != null)
			laidout.add((ViewGroup) activeView.findViewById(R.id.secondsetlayout));
		//else continue;

		ImageView menu = (ImageView) activeView.findViewById(R.id.menukey);
		menu.setClickable(true);
		menu.setOnClickListener(this);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			ImageView book = (ImageView) activeView.findViewById(R.id.interpretationkey);
			book.setClickable(true);
			book.setOnClickListener(this);
		}
		

		ImageView spread = (ImageView) activeView.findViewById(R.id.spreadkey);
		if (Spread.circles.size() > 1) {
			spread.setClickable(true);
			spread.setOnClickListener(this);
		} else {
			spread.setAlpha(0);
		}
		
		ImageView divine = (ImageView) activeView.findViewById(R.id.divine);
		divine.setClickable(true);
		divine.setOnTouchListener(gestureListener);
		activeView.setOnTouchListener(gestureListener);	
		
		flipper.addView(activeView);
		
		divine = placeImage(indexin,divine,getApplicationContext(),flipdex);
		
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			showInfo(getResources().getConfiguration().orientation);
		else
			infoDisplayed=false;
		flipper.setAnimationCacheEnabled(false);
		flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
		flipper.setDrawingCacheEnabled(false);
		flipper.setDisplayedChild(0);
		if (!browsing)
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
			ImageView menu = (ImageView) activeView.findViewById(R.id.menukey);
			menu.setClickable(true);
			menu.setOnClickListener(this);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				ImageView book = (ImageView) activeView.findViewById(R.id.interpretationkey);
				book.setClickable(true);
				book.setOnClickListener(this);
			}
			
			ImageView spread = (ImageView) activeView.findViewById(R.id.spreadkey);
			if (Spread.circles.size() > 1) {
				spread.setClickable(true);
				spread.setOnClickListener(this);
			} else {
				spread.setAlpha(0);
			}
			
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
	
	public void redisplay() {
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
		inFromRight.setDuration(80);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		return inFromRight;
	}
	protected Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoLeft.setDuration(80);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		return outtoLeft;
	}

	protected Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		inFromLeft.setDuration(80);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}
	protected Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
				Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
		);
		outtoRight.setDuration(80);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		return outtoRight;
	}	

	protected void onStop() {
		super.onStop();
//		if (!sharing) {	
//			Spread.circles = new ArrayList<Integer>();
//			Spread.working = new ArrayList<Integer>();
//			laidout = null;
//			inflater=null;
//			gestureDetector = null;
//			gestureListener = null;
//			//flipper = null;
//			myInt = null;
//			aq=null;
//			System.gc();
//			finish();
//		}
	}
	@Override
	protected void onPause() {
		super.onPause();	
		if (mp != null)
			if (mp.isPlaying())
				mp.stop();
		System.gc();
	}

	@Override
	protected void onResume() {
		super.onResume();	
		if (mp != null)
			if (!mp.isPlaying() &! muted) {
				mp=MediaPlayer.create(this, R.raw.background); 
				mp.start();
				mp.setLooping(true);
			}
		System.gc();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		if (mySpread != null && mySpread.myview != null)
			mySpread.clearView(mySpread.myview);
		if (mp != null)
			if (mp.isPlaying())
				mp.stop();
		System.gc();
	}
	
	
	
	public ImageView placeImage(int index, ImageView toPlace, Context con, ArrayList<Integer> flipdex) {
		
		Bitmap bmp = null;
		
		
       
		
		File customFile = new File(Environment.getExternalStorageDirectory()+"/tarotbot.custom/"+Interpretation.getCardName(flipdex.get(index)));
		File customFile2 = new File(Environment.getExternalStorageDirectory()+"/tarotbot.custom/"+Interpretation.getCardNumber(index));
		if (customFile.exists() && displayPrefs.getBoolean("custom.deck", false)) {
        	bmp = BitmapFactory.decodeFile(customFile.getPath());
        } else if (customFile2.exists() && displayPrefs.getBoolean("custom.deck", false)) {
        	bmp = BitmapFactory.decodeFile(customFile2.getPath());
        }
		if (bmp == null && TarotBotManager.hasEnoughMemory(32,getApplicationContext())) 	
			try {
				File toRead = new File(Environment.getExternalStorageDirectory()+"/"+WebUtils.md5(getMyFolder())+"/"+WebUtils.md5(getMyType())+".ex");
	            if (toRead.exists() ){//&& ((conf.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)) {

	            	bmp = BitmapFactory.decodeFile(toRead.getPath()+"/"+WebUtils.md5(getMyType()+"/"+BotaInt.getCardName(flipdex.get(index))));
	            	if (bmp==null)
	            		bmp = BitmapFactory.decodeFile(toRead.getPath()+"/"+WebUtils.md5(BotaInt.getCardName(flipdex.get(index))));
	            	System.err.println(toRead.getPath()+"/"+WebUtils.md5(BotaInt.getCardName(flipdex.get(index))));
	            	//bmp = BitmapFactory.decodeByteArray(buf, 0,buf.length);
	        		deckPrefs = getSharedPreferences("decked", 0);
	        		deckPrefs.edit().putString("path", WebUtils.md5(getMyFolder())+"/"+WebUtils.md5(getMyType()));
				        
	    		}      
	            
		    } catch (Exception e) {
		    	    			
		    }
		    BitmapFactory.Options options=new BitmapFactory.Options();
		    options.inDither=false;
		    
		    if (bmp == null) {	        	
	        	if (!TarotBotManager.hasEnoughMemory(MIDRES, getApplicationContext())&&isBig)// && 
	        		options.inSampleSize = 2;					
	        	bmp = BitmapFactory.decodeResource(con.getResources(), BotaInt.getCard(flipdex.get(index)),options);
	        }
		int w = bmp.getWidth();
        int h = bmp.getHeight();
        
        boolean changed = false;
        int rotation = 0;
        
        Matrix mtx = new Matrix();
        int diff = h-w;
        
        if (diff < (h/4)*-1) {
        	rotation+=90;
        	changed = true;        	
        }		
		if (!browsing && mySpread.myDeck.reversed[flipdex.get(index)]) {		
			rotation+=180;
			changed = true;
		} 
		if (changed) {
			mtx = new Matrix();
			mtx.postRotate(rotation);
			
			bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
        	w = bmp.getWidth();
            h = bmp.getHeight();
		}
		BitmapDrawable bmd = new BitmapDrawable(bmp);
		
		toPlace.setImageDrawable(bmd);
		
		setBackground(toPlace);
		
		return toPlace;
	}

	public ImageView placeImage(int index, ImageView toPlace, Context con) {
		Configuration conf =con.getResources().getConfiguration();
		Bitmap bmp = null;
		
		if (TarotBotManager.hasEnoughMemory(32,getApplicationContext())) 	
			try {
				File toRead = new File(Environment.getExternalStorageDirectory()+"/"+WebUtils.md5(getMyFolder())+"/"+WebUtils.md5(getMyType()));
	            if (toRead.exists() ){//&& ((conf.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)) {
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
			        		deckPrefs = getSharedPreferences("decked", 0);
			        		deckPrefs.edit().putString("path", WebUtils.md5(getMyFolder())+"/"+WebUtils.md5(getMyType()));
			        		//
			        		break;
			            }
		            }				        
	    		}      
	            
		    } catch (Exception e) {
		    	    			
		    }
		    BitmapFactory.Options options;
        	options=new BitmapFactory.Options();
        	options.inDither=false;

		    if (bmp == null) {
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
		
		
		BitmapDrawable bmd = new BitmapDrawable(bmp);
		
		toPlace.setImageDrawable(bmd);		
		setBackground(toPlace);
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
		ImageView im = ((ImageView) v.findViewById(R.id.divine));
		if (im.getDrawable() instanceof BitmapDrawable)
			((BitmapDrawable)im.getDrawable()).getBitmap().recycle();
		im.getDrawable().setCallback(null);
		im.setImageDrawable(null);
		im.getResources().flushLayoutCache();
		im.destroyDrawingCache();
		im = null;
		v=null;
		System.gc();
	}
	public void preNavigate() {
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		for (int i = 0; i < flipper.getChildCount(); i++) {
			View v = flipper.getChildAt(i);
			flipper.removeView(v);
			v.destroyDrawingCache();
			ImageView im = ((ImageView) v.findViewById(R.id.divine));
			if (im.getDrawable() instanceof BitmapDrawable)
				((BitmapDrawable)im.getDrawable()).getBitmap().recycle();
			im.getDrawable().setCallback(null);
			im.setImageDrawable(null);
			im.getResources().flushLayoutCache();
			im.destroyDrawingCache();
			im = null;
			v=null;
		}
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
		if (spreadLabels != null && 
				spreadLabels.length > secondSetIndex && 
				Spread.circles.size() <78 && 
				getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && 
				spreadLabels[secondSetIndex] != null 
				&& spreadLabels[secondSetIndex].length() > 0) {
			Toast toast = new Toast(getApplicationContext());
			View toaster = inflater.inflate(R.layout.gothic_toast,(ViewGroup) findViewById(R.id.toast_layout_root));
			setBackground(toaster);
			toast.setView(toaster);
			((TextView)toaster.findViewById(R.id.toast_text)).setText(spreadLabels[secondSetIndex]);			
			toast.setDuration(Toast.LENGTH_SHORT);			
			//toast.show();
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return 1;
	}
	public String getStorageFile() {
		// TODO Auto-generated method stub
		return "tarotbot.store";
	}
	protected boolean canBeRestored() {
		if (getLastNonConfigurationInstance()!=null) {
			return true;
		}
		return false;
	}
	public void establishMenu(int menu) {
		View menulay = inflater.inflate(menu, null);
		setBackground(menulay);
		setContentView(menulay);
		
	}
	public void setBackground(View v) {
		v.setBackgroundColor(displayPrefs.getInt("background.color", Color.BLACK));
		if (v.findViewById(R.id.menulist) != null)
			((ListView)v.findViewById(R.id.menulist)).setCacheColorHint(displayPrefs.getInt("background.color", Color.BLACK));
		if (v.findViewById(R.id.spreadmenulist) != null)
			((ListView)v.findViewById(R.id.spreadmenulist)).setCacheColorHint(displayPrefs.getInt("background.color", Color.BLACK));
	}
}