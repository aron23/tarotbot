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
import liberus.utils.EfficientAdapter;
import liberus.utils.MyGestureDetector;
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
import android.telephony.TelephonyManager;
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


public class OldTarotBotActivity extends AbstractTarotBotActivity  {




	private Dialog helper;
	
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//tarotbottype = "liberus.tarot.android.activity";
		int in = 5;
		
		setFullscreen();
		
		inflater = LayoutInflater.from(this);
		state = "mainmenu";
		setContentView(R.layout.mainmenu);
		initText();	
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,mainmenu,R.layout.listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("mainmenu");		
		
		readingPrefs = getSharedPreferences("tarotbot.reading", 0);
		readingPrefsEd = readingPrefs.edit();
		gestureDetector = new GestureDetector(new MyGestureDetector(this));
		gestureListener = getGestureListener(gestureDetector);
		initSaved("tarotbot");		
	}
	
	
	protected void launchBrowse() {
		state = "loaded";
		
		spreading=false;
		
		secondSetIndex = 0;

		begun = true;
		browsing = true;
		loaded=true;
    	BotaInt.loaded = true;
		myInt = new BotaInt(new FullTarotDeck(), aq);

		ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
    	for(int card: FullTarotDeck.cards) {
    		reversals.add(false);
    	}
    	
    	Interpretation.myDeck = new FullTarotDeck(reversals.toArray(new Boolean[0]));	
    	
    	
		mySpread = new BrowseSpread(myInt,isTrumpsOnly());
		
		
		type = new ArrayList<Integer>();
		flipdex = new ArrayList<Integer>();
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
		dialectic = res.getStringArray(R.array.dialectic);
		chaosStar = res.getStringArray(R.array.chaosStar);
		pentagram = res.getStringArray(R.array.pentagram);
		celticCross = res.getStringArray(R.array.celticCross);
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  if (spreading) {
		  reInitSpread(R.layout.spreadmenu);
		  
	  } else if (!begun && !browsing) {
		  reInit();
	  } else {
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
	
	public void showInfo(int type) {
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);

		if (type == Configuration.ORIENTATION_PORTRAIT) {
			infoDisplayed = true;
			int i = Spread.circles.get(secondSetIndex);
			String interpretation = mySpread.getInterpretation(i,getApplicationContext());
			showing = inflater.inflate(R.layout.interpretation, null);
			infotext = (TextView) showing.findViewById(R.id.interpretation);		
			infotext.setText(Html.fromHtml(interpretation));
			
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//			builder.setView(showing);
			
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
	
	
	
	public void onClick(View v) {	
		if (v.equals(this.findViewById(R.id.initbotabutton))) {
			state = "new reading";
			begun = true;
            browsing = false;
            changeQuerant();
            myInt = new BotaInt(new FullTarotDeck(), aq);
            mySpread = new BotaSpread(myInt);
            beginSecondStage();     
		} else if (v.equals(this.findViewById(R.id.menulist))) {
			showing = inflater.inflate(R.layout.spreadmenu, null);
			ListView spreadlist = (ListView) showing.findViewById(R.id.spreadmenulist);		
			spreadlist.setAdapter(new EfficientAdapter(this,inflater,spreadmenu,R.layout.listitem));
			spreadlist.setOnItemSelectedListener(this);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setView(showing);
			
			Dialog interpretor = new Dialog(this,android.R.style.Theme);
			interpretor.setContentView(showing);
			interpretor.show();
			
		} else if (v.equals(this.findViewById(R.id.reversal_button))) {
			if (!init) {
				readingPrefsEd.putBoolean("reversal", reverseToggle.isChecked());
				readingPrefsEd.commit();
			}
		} else if (v instanceof ImageView && v.getId() > -1) {
			if (navigator != null) {
				navigator.dismiss();
				navigator = null;				
			}
			if (browsing) {
				if (Interpretation.getCardIndex(v.getId()) > 0)
					secondSetIndex = Interpretation.getCardIndex(v.getId());
				else
					secondSetIndex = Spread.working.size();
			} else {
//				if (v.getId() == 0)
//					secondSetIndex = Spread.working.size()-1;
//				else
					secondSetIndex = v.getId();
					
			}
			state = "new reading";
			priorstate = "";
			displaySecondStage(secondSetIndex);
			
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
					        File gpxfile = new File(root, "tarotbot.gothic.store");
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
					        File gpxfile = new File(root, "tarotbot.gothic.store");
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
				    	state = "loaded";
				    	//Interpretation.myDeck = ;	
				    	myInt = new BotaInt(new FullTarotDeck(reversals.toArray(new Boolean[0])),aq);
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
				    		mySpread = new SeqSpread(myInt,single,false);
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
				    	}
				    	spreading=false;
						begun = true;
						browsing = false;
				    	//new BotaInt(new FullTarotDeck(reversals.toArray(new Boolean[0])),new Querant(significator),working);
				    	beginSecondStage();
				    	break;
					}
				}
			
				
				if (sharing) {
					saveResult = WebUtils.saveTarotBot(spreadToString(),deckToString(),reversalsToString(),saveTitle,style,getApplicationContext(),getMyType());
					String url = WebUtils.bitly(saveResult);
					share(getString(R.string.share_subject),WebUtils.bitly(saveResult));
				}
		} 
	}
	

	public void onItemSelected(AdapterView<?> adapter, View list, int arg2,
			long arg3) {
		changeQuerant();

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
			helper = new Dialog(this,android.R.style.Theme);
			switch (index) {
				case 0: 
					//seqSpread();
					spreadLabels = single;
					style = "single";
					spreading=false;
					
					secondSetIndex = 0;
					state = "single";
					begun = true;
					browsing = false;
					myInt = new BotaInt(new FullTarotDeck(), aq);
					Integer[] shuffled = Interpretation.myDeck.shuffle(Deck.cards,3);
					Deck.cards = shuffled;
					mySpread = new SeqSpread(myInt,spreadLabels,false);
					loaded = false;
					ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
			    	for(int card: Deck.cards) {
			    		reversals.add(false);
			    	}
			    	
			    	Interpretation.myDeck = new FullTarotDeck(reversals.toArray(new Boolean[0]));
					beginSecondStage();
					break;				
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
					displaySaved();
			        return;	
			    case 4: 
			    	showing = inflater.inflate(R.layout.interpretation, null);
					infotext = (TextView) showing.findViewById(R.id.interpretation);		
					infotext.setText(Html.fromHtml(getString(R.string.about_tarot)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the Tarot");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 5: 	 
					showing = inflater.inflate(R.layout.interpretation, null);
					infotext = (TextView) showing.findViewById(R.id.interpretation);		
					infotext.setText(Html.fromHtml(getString(R.string.about_artist)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the Artist");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 6:
			    	showing = inflater.inflate(R.layout.interpretation, null);
					infotext = (TextView) showing.findViewById(R.id.interpretation);		
					infotext.setText(Html.fromHtml(getString(R.string.about_app)+getString(R.string.market_link)+getString(R.string.other_apps)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the App");
					helper.setContentView(showing);
					helper.show();
					break;		
				}			
			

		} else if (adapter.getTag().equals("loadmenu")) {//.getContentDescription().equals("querant")
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
	    		mySpread = new ChaosSpread(myInt,pentagram);
	    		spreadLabels = pentagram;
	    	} else if (savedReadings.get(savedList.get(savedList.indexOf(sortedSaved.get(index)))).get("type").equals("single")) {
	    		mySpread = new SeqSpread(myInt,single,false);
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
	    	spreading=false;
			begun = true;
			browsing = false;
	    	//new BotaInt(new FullTarotDeck(reversals.toArray(new Boolean[0])),new Querant(significator),working);
	    	beginSecondStage();
	    	
			

		}  else if (adapter.getTag().equals("secondmenu")) {//.getContentDescription().equals("querant")
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
			    	spreading=false;
					
					begun = false;
					browsing = false;
					loaded=false;
			    	BotaInt.loaded = false;
			    	Spread.circles = new ArrayList<Integer>();
					Spread.working = new ArrayList<Integer>();
					myInt = new BotaInt(new FullTarotDeck(), aq);
			    	type = new ArrayList<Integer>();
					flipdex = new ArrayList<Integer>();
					
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
			    case 4: 
			    	showing = inflater.inflate(R.layout.interpretation, null);
					infotext = (TextView) showing.findViewById(R.id.interpretation);		
					infotext.setText(Html.fromHtml(getString(R.string.about_tarot)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the Tarot");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 5: 	 
					showing = inflater.inflate(R.layout.interpretation, null);
					infotext = (TextView) showing.findViewById(R.id.interpretation);		
					infotext.setText(Html.fromHtml(getString(R.string.about_artist)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the Artist");
					helper.setContentView(showing);
					helper.show();
					break;
			    case 6:
			    	showing = inflater.inflate(R.layout.interpretation, null);
					infotext = (TextView) showing.findViewById(R.id.interpretation);		
					infotext.setText(Html.fromHtml(getString(R.string.about_app)+getString(R.string.market_link)+getString(R.string.other_apps)));
					Linkify.addLinks(infotext, Linkify.ALL);
					infotext.setLinksClickable(true);
					infotext.setMovementMethod(LinkMovementMethod.getInstance());
					helper.setTitle("About the App");
					helper.setContentView(showing);
					helper.show();
					break;
				}
			
			

		} else if (adapter.getTag().equals("spreadmenu")) {//.getContentDescription().equals("querant")			
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
					//botaSpread();		
					style = "bota";
					spreading=false;
					return;
				}
			}
				spreading=false;
				
				secondSetIndex = 0;
				state = "new reading";
				begun = true;
				browsing = false;
				myInt = new BotaInt(new FullTarotDeck(), aq);
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
					mySpread = new SeqSpread(myInt,spreadLabels,false);
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
				
					reInit();
					return true;
				}
			} else if (state.equals("new reading")) {
				spreading=true;
				
				reInitSpread(R.layout.spreadmenu);
				return true;
			} else if (state.equals("spreadmenu") || state.equals("loaded") || state.equals("single")) {
				spreading=false;
				
				begun = false;
				browsing = false;
				loaded=false;
		    	BotaInt.loaded = false;
		    	Spread.circles = new ArrayList<Integer>();
				Spread.working = new ArrayList<Integer>();
				myInt = new BotaInt(new FullTarotDeck(), aq);
		    	type = new ArrayList<Integer>();
				flipdex = new ArrayList<Integer>();
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
			if (state.equals("secondmenu")||state.equals("mainmenu"))
				return true;
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
	@Override
	void reInit() {
		state = "mainmenu";
		setContentView(R.layout.mainmenu);
		initText();	
		myMenuList = (ListView) this.findViewById(R.id.menulist);
		myMenuList.setAdapter(new EfficientAdapter(this,inflater,mainmenu,R.layout.listitem));
		myMenuList.setOnItemClickListener(this);
		myMenuList.setTag("mainmenu");
	}


	@Override
	public long getHiResZipSize() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getMyType() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getMyFolder() {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Spread getMySeqSpread(Interpretation myInt, String[] labels,
			boolean cardOfTheDay, boolean trumpsOnly) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Spread getMyBrowseSpread(Interpretation in, boolean trumps) {
		// TODO Auto-generated method stub
		return null;
	}

}