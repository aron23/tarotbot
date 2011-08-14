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
import liberus.tarot.spread.gothic.GothicArch;
import liberus.tarot.spread.gothic.GothicArrowSpread;
import liberus.tarot.spread.gothic.GothicBotaSpread;
import liberus.tarot.spread.gothic.GothicBrowseSpread;
import liberus.tarot.spread.gothic.GothicCelticSpread;
import liberus.tarot.spread.gothic.GothicChaosSpread;
import liberus.tarot.spread.gothic.GothicDialecticSpread;
import liberus.tarot.spread.gothic.GothicPentagram;
import liberus.tarot.spread.gothic.GothicSeqSpread;
import liberus.tarot.spread.gothic.GothicSpread;
import liberus.tarot.spread.gothic.MysticSeven;
import liberus.tarot.spread.gothic.VampireKiss;
import liberus.utils.EfficientAdapter;
import liberus.utils.WebUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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


public class TarotBotGothicActivity extends TarotBotActivity  {

	
	
	protected String[] mystic;
	protected String[] vampire;
	protected String[] gothicPentagram;
	protected String[] arch;

	private Dialog helper;
	private boolean leavespread;
	
	/** Called when the activity is first created. */
	
	

	protected void initText() {
//		whatUrl = getString(R.string.whatUrl);
//		howUrl = getString(R.string.howUrl);
//		tarotUrl = getString(R.string.tarotUrl);
//		updatesUrl = getString(R.string.updatesUrl);
//		helpUrls = new String[]{whatUrl,howUrl,tarotUrl,updatesUrl};
		res = getResources();
		mainmenu = res.getStringArray(R.array.gothic_main_menu);
		secondmenu = res.getStringArray(R.array.gothic_second_menu);
		spreadmenu = res.getStringArray(R.array.gothic_spread_array);
		single = res.getStringArray(R.array.single);
		timeArrow = res.getStringArray(R.array.timeArrow);
		dialectic = res.getStringArray(R.array.dialectic);
		arch = res.getStringArray(R.array.gothic_arch);
		gothicPentagram = res.getStringArray(R.array.gothic_pentagram);
		mystic = res.getStringArray(R.array.gothic_mystic);
		vampire = res.getStringArray(R.array.gothic_vampire);
		chaosStar = res.getStringArray(R.array.chaosStar);
		celticCross = res.getStringArray(R.array.gothic_celticCross);
	}

	
	protected void changeQuerant() {
	}
	@Override
	public Spread getMySeqSpread(Interpretation myInt, String[] labels, boolean cardOfTheDay,boolean trumpsOnly) {
		return new GothicSeqSpread(myInt,labels,cardOfTheDay,trumpsOnly);
	}
	@Override
	public Spread getMyBrowseSpread(Interpretation myInt, boolean trumpsOnly) {
		return new GothicBrowseSpread(myInt,trumpsOnly);
	}
//	public void showInfo(int type) {
//		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);
//
//		if (type == Configuration.ORIENTATION_PORTRAIT) {
//			infoDisplayed = true;
//			int i = GothicSpread.circles.get(secondSetIndex);
//			String interpretation = mySpread.getInterpretation(i,getApplicationContext());
//			showing = inflater.inflate(R.layout.interpretation, null);
//			TextView title = (TextView)showing.findViewById(R.id.title);
//			if (secondSetIndex < mySpread.myLabels.length)
//				title.setText(mySpread.myLabels[secondSetIndex]);
//			else
//				title.setText("");
//			TextView cardlabel = (TextView)showing.findViewById(R.id.cardlabel);
//			
//			if (mySpread.myDeck.isReversed(i)) 
//				cardlabel.setText(mySpread.getCardTitle(i,getApplicationContext())+" reversed");
//			else
//				cardlabel.setText(mySpread.getCardTitle(i,getApplicationContext()));
//			
//			infotext = (TextView) showing.findViewById(R.id.interpretation);		
//			infotext.setText(Html.fromHtml(interpretation));
//			
//			ImageView card = (ImageView) showing.findViewById(R.id.cardkey);
//			card.setClickable(true);
//			card.setOnClickListener(this);
//			
//			ImageView spread = (ImageView) showing.findViewById(R.id.spreadkey);
//			if (Spread.circles.size() > 1) {
//				spread.setClickable(true);
//				spread.setOnClickListener(this);
//			} else {
//				spread.setAlpha(0);
//			}
//			
//			interpretor = new Dialog(this,R.style.interpretation_style);
//			
//			interpretor.setContentView(showing);			
//			interpretor.setOnKeyListener(this);			
//			interpretor.show();
//		} else if (type == Configuration.ORIENTATION_LANDSCAPE) {
//			int i = GothicSpread.circles.get(secondSetIndex);
//			String interpretation = mySpread.getInterpretation(i,getApplicationContext());
//			View v = flipper.getCurrentView();
//			infotext = (TextView)v.findViewById(R.id.interpretation);		
//			
//			if (mySpread.myLabels[secondSetIndex] != null)
//				infotext.setText(Html.fromHtml("<br/><i>"+mySpread.myLabels[secondSetIndex]+"</i><br/><br/>"+interpretation));
//			else
//				infotext.setText(Html.fromHtml(interpretation));
//		}			
//	}

