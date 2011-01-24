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


public class TarotBotGothicActivity extends AbstractTarotBotActivity  {

	
	
	protected String[] mystic;
	protected String[] vampire;
	protected String[] gothicPentagram;
	protected String[] arch;

	private Dialog helper;
	private boolean leavespread;
	
	/** Called when the activity is first created. */
	
	
	protected void launchBrowse() {
		state = "loaded";
		
		spreading=false;
		//spread=true;
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
    	
    	
		mySpread = new GothicBrowseSpread(myInt);
		
		
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


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	  if (spreading) {
		  reInitSpread(R.layout.spreadmenu);		  
	  } else if (!begun && !browsing) {
		  reInit();
	  } else if (!state.equals("navigate")) {
		  rotateDisplay();
	  }
	}

	
	
	
	protected void changeQuerant() {
	}
	
	public void showInfo(int type) {
		ViewSwitcher flipper = (ViewSwitcher) this.findViewById(R.id.flipper);

		if (type == Configuration.ORIENTATION_PORTRAIT) {
			infoDisplayed = true;
			int i = GothicSpread.circles.get(secondSetIndex);
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
			int i = GothicSpread.circles.get(secondSetIndex);
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
		if (v.equals(this.findViewById(R.id.menulist))) {

			
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
				if (Interpretation.getCardIndex(v.getId()) >= 0)
					secondSetIndex = Interpretation.getCardIndex(v.getId());
				else
					secondSetIndex = GothicSpread.working.size();
				state = "loaded";
				priorstate = "";
				displaySecondStage(secondSetIndex);
			} else {
//				if (v.getId() == 0)
//					secondSetIndex = GothicSpread.working.size()-1;
//				else
					secondSetIndex = v.getId();
					state = priorstate;
					priorstate = "";
					displaySecondStage(secondSetIndex);
			}
			
			
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
			helper = new Dialog(this,android.R.style.Theme);
			switch (index) {
				case 0: 
					//seqSpread();
					spreadLabels = single;
					style = "single";
					spreading=false;
					//spread=true;
					secondSetIndex = 0;
					state = "single";
					begun = true;
					browsing = true;
					myInt = new BotaInt(new FullTarotDeck(), aq);
					Deck.cards = Interpretation.myDeck.shuffle(Deck.cards,3);
					mySpread = new GothicSeqSpread(myInt,spreadLabels);
					loaded = false;
					ArrayList<Boolean> reversals = new ArrayList<Boolean>(); 
			    	for(int card: Deck.cards) {
			    		reversals.add(false);
			    	}
			    	
			    	//Interpretation.myDeck = new FullTarotDeck(reversals.toArray(new Boolean[0]));
			    	
					beginSecondStage();
					break;				
				case 1:					
					launchBrowse();
			        return;
			    case 2:	
			    	initSpreadMenu();
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
			    	reInitSpread(R.layout.spreadmenu);								
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
				myInt = new BotaInt(new FullTarotDeck(), aq);
				Deck.cards = Interpretation.myDeck.shuffle(Deck.cards,3);
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
					mySpread = new SeqSpread(myInt,spreadLabels,false);
				loaded = false;
				beginSecondStage();
		} else if (adapter.getTag().equals("loadmenu")) {//.getContentDescription().equals("querant") {
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
	}
	private void initSpreadMenu() {
		begun = false;
		browsing = false;
		loaded = false;
		Spread.circles = new ArrayList<Integer>();
		Spread.working = new ArrayList<Integer>();
		myInt = new BotaInt(new FullTarotDeck(), aq);
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
				builder.setMessage("Are you sure you want to leave your reading?")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   spreading=true;
								//spread=false;
								reInitSpread(R.layout.spreadmenu);
								
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   TarotBotGothicActivity.this.leavespread = false; 
				        	   dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
				return true;
				
			} else if (state.equals("spreadmenu") || (state.equals("loaded") &! browsing) || state.equals("single") || (state.equals("navigate") && browsing)) {
				spreading=false;
				//spread=false;
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
			} else if (browsing) {
				navigate();
				return true;
			} else if (state.equals("secondmenu") || state.equals("navigate")) {
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
			if ((priorstate.equals("new reading") || priorstate.equals("navigate")) &!browsing) {
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

}