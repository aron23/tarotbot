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
import liberus.tarot.os.activity.AbstractTarotBotActivity.MyGestureDetector;
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
import liberus.tarot.spread.gothic.GothicArch;
import liberus.tarot.spread.gothic.GothicArrowSpread;
import liberus.tarot.spread.gothic.GothicCelticSpread;
import liberus.tarot.spread.gothic.GothicChaosSpread;
import liberus.tarot.spread.gothic.GothicDialecticSpread;
import liberus.tarot.spread.gothic.GothicPentagram;
import liberus.tarot.spread.gothic.MysticSeven;
import liberus.tarot.spread.gothic.VampireKiss;
import liberus.utils.EfficientAdapter;
import liberus.utils.WebUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewSwitcher;


public class OldTarotBotActivity extends AbstractTarotBotActivity  {


	private boolean spread;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setFullscreen();
		
		inflater = LayoutInflater.from(this);
		state = "mainmenu";
		setContentView(R.layout.mainmenu);
		initText();	
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,mainmenu,R.layout.listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("mainmenu");		
		
		readingPrefs = getSharedPreferences("tarotbot.gothic.reading", 0);
		readingPrefsEd = readingPrefs.edit();
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = getGestureListener(gestureDetector);
		initSaved();		
	}
	
	
	protected void launchBrowse() {
		browsing = true;
		spreading = false;
		ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
    	for(int card: RiderWaiteDeck.cards) {
    		reversals.add(false);
    	}
    	loaded=true;
    	Interpretation.myDeck = new RiderWaiteDeck(reversals.toArray(new Boolean[0]));	
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
	
	protected void initSaved() {
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

	protected void reInit() {
		state = "mainmenu";
		setContentView(R.layout.mainmenu);
		initText();	
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,mainmenu,R.layout.listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("mainmenu");
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
			if (loaded &! browsing) {
				menu.findItem(MENU_SHARE).setEnabled(true);
				menu.findItem(MENU_NAVIGATE).setEnabled(true);
			} else if (browsing) {
				menu.findItem(MENU_NAVIGATE).setEnabled(true);
				menu.findItem(MENU_SHARE).setEnabled(false);
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

	protected void initText() {
		whatUrl = getString(R.string.whatUrl);
		howUrl = getString(R.string.howUrl);
		tarotUrl = getString(R.string.tarotUrl);
		updatesUrl = getString(R.string.updatesUrl);
		helpUrls = new String[]{whatUrl,howUrl,tarotUrl,updatesUrl};
		res = getResources();
		
		mainmenu = res.getStringArray(R.array.gothic_main_menu);
		secondmenu = res.getStringArray(R.array.gothic_second_menu);
		spreadmenu = res.getStringArray(R.array.spread_array);
		
		single = res.getStringArray(R.array.single);
		timeArrow = res.getStringArray(R.array.timeArrow);
		dialectic = res.getStringArray(R.array.dialectic);
		pentagram = res.getStringArray(R.array.pentagram);
		chaosStar = res.getStringArray(R.array.chaosStar);
		celticCross = res.getStringArray(R.array.celticCross);
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  if (spreading) {
		  redisplaySpreadStart();
		  
	  } else if (!begun && !browsing) {
		  redisplayMain();
	  } else {
		  rotateDisplay();
//		  if (GothicSpread.circles.size() > 1) {
//			  if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//					Toast.makeText(this, R.string.portraitnavigation, Toast.LENGTH_LONG).show();
//				else
//					Toast.makeText(this, R.string.landscapenavigation, Toast.LENGTH_LONG).show();
//		  }  
	  }
	}

	public void botaSpread() {
		setContentView(R.layout.botastart);		
		
		querantPrefs = getSharedPreferences("tarotbot", 0);
				
		sharing = false;
		
		init = true;
		//statusspin = (WheelView) findViewById(R.id.statusspinner);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getResources().getStringArray(R.array.status_array));
		
//		statusspin.setAdapter(adapter);
//		statusspin.setVisibleItems(3);
//		statusspin.setCurrentItem(querantPrefs.getInt("querantstatus", 0));
		
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
			laidout.add((ViewGroup) this.findViewById(R.id.mainlayout));
			//myRandomCard = getRandomCard();
			//((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(myRandomCard));
			
	
			initbutton = (Button) this.findViewById(R.id.initbotabutton);
			initbutton.setOnClickListener(this);
			
			toastText(getString(R.string.questionprompt)); 
		}
	}
	
	public void seqSpread() {
		setContentView(R.layout.seqstart);		
		
		querantPrefs = getSharedPreferences("tarotbot", 0);
		
		
		sharing = false;

		//reversalCheck = (CheckBox)this.findViewById(R.id.reversalcheck);
		
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
			laidout.add((ViewGroup) this.findViewById(R.id.mainlayout));
			//myRandomCard = getRandomCard();
			//((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(myRandomCard));
			
	
			initbutton = (Button) this.findViewById(R.id.initseqbutton);
			initbutton.setOnClickListener(this);
		}
	}
	
	protected void restoreMe() {
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
		Interpretation.myDeck.reversed = reversals.toArray(new Boolean[0]);
		secondSetIndex = querantPrefs.getInt("activeCard", 0);
	}

	protected void changeQuerant() {

					
//			if (statusspin.getAdapter().getItem(statusspin.getCurrentItem()).toString().contains(getString(R.string.status_sm))) {
//				male = true;
//				partnered=false;
//			} else if (statusspin.getAdapter().getItem(statusspin.getCurrentItem()).toString().contains(getString(R.string.status_pf))) {
//				male=false;
//				partnered = true;    	
//			} else if (statusspin.getAdapter().getItem(statusspin.getCurrentItem()).toString().contains(getString(R.string.status_pm))) {
//				male = true;
//				partnered = true;
//			} else {
//				male = false;
//				partnered = false;
//			}

		//Toast.makeText(this, dp.getMonth(), Toast.LENGTH_SHORT).show();
		aq = new Querant(male,partnered,new GregorianCalendar(dp_year_spin.getCurrentItem()+1910,dp_month_spin.getCurrentItem(),dp_day_spin.getCurrentItem()+1));
		SharedPreferences.Editor ed = querantPrefs.edit();
		ed.putInt("birthyear", aq.birth.get(Calendar.YEAR));
		ed.putInt("birthmonth", aq.birth.get(Calendar.MONTH));
		ed.putInt("birthday", aq.birth.get(Calendar.DAY_OF_MONTH));
		//ed.putInt("querantstatus", statusspin.getCurrentItem());		
		ed.commit();
	}
	
	protected void showInfo(int type) {
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);

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
			
			Dialog interpretor = new Dialog(this,android.R.style.Theme);
			interpretor.setTitle(mySpread.myLabels[secondSetIndex]);
			interpretor.setContentView(showing);
			interpretor.show();
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
	
	protected void redisplayMain() {
		View activeView = inflater.inflate(R.layout.botastart, null);
		setContentView(activeView);
		//statusspin = (WheelView) findViewById(R.id.statusspinner);
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getResources().getStringArray(R.array.status_array));
//		
//		statusspin.setAdapter(adapter);
//		statusspin.setVisibleItems(3);
//		statusspin.setCurrentItem(querantPrefs.getInt("querantstatus", 0));
		statusspin.forceLayout();

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
			laidout.add((ViewGroup) this.findViewById(R.id.mainlayout));			
			//((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(myRandomCard));
			
	
			initbutton = (Button) this.findViewById(R.id.initbotabutton);
			initbutton.setOnClickListener(this);
		
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
				//readingPrefsEd.putBoolean("reversal", reversalCheck.isChecked());
				//readingPrefsEd.commit();
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
				    	//Interpretation.myDeck = ;	
				    	myInt = new BotaInt(new RiderWaiteDeck(reversals.toArray(new Boolean[0])),aq);
				    	Spread.working = working;
				    	BotaInt.loaded = true;
				    	if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("bota")) {
				    		BotaInt.setMyQuerant(new Querant(significator));
				    		mySpread = new BotaSpread(myInt);				    		
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("celtic")) {
				    		mySpread = new CelticSpread(myInt,celticCross);
				    		spreadLabels = celticCross;
				    		Spread.circles = Spread.working;
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("chaos")) {
				    		Spread.circles = Spread.working;
				    		mySpread = new ChaosSpread(myInt,chaosStar);
				    		spreadLabels = chaosStar;
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("single")) {
				    		mySpread = new SeqSpread(myInt,single);
				    		Spread.circles = Spread.working;
				    		spreadLabels = single;
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("arrow")) {
				    		mySpread = new ArrowSpread(myInt,timeArrow);
				    		spreadLabels = timeArrow;
				    		Spread.circles = Spread.working;				    		
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("dialectic")) {
				    		Spread.circles = Spread.working;
				    		spreadLabels = dialectic;
				    		mySpread = new DialecticSpread(myInt,dialectic);
				    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(which)))).get("type").equals("pentagram")) {
				    		Spread.circles = Spread.working;
				    		spreadLabels = pentagram;
				    		mySpread = new PentagramSpread(myInt,pentagram);
				    	}
				    	spreading=false;
						begun = true;
						browsing = false;
				    	//new BotaInt(new RiderWaiteDeck(reversals.toArray(new Boolean[0])),new Querant(significator),working);
				    	beginSecondStage();
				    	break;
					}
				}
			}
				
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
			//statusselected = statusspin.getCurrentItem();
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
		