	@Override
	public String getStorageFile() {
		return "tarotbot.gothic.store";
	}

	@Override
	public void handleLoadMenu(int index, AdapterView<?> adapter) {
		Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
		flipdex = new ArrayList<Integer>();
		//ArrayList<String[]> deck = WebUtils.loadTarotBotReading(getApplicationContext(),Integer.valueOf(savedReadings.get(which)[0]));
		ArrayList<String[]> deck = new ArrayList<String[]>();
    	ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
    	ArrayList<Integer> working = new ArrayList<Integer>();
    	int significator = Integer.valueOf(savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("significator"));
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
    	//Interpretation.myDeck = ;	
    	myInt = new BotaInt(new FullTarotDeck(reversals.toArray(new Boolean[0])),aq);
    	Spread.working = working;
    	BotaInt.loaded = true;
    	if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("arch")) {
    		mySpread = new GothicArch(myInt,arch);
    		spreadLabels = arch;
    		Spread.circles = Spread.working;				    		
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("gothicPentagram")) {
    		mySpread = new GothicPentagram(myInt,gothicPentagram);
    		spreadLabels = gothicPentagram;
    		Spread.circles = Spread.working;
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("mystic")) {
    		mySpread = new MysticSeven(myInt,mystic);
    		spreadLabels = mystic;
    		Spread.circles = Spread.working;
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("vampire")) {
    		mySpread = new VampireKiss(myInt,vampire);
    		spreadLabels = vampire;
    		Spread.circles = Spread.working;
    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("celtic")) {
    		mySpread = new GothicCelticSpread(myInt,celticCross);
    		spreadLabels = celticCross;
    		Spread.circles = Spread.working;				    		
    	} 
    	
    	
    	spreading=false;
		begun = true;
		browsing = false;
    	//new BotaInt(new FullTarotDeck(reversals.toArray(new Boolean[0])),new Querant(significator),working);
    	beginSecondStage();
	}
	@Override
	public void handleSpreadSelection(int index, AdapterView<?> adapter) {
		readingPrefsEd.putInt("spread", adapter.getSelectedItemPosition());
		readingPrefsEd.commit();
		switch (index) {
			case 4: {
				//seqSpread();
				spreadLabels = arch;
				style = "arch";
				break;
			}
			case 3: {
				//seqSpread();
				spreadLabels = gothicPentagram;
				style = "gothicPentagram";
				break;
			}
			case 2: {
				//seqSpread();
				spreadLabels = vampire;
				style = "vampire";
				break;
			}
			case 1: {
				//seqSpread();
				spreadLabels = mystic;
				style = "mystic";
				break;
			}
			case 0: {
				//seqSpread();
				spreadLabels = celticCross;
				style = "celtic";
				break;
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
				mySpread = new GothicArrowSpread(myInt,timeArrow);
			else if (style.equals("dialectic"))
				mySpread = new GothicDialecticSpread(myInt,dialectic);
			else if (style.equals("arch"))
				mySpread = new GothicArch(myInt,arch);
			else if (style.equals("gothicPentagram"))
				mySpread = new GothicPentagram(myInt,gothicPentagram);
			else if (style.equals("mystic"))
				mySpread = new MysticSeven(myInt,mystic);
			else if (style.equals("vampire"))
				mySpread = new VampireKiss(myInt,vampire);
			else if (style.equals("chaos"))
				mySpread = new GothicChaosSpread(myInt,chaosStar);
			else if (style.equals("celtic"))
				mySpread = new GothicCelticSpread(myInt,celticCross);
			else
				mySpread = new GothicSeqSpread(myInt,spreadLabels,false,isTrumpsOnly());
			loaded = false;
			beginSecondStage();
	}
	
	private String getGothicType(String type) {
		if (type.equals("arch"))
			return "Gothic Arch";
		else if (type.equals("gothicPentagram"))
			return "Pentagram";
		else if (type.equals("mystic"))
			return "Mystic Seven";
		else if (type.equals("vampire"))
			return "Vampire\'s Kiss";
		else if (type.equals("celtic"))
			return "Celtic Cross";
		else
			return "";
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
				readingLabels.add(reading.get("date")+"\n"+reading.get("label")+": "+getGothicType(reading.get("type")));
			else
				readingLabels.add(reading.get("date")+"\n"+getGothicType(reading.get("type")));
		}
		String[] items = readingLabels.toArray(new String[0]);
		
		state = "spreadmenu";
		establishMenu(R.layout.mainmenu);
		
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,items,R.layout.load_listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("loadmenu");
		
	}

	@Override
	public String getMyType() {
		return "liberus.tarot.monolith.gothic";
	}

	@Override
	public long getHiResZipSize() {
		// TODO Auto-generated method stub
		return 15805970;
	}

	@Override
	public String getMyFolder() {
		// TODO Auto-generated method stub
		return "TarotBotGothic";
	}
	@Override
	public void setBackground(View v) {
		
	}


	public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}

}