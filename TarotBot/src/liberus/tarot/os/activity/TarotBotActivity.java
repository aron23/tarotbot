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


import liberus.tarot.deck.Deck;
import liberus.tarot.deck.FullTarotDeck;
import liberus.tarot.deck.TarotTrumpDeck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;

import liberus.utils.MyGestureDetector;
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

import liberus.utils.EfficientAdapter;
import liberus.utils.MyProgressDialog;
import liberus.utils.TarotBotManager;
import liberus.utils.WebUtils;
import liberus.utils.color.ColorDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
//import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
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
import android.widget.BaseAdapter;
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

import com.heyzap.sdk.HeyzapLib;

public abstract class TarotBotActivity extends AbstractTarotBotActivity implements ColorDialog.OnClickListener  {

	private Dialog helper;
	private boolean leavespread;
	private TextView deckNote;
	protected Dialog interpretor;
	private Integer significator;
	
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setFullscreen();
		
		inflater = LayoutInflater.from(this);
		readingPrefs = getSharedPreferences("tarotbot.reading", 0);
		readingPrefsEd = readingPrefs.edit();
		
		displayPrefs = getSharedPreferences("tarotbot.display", 0);
		displayPrefsEd = displayPrefs.edit();
		
		gestureDetector = new GestureDetector(new MyGestureDetector(this));
		gestureListener = getGestureListener(gestureDetector);
		initSaved(getStorageFile());
		new Thread(new Runnable(){
			public void run(){				
				initHighRes();
			}
		},"downloading").start();
		initText();	
		if (getIntent().getType() != null && getIntent().getType().equals("widget")) {
			//seqSpread();
			spreadLabels = single;
			style = "single";
			spreading=false;
			//spread=true;
			secondSetIndex = 0;
			state = "single";
			browsing = false;
			begun = true;	
			if (readingPrefs.getBoolean("trumps.only", false) || isTrumpsOnly()){
					myInt = new BotaInt(new TarotTrumpDeck(), aq); 
					Deck.cards = Deck.orderedDeck(22);
				} else {
					myInt = new BotaInt(new FullTarotDeck(), aq);
					Deck.cards = Deck.orderedDeck(78);
				}
			
			mySpread = getMySeqSpread(myInt,spreadLabels,true,isTrumpsOnly());
			loaded = false;
			
			beginSecondStage();
		} else {		
			establishMenu(R.layout.mainmenu);
			state = "mainmenu";						
			myMenuList = (ListView) this.findViewById(R.id.menulist);
			myMenuList.setAdapter(new EfficientAdapter(this,inflater,mainmenu,R.layout.listitem));
			myMenuList.setOnItemClickListener(this);
			myMenuList.setTag("mainmenu");		
		}
		HeyzapLib.load(this,false);
	}
	@Override
	public Spread getMySeqSpread(Interpretation myInt, String[] labels, boolean cardOfTheDay,boolean trumpsOnly) {
		return new SeqSpread(myInt,labels,cardOfTheDay,trumpsOnly);
	}
	@Override
	public Spread getMyBrowseSpread(Interpretation myInt, boolean trumpsOnly) {
		return new BrowseSpread(myInt,trumpsOnly);
	}
	protected void launchBrowse() {
		state = "loaded";
		
		spreading=false;
		//spread=true;
		secondSetIndex = 0;

		begun = true;
		
		loaded=true;
    	BotaInt.loaded = true;
    	Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
    	if (isTrumpsOnly())
			myInt = new BotaInt(new TarotTrumpDeck(), aq);
		else
			myInt = new BotaInt(new FullTarotDeck(), aq);
		

		ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
    	for(int card: myInt.myDeck.cards) {
    		reversals.add(false);
    	}
    	
    	if (isTrumpsOnly())
    		myInt.myDeck = new TarotTrumpDeck(reversals.toArray(new Boolean[0]));
		else
			myInt.myDeck = new FullTarotDeck(reversals.toArray(new Boolean[0]));
    		
    	
    	
		mySpread = getMyBrowseSpread(myInt,isTrumpsOnly());
		
		style="browse";
		type = new ArrayList<Integer>();
		flipdex = new ArrayList<Integer>();
		browsing = true;
		beginSecondStage();
		
	}


	

	protected void initText() {
//		whatUrl = getString(R.string.whatUrl);
//		howUrl = getString(R.string.howUrl);
//		tarotUrl = getString(R.string.tarotUrl);
//		updatesUrl = getString(R.string.updatesUrl);
//		helpUrls = new String[]{whatUrl,howUrl,tarotUrl,updatesUrl};
		res = getResources();
		mainmenu = res.getStringArray(R.array.main_menu);
		secondmenu = res.getStringArray(R.array.second_menu);
		spreadmenu = res.getStringArray(R.array.spread_array);
		single = res.getStringArray(R.array.single);
		timeArrow = res.getStringArray(R.array.timeArrow);
		pentagram = res.getStringArray(R.array.pentagram);
		dialectic = res.getStringArray(R.array.dialectic);
		chaosStar = res.getStringArray(R.array.chaosStar);
		celticCross = res.getStringArray(R.array.celticCross);
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
//	  if (spreading) {
//		  reInitSpread(R.layout.spreadmenu);		  
//	  } else if (!begun && !browsing) {
//		  reInit();
//	  } else 
	  if (state.equals("new reading") || state.equals("loaded") || state.equals("single")) {
		  rotateDisplay();
	  }
	}

	
	
	
	protected void changeQuerant() {

		
		if (statusspin.getSelectedItem().toString().contains(getString(R.string.status_sm))) {
			male = true;
			partnered=false;
		} else if (statusspin.getSelectedItem().toString().contains(getString(R.string.status_pf))) {
			male=false;
			partnered = true;    	
		} else if (statusspin.getSelectedItem().toString().contains(getString(R.string.status_pm))) {
			male = true;
			partnered = true;
		} else {
			male = false;
			partnered = false;
		}

		//Toast.makeText(this, dp.getMonth(), Toast.LENGTH_SHORT).show();
		aq = new Querant(male,partnered,new GregorianCalendar(dp.getYear(),dp.getMonth(),dp.getDayOfMonth()));
		SharedPreferences.Editor ed = querantPrefs.edit();
		ed.putInt("birthyear", aq.birth.get(Calendar.YEAR));
		ed.putInt("birthmonth", aq.birth.get(Calendar.MONTH));
		ed.putInt("birthday", aq.birth.get(Calendar.DAY_OF_MONTH));
		ed.putInt("querantstatus", statusspin.getSelectedItemPosition());		
		ed.commit();
	}
	
	public void botaSpread() {
		state = "bota reading";
        setContentView(R.layout.botastart);             
        
        querantPrefs = getSharedPreferences("tarotbot", 0);
                        
        sharing = false;
        
        statusspin = (Spinner) findViewById(R.id.statusspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                        this, R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusspin.setAdapter(adapter);
        statusspin.setOnItemSelectedListener(this);
        statusspin.setSelection(3);
        statusspin.setSelection(querantPrefs.getInt("querantstatus", 0));
        dp = (DatePicker)this.findViewById(R.id.birthdatepicker);

        Calendar today = Calendar.getInstance();
         
        if (querantPrefs.contains("birthyear"))
                dp.init(querantPrefs.getInt("birthyear", today.get(Calendar.YEAR)), querantPrefs.getInt("birthmonth", today.get(Calendar.MONTH)), querantPrefs.getInt("birthday", today.get(Calendar.DAY_OF_MONTH)), this);        
        else
                dp.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), this);

        //changeQuerant();                
        
        if (canBeRestored()) {
        	if (readingPrefs.getBoolean("trumps.only", false) || isTrumpsOnly())
				myInt = new BotaInt(new TarotTrumpDeck(), aq);
			else
				myInt = new BotaInt(new FullTarotDeck(), aq);
                //restoreMe();
                restoreSecondStage();
        } else {
                secondSetIndex = 0;
                laidout.add((RelativeLayout) this.findViewById(R.id.mainlayout));
                //myRandomCard = getRandomCard();
                //((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(myRandomCard));
                

                initbutton = (Button) this.findViewById(R.id.initbotabutton);
                initbutton.setOnClickListener(this);
                //Toast.makeText(this, R.string.questionprompt, Toast.LENGTH_LONG).show(); 
        }
}
	
	public void showInfo(int type) {
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
		if (type == Configuration.ORIENTATION_PORTRAIT) {
			infoDisplayed = true;
			int i = Spread.circles.get(secondSetIndex);
			String interpretation = mySpread.getInterpretation(i,getApplicationContext());
			showing = inflater.inflate(R.layout.interpretation, null);
			setBackground(showing);
			
			TextView title = (TextView)showing.findViewById(R.id.title);
			if (secondSetIndex < mySpread.myLabels.length)
				title.setText(mySpread.myLabels[secondSetIndex]);
			else
				title.setText("");
			TextView cardlabel = (TextView)showing.findViewById(R.id.cardlabel);
			
			if (mySpread.myDeck.isReversed(i)) 
				cardlabel.setText(mySpread.getCardTitle(i,getApplicationContext())+" reversed");
			else
				cardlabel.setText(mySpread.getCardTitle(i,getApplicationContext()));
			
			infotext = (TextView) showing.findViewById(R.id.interpretation);	
			
			infotext.setText(Html.fromHtml(interpretation));
			infotext.setOnTouchListener(gestureListener);
			
			showing.setOnTouchListener(gestureListener);

			ImageView card = (ImageView) showing.findViewById(R.id.cardkey);
			card.setClickable(true);
			card.setOnClickListener(this);
			
			ImageView spread = (ImageView) showing.findViewById(R.id.spreadkey);
			if (Spread.circles.size() > 1) {
				spread.setClickable(true);
				spread.setOnClickListener(this);
			} else {
				spread.setAlpha(0);
			}
			
			interpretor = new Dialog(this,R.style.interpretation_style);

			interpretor.setContentView(showing);			
			interpretor.setOnKeyListener(this);			
			interpretor.show();
			
		} else if (type == Configuration.ORIENTATION_LANDSCAPE) {
			int i = Spread.circles.get(secondSetIndex);
			String interpretation = mySpread.getInterpretation(i,getApplicationContext());
			View v = flipper.getCurrentView();
			setBackground(v);
			
			TextView title = (TextView)v.findViewById(R.id.title);
			if (secondSetIndex < mySpread.myLabels.length)
				title.setText(mySpread.myLabels[secondSetIndex]);
			else
				title.setText("");
			TextView cardlabel = (TextView)v.findViewById(R.id.cardlabel);
			
			if (mySpread.myDeck.isReversed(i)) 
				cardlabel.setText(mySpread.getCardTitle(i,getApplicationContext())+" reversed");
			else
				cardlabel.setText(mySpread.getCardTitle(i,getApplicationContext()));
			
			infotext = (TextView)v.findViewById(R.id.interpretation);
			infotext.setTextColor(Color.WHITE);
			infotext.setText(Html.fromHtml(interpretation));
		}			
	}
	
	public void onClick(Object tag, int color) {
		displayPrefsEd.putInt("background.color", color);
		displayPrefsEd.commit();
		View options = findViewById(R.id.mainlayout);
		options.setBackgroundColor(color);
	}
	
	public void onClick(View v) {	
		if (v.equals(this.findViewById(R.id.initbotabutton))) {
			state = "new reading";
			begun = true;
            browsing = false;
            changeQuerant();
            if (isTrumpsOnly())
    			myInt = new BotaInt(new TarotTrumpDeck(), aq);
    		else
    			myInt = new BotaInt(new FullTarotDeck(), aq);
            mySpread = new BotaSpread(myInt);
            beginSecondStage();     
		} else if (v.equals(this.findViewById(R.id.reversal_button))) {
			if (!init) {
				readingPrefsEd.putBoolean("reversal", reverseToggle.isChecked());
				readingPrefsEd.commit();
			}
		} else if (v.equals(this.findViewById(R.id.trumps_only_button))) {
			if (!init) {
				readingPrefsEd.putBoolean("trumps.only", trumpToggle.isChecked());
				readingPrefsEd.commit();
			}
		} else if (v.equals(this.findViewById(R.id.custom_deck_button))) {
			if (!init) {
				displayPrefsEd.putBoolean("custom.deck", deckToggle.isChecked());
				displayPrefsEd.commit();
				if (deckToggle.isChecked()) {
					deckNote.setText(Html.fromHtml(getString(R.string.custom_deck_note)));
					Linkify.addLinks(deckNote, Linkify.ALL);
					deckNote.setLinksClickable(true);
					deckNote.setMovementMethod(LinkMovementMethod.getInstance());				
				} else
					deckNote.setText("");
			}
		} else if (v.getId()==R.id.menukey) {
			onKeyDown(KeyEvent.KEYCODE_MENU,null);
		} else if (v.getId()==R.id.interpretationkey) {
			showInfo(getResources().getConfiguration().orientation);
		} else if (v.getId()==R.id.cardkey) {
			if (interpretor != null)
				interpretor.dismiss();
		} else if (v.getId()==R.id.spreadkey) {
			navigate();
			if (interpretor != null)
				interpretor.dismiss();
		} else if (v instanceof ImageView && v.getId() > -1) {
		
			if (navigator != null) {
				navigator.dismiss();
				navigator = null;				
			}
			if (browsing && v.getId() < 5) {
				ViewGroup viewey = (ViewGroup) findViewById(android.R.id.content);
				mySpread.clearView(viewey);
				mySpread.paged = v.getId();
				navigate();
				return;
			}
			if (browsing) {
				ViewGroup viewey = (ViewGroup) findViewById(android.R.id.content);
				mySpread.clearView(viewey);
				if (Interpretation.getCardIndex(v.getId()) >= 0)
					secondSetIndex = Interpretation.getCardIndex(v.getId());
				else
					secondSetIndex = Spread.working.size();
				state = "loaded";
				priorstate = "";
				displaySecondStage(secondSetIndex);
			} else {
//				if (v.getId() == 0)
//					secondSetIndex = GothicSpread.working.size()-1;
//				else
					secondSetIndex = v.getId();
					if (loaded)
						state = "loaded";
					else
						state = "new reading";
					priorstate = "";
					displaySecondStage(secondSetIndex);
			}
			if (!browsing && mySpread != null)
				mySpread.clearView(mySpread.myview);
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
	//		    if (saved.length > 5)
	//		    	read.put("date", saved[5]);
	//		    else
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
				        File gpxfile = new File(root, getStorageFile());
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
				        File gpxfile = new File(root, getStorageFile());
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
		}
	
		
	

			
				
				if (sharing) {
					saveResult = WebUtils.saveTarotBot(spreadToString(),deckToString(),reversalsToString(),input.getEditableText().toString(),style,getApplicationContext(),getMyType());
					String url = WebUtils.bitly(saveResult);
					share(getString(R.string.share_subject),WebUtils.bitly(saveResult));
				}
		} 
	}
	

	

	public void onItemSelected(AdapterView<?> adapter, View list, int arg2,
			long arg3) {
		

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		

	}
	public void onItemClick(AdapterView<?> adapter, View list, int index, long arg3) {
		
		if (adapter.getTag().equals("mainmenu")) {//.getContentDescription().equals("querant")
			helper = new Dialog(this,R.style.interpretation_style);
			switch (index) {
				case 0:
					//seqSpread();
					spreadLabels = single;
					style = "single";
					spreading=false;
					//spread=true;
					secondSetIndex = 0;
					state = "single";
					browsing = false;
					begun = true;					
					if (readingPrefs.getBoolean("trumps.only", false) || isTrumpsOnly()) {
						myInt = new BotaInt(new TarotTrumpDeck(), aq);
						Deck.cards = Deck.orderedDeck(22);
					} else {
						myInt = new BotaInt(new FullTarotDeck(), aq);
						Deck.cards = Deck.orderedDeck(78);
					}
					mySpread = getMySeqSpread(myInt,spreadLabels,true,isTrumpsOnly());
					loaded = false;
					
			    	
			    	//Interpretation.myDeck = new TarotTrumpDeck(reversals.toArray(new Boolean[0]));
			    	
					beginSecondStage();
					break;
				case 1: 
					//seqSpread();
					spreadLabels = single;
					style = "single";
					spreading=false;
					//spread=true;
					secondSetIndex = 0;
					state = "single";
					browsing = false;
					begun = true;					
					if (readingPrefs.getBoolean("trumps.only", false) || isTrumpsOnly())
						myInt = new BotaInt(new TarotTrumpDeck(), aq);
					else
						myInt = new BotaInt(new FullTarotDeck(), aq);
					Deck.cards = myInt.myDeck.shuffle(Deck.cards,3);					
					mySpread = getMySeqSpread(myInt,spreadLabels,false,isTrumpsOnly());
					loaded = false;
					ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
			    	for(int card: Deck.cards) {
			    		reversals.add(false);
			    	}
			    	
			    	//Interpretation.myDeck = new TarotTrumpDeck(reversals.toArray(new Boolean[0]));
			    	
					beginSecondStage();
					break;				
				case 2:					
					launchBrowse();
			        return;
			    case 3:	
			    	initSpreadMenu();
					return;			    	
			    case 4:
					displaySaved();
			        return;	
			    case 5: 
			    	showing = inflater.inflate(R.layout.interpretation, null);
			    	setBackground(showing);
					infotext = (TextView) showing.findViewById(R.id.interpretation);
					
					infotext.setText(Html.fromHtml(getString(R.string.about_tarot)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					//helper.setTitle("About the Tarot");
					((TextView)showing.findViewById(R.id.title)).setText("About the Tarot");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 6: 	 
					showing = inflater.inflate(R.layout.interpretation, null);
					setBackground(showing);
					infotext = (TextView) showing.findViewById(R.id.interpretation);	
					
					infotext.setText(Html.fromHtml(getString(R.string.about_artist)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					//helper.setTitle("About the Artist");
					((TextView)showing.findViewById(R.id.title)).setText("About the Artist");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 7:
			    	showing = inflater.inflate(R.layout.interpretation, null);
			    	setBackground(showing);
					infotext = (TextView) showing.findViewById(R.id.interpretation);	
					
					infotext.setText(Html.fromHtml(getString(R.string.about_app)+getString(R.string.market_link)+getString(R.string.other_apps)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					((TextView)showing.findViewById(R.id.title)).setText("About the App");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 8:	
			    	state = "spreadmenu";
			    	initOptionsMenu();
					return;						
				}			
			

		} else if (adapter.getTag().equals("secondmenu")) {//.getContentDescription().equals("querant")
			Dialog helper = new Dialog(this,android.R.style.Theme);
			switch (index) {
				case 0:			
					navigate();
			        return;
				case 1:
			        save(false);
			        return;
			    case 2:
			    	save(true);			     
			        return;		
			    case 3:
			    	HeyzapLib.checkin(this, R.string.checkin);			     
			        return;		
			    case 4:	
			    	reInitSpread(R.layout.spreadmenu);								
					return;			    	
			    case 5: 
			    	showing = inflater.inflate(R.layout.interpretation, null);
			    	setBackground(showing);
					infotext = (TextView) showing.findViewById(R.id.interpretation);
					
					infotext.setText(Html.fromHtml(getString(R.string.about_tarot)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the Tarot");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 6: 	 
					showing = inflater.inflate(R.layout.interpretation, null);
					setBackground(showing);
					infotext = (TextView) showing.findViewById(R.id.interpretation);	
					
					infotext.setText(Html.fromHtml(getString(R.string.about_artist)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the Artist");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 7:
			    	showing = inflater.inflate(R.layout.interpretation, null);
			    	setBackground(showing);
					infotext = (TextView) showing.findViewById(R.id.interpretation);	
					
					infotext.setText(Html.fromHtml(getString(R.string.about_app)+getString(R.string.market_link)+getString(R.string.other_apps)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the App");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 8:	
			    	state = "secondmenu";
			    	initOptionsMenu();
					return;
				}
			
			

		} else if (adapter.getTag().equals("spreadmenu")) {//.getContentDescription().equals("querant")			
			handleSpreadSelection(index,adapter);
		} else if (adapter.getTag().equals("loadmenu")) {//.getContentDescription().equals("querant") {
			handleLoadMenu(index,adapter);
		}
	}
	public void handleLoadMenu(int index, AdapterView<?> adapter) {
		Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
		flipdex = new ArrayList<Integer>();
		//ArrayList<String[]> deck = WebUtils.loadTarotBotReading(getApplicationContext(),Integer.valueOf(savedReadings.get(which)[0]));
		ArrayList<String[]> deck = new ArrayList<String[]>();
    	ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
    	ArrayList<Integer> working = new ArrayList<Integer>();
    	significator = Integer.valueOf(savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("significator"));
    	int count = 0;
    	String[] reversed = savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("reversals").split(",");
    	ArrayList<String> spreaded = new ArrayList<String>(Arrays.asList(savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("spread").split(",")));
    	for(String card: savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("deck").split(",")) {
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
    	state = "loaded";
    	myInt = new BotaInt(new FullTarotDeck(reversals.toArray(new Boolean[0])),aq);
    	Spread.working = working;
    	BotaInt.loaded = true;

    	processSavedSpread(index);
    	
    	spreading=false;
		begun = true;
		browsing = false;
    	//new BotaInt(new TarotTrumpDeck(reversals.toArray(new Boolean[0])),new Querant(significator),working);
    	beginSecondStage();
	}
	
	public void processSavedSpread(int index) {
		if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("bota")) {
    		BotaInt.setMyQuerant(new Querant(significator));
    		mySpread = new BotaSpread(myInt);				    		
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("celtic")) {
    		mySpread = new CelticSpread(myInt,celticCross);
    		spreadLabels = celticCross;
    		Spread.circles = Spread.working;
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("chaos")) {
    		Spread.circles = Spread.working;
    		mySpread = new ChaosSpread(myInt,chaosStar);
    		spreadLabels = chaosStar;
    	}  else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("pentagram")) {
    		Spread.circles = Spread.working;
    		mySpread = new PentagramSpread(myInt,pentagram);
    		spreadLabels = pentagram;
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("single")) {
    		mySpread = getMySeqSpread(myInt,single,false,isTrumpsOnly());
    		Spread.circles = Spread.working;
    		spreadLabels = single;
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("arrow")) {
    		mySpread = new ArrowSpread(myInt,timeArrow);
    		spreadLabels = timeArrow;
    		Spread.circles = Spread.working;				    		
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("dialectic")) {
    		Spread.circles = Spread.working;
    		spreadLabels = dialectic;
    		mySpread = new DialecticSpread(myInt,dialectic);
    	}
	}

	public void handleSpreadSelection(int index, AdapterView<?> adapter) {
		readingPrefsEd.putInt("spread", adapter.getSelectedItemPosition());
		readingPrefsEd.commit();
		
		switch (index) {
		case 0: {
			//seqSpread();
			spreadLabels = timeArrow;
			style = "arrow";
			break;
		}
		case 1: {
			//seqSpread();
			spreadLabels = dialectic;
			style = "dialectic";
			break;
		}			
		case 2: {
			//seqSpread();
			spreadLabels = pentagram;
			style = "pentagram";
			break;
		}	
		case 3: {
			//seqSpread();
			spreadLabels = chaosStar;
			style = "chaos";
			break;
		}
		case 4: {
			//seqSpread();
			spreadLabels = celticCross;
			style = "celtic";
			break;
		}
		case 5: {
			botaSpread();		
			style = "bota";
			spreading=false;
			return;
		}	
		}
			spreading=false;
			//spread=true;
			secondSetIndex = 0;
			state = "new reading";
			begun = true;
			browsing = false;
			if (readingPrefs.getBoolean("trumps.only", false) || isTrumpsOnly())
				myInt = new BotaInt(new TarotTrumpDeck(), aq);
			else
				myInt = new BotaInt(new FullTarotDeck(), aq);
			Deck.cards = myInt.myDeck.shuffle(Deck.cards,3);
			
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
			else {
				Toast.makeText(this, "Unfortunately this reading has been lost", Toast.LENGTH_SHORT);
				return;
			}
				//mySpread = getMySeqSpread(myInt,spreadLabels,false,isTrumpsOnly());
			loaded = false;
			beginSecondStage();
	}

	private void initSpreadMenu() {
		begun = false;
		browsing = false;
		loaded = false;
		Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
		//myInt = new BotaInt(new TarotTrumpDeck(), aq);
		state = "spreadmenu";		
		
		establishMenu(R.layout.spreadmenu);
		myMenuList = (ListView) this.findViewById(R.id.spreadmenulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,spreadmenu,R.layout.listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("spreadmenu");
	}
	
	public void mute(View v) {
		muted = !muted;	
		readingPrefsEd.putBoolean("muted", muted);
		readingPrefsEd.commit();
		if (mp != null)
			if (!mp.isPlaying() && !muted){
				 mp=MediaPlayer.create(this, R.raw.background);
				 mp.start();
				 mp.setLooping(true);
			} else if (mp.isPlaying() && muted)
				mp.stop();
	}

	public void displayBackgroundColorChoice(View v) {
		ColorDialog dialog = new ColorDialog(this, true, v, displayPrefs.getInt("background.color", Color.BLACK), this, R.drawable.logo);		
		dialog.show();		
	}
	
	public void displayBackgroundImageChoice(View v) {
				
	}
	
	private void initOptionsMenu() {
		establishMenu(R.layout.optionsmenu);		
		muteToggle = (ToggleButton) this.findViewById(R.id.mute_button);
		muteToggle.setChecked(readingPrefs.getBoolean("muted", false));

		reverseToggle = (ToggleButton) this.findViewById(R.id.reversal_button);
		reverseToggle.setChecked(readingPrefs.getBoolean("reversal", false));
		reverseToggle.setClickable(true);
		reverseToggle.setOnClickListener(this);
		trumpToggle = (ToggleButton) this.findViewById(R.id.trumps_only_button);
		trumpToggle.setChecked(readingPrefs.getBoolean("trumps.only", false));
		trumpToggle.setClickable(true);
		trumpToggle.setOnClickListener(this);
		deckToggle = (ToggleButton) this.findViewById(R.id.custom_deck_button);
		deckToggle.setChecked(displayPrefs.getBoolean("custom.deck", false));
		deckToggle.setClickable(true);
		deckToggle.setOnClickListener(this);
		deckNote = (TextView) this.findViewById(R.id.custom_deck_note);		
	}
	
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent arg2) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {			
			state = priorstate;
			priorstate = "";
			infoDisplayed = false;
			dialog.dismiss();
			//displaySecondStage(secondSetIndex);
			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (state.equals("mainmenu")) {
				finish();
				return true;
			} else if (state.equals("dialog")) {
				if (priorstate.equals("secondmenu") || priorstate.equals("new reading")) {
					state = "new reading";
					priorstate = "";
					infoDisplayed = false;
					displaySecondStage(secondSetIndex);
					return true;
				} else {
					state = "mainmenu";
					spreading=false;
					//spread=false;
					reInit();
					return true;
				}
			} else if (state.equals("new reading") &! browsing) {
				leavespread = true;
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(getString(R.string.are_you_sure))
				       .setCancelable(false)
				       .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   spreading=true;
								//spread=false;
								reInitSpread(R.layout.spreadmenu);
								
				           }
				       })
				       .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   TarotBotActivity.this.leavespread = false; 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
				return true;
				
			} else if (state.equals("spreadmenu") || (state.equals("loaded") &! browsing) || state.equals("single") || (state.equals("navigate") && browsing)) {
				reInit();
				return true;
			} else if (browsing) {
				ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
				postFlip(flipper.getChildAt(0));
				navigate();
				return true;
			} else if (state.equals("secondmenu") || state.equals("navigate")) {
				state = priorstate;
				displaySecondStage(secondSetIndex);
				return true;
			} else {
				reInit();
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (state.endsWith("menu"))
				return true;
			priorstate = state;
			state = "secondmenu";
			establishMenu(R.layout.mainmenu);
			initText();	
			myMenuList = (ListView) this.findViewById(R.id.menulist);
			myMenuList.setOnItemClickListener(this);
			if ((priorstate.equals("new reading") || priorstate.equals("navigate")) &!browsing) {
				myMenuList.setAdapter(new EfficientAdapter(this,inflater,secondmenu,R.layout.listitem));			
				myMenuList.setTag("secondmenu");
			} else {
				reInit();
			}
	
			return true;
		}
		return super.onKeyDown(keyCode,event);		
	}
	@Override
	void reInit() {
		if (mySpread != null && mySpread.myview != null &! browsing)
			mySpread.clearView(mySpread.myview);
		spreading=false;
		//spread=false;
		begun = false;
		browsing = false;
		loaded=false;
    	BotaInt.loaded = false;
    	Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
		if (readingPrefs.getBoolean("trumps.only", false) || isTrumpsOnly())
			myInt = new BotaInt(new TarotTrumpDeck(), aq);
		else
			myInt = new BotaInt(new FullTarotDeck(), aq);
    	type = new ArrayList<Integer>();
		flipdex = new ArrayList<Integer>();
		state = "mainmenu";
		establishMenu(R.layout.mainmenu);
		initText();	
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,mainmenu,R.layout.listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("mainmenu");
	}
	@Override
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
		establishMenu(R.layout.mainmenu);
		
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,items,R.layout.load_listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("loadmenu");
		
	}

	

}