public void onItemClick(AdapterView<?> adapter, View list, int index, long arg3) {
		
		if (adapter.getTag().equals("mainmenu")) {//.getContentDescription().equals("querant")
			Dialog helper = new Dialog(this,android.R.style.Theme);
			switch (index) {
				case 0:					
					launchBrowse();
			        return;
			    case 1:	
			    	state = "spreadmenu";
					setContentView(R.layout.spreadmenu);
					
					myMenuList = (ListView) this.findViewById(R.id.spreadmenulist);
					myMenuList.setAdapter(new EfficientAdapter(this,inflater,spreadmenu,R.layout.listitem));
					myMenuList.setOnItemClickListener(this);
					myMenuList.setTag("spreadmenu");
					reverseToggle = (ToggleButton) this.findViewById(R.id.reversal_button);
					reverseToggle.setChecked(readingPrefs.getBoolean("reversal", false));
					reverseToggle.setClickable(true);
					reverseToggle.setOnClickListener(this);
					return;			    	
				case 2: 	
					priorstate = state;
					state = "dialog";
					helping = true;
			    	//chooseAbout();
					return;
				case 3:
					displaySaved();
			        return;				
				}			
			

		} else if (adapter.getTag().equals("secondmenu")) {//.getContentDescription().equals("querant")
			Dialog helper = new Dialog(this,android.R.style.Theme);
			switch (index) {
				case 0:					
					navigate();
			        return;	
				case 1:					
					launchBrowse();
			        return;
			    case 2:	
			    	state = "spreadmenu";
					setContentView(R.layout.spreadmenu);
					
					myMenuList = (ListView) this.findViewById(R.id.spreadmenulist);
					myMenuList.setAdapter(new EfficientAdapter(this,inflater,spreadmenu,R.layout.listitem));
					myMenuList.setOnItemClickListener(this);
					myMenuList.setTag("spreadmenu");	
					reverseToggle = (ToggleButton) this.findViewById(R.id.reversal_button);
					reverseToggle.setChecked(readingPrefs.getBoolean("reversal", false));
					reverseToggle.setClickable(true);
					reverseToggle.setOnClickListener(this);
					return;			    	
				case 3: 	
					priorstate = state;
					state = "dialog";
					helping = true;
			    	//chooseAbout();
					return;
				case 4:
					displaySaved();
			        return;				
				case 5:
			        save(false);
			        return;
			    case 6:
			    	save(true);			     
			        return;				
				}
			
			

		} else if (adapter.getTag().equals("spreadmenu")) {//.getContentDescription().equals("querant")			
			readingPrefsEd.putInt("spread", adapter.getSelectedItemPosition());
			readingPrefsEd.commit();
			switch (index) {
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
			spread=true;
			secondSetIndex = 0;
			state = "new reading";
			begun = true;
			browsing = false;
			myInt = new BotaInt(new RiderWaiteDeck(), aq);
			Integer[] shuffled = Interpretation.myDeck.shuffle(new Integer[78],3);
			Deck.cards = shuffled;
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
			loaded = false;
			beginSecondStage();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (state.equals("mainmenu")) {
				finish();
				return true;
			} else if (state.equals("dialog")) {
				if (priorstate.equals("secondmenu")) {
					state = "new reading";
					priorstate = "";
					displaySecondStage(secondSetIndex);
					return true;
				} else {
					state = "mainmenu";
					spreading=false;
					spread=false;
					reInit();
					return true;
				}
			} else if (state.equals("new reading")) {
				spreading=true;
				spread=false;
				//reInitSpread();
				return true;
			} else if (state.equals("spreadmenu") || state.equals("loaded")) {
				spreading=false;
				spread=false;
				reInit();
				return true;
			} else if (state.equals("secondmenu")) {
				state = priorstate;
				displaySecondStage(secondSetIndex);
				return true;
			} else {
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {			
			priorstate = state;
			state = "secondmenu";
			setContentView(R.layout.mainmenu);
			initText();	
			myMenuList = (ListView) this.findViewById(R.id.menulist);
			myMenuList.setOnItemClickListener(this);
			if (priorstate.equals("new reading")) {
				myMenuList.setAdapter(new EfficientAdapter(this,inflater,secondmenu,R.layout.listitem));			
				myMenuList.setTag("secondmenu");
			} else {
				myMenuList.setAdapter(new EfficientAdapter(this,inflater,mainmenu,R.layout.listitem));			
				myMenuList.setTag("mainmenu");
			}
	
			return true;
		}
		return super.onKeyDown(keyCode,event);		
	}
}