package liberus.tarot.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import liberus.tarot.android.R;
import liberus.tarot.android.R.array;
import liberus.tarot.android.R.drawable;
import liberus.tarot.android.R.id;
import liberus.tarot.android.R.layout;
import liberus.tarot.deck.RiderWaiteDeck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.querant.Querant;
import liberus.utils.WebUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker.OnDateChangedListener;

public class TarotBotActivity extends Activity implements OnClickListener, View.OnClickListener, OnItemSelectedListener, OnDateChangedListener {
	private DatePicker dp;
	private Spinner statusspin;
	private Button initbutton;

	private Querant aq;
	//private RelativeLayout mainlayout;
	private boolean male = false;
	private boolean partnered = false;
	private boolean unselected = true;
	private boolean firstpass=true;

	private int secondSetIndex=79;
	protected RelativeLayout secondlayout;

	private static final int SWIPE_MIN_DISTANCE = 80;
	private static final int SWIPE_MAX_OFF_PATH = 180;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final int MENU_SAVE = 0;
	private static final int MENU_SHARE = 1;
	private static final int MENU_LOAD = 2;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	//private ViewFlipper display;
	protected LayoutInflater inflater;
	protected View showing;
	//private RelativeLayout wasShowing;
	private TextView infotext;
	private Button agreebutton;
	private Button disagreebutton;
	protected TextView closure;

	private SharedPreferences mPrefs;
	private int statusselected;
	private ViewFlipper flipper = null;
	private ArrayList<Integer> type = new ArrayList<Integer>();
	private ArrayList<Integer> flipdex = new ArrayList<Integer>();
	private ArrayList<RelativeLayout> laidout = new ArrayList<RelativeLayout>();
	private BotaInt myInt;
	private String saveTitle;
	private String saveResult;
	private EditText input;
	private boolean infoDisplayed;
	public static boolean cardfortheday = false;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setFullscreen();
		setContentView(R.layout.main);
		mPrefs = getSharedPreferences("tarotbot", 0);
		inflater = LayoutInflater.from(this);
		statusspin = (Spinner) findViewById(R.id.statusspinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.status_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		statusspin.setAdapter(adapter);
		statusspin.setOnItemSelectedListener(this);
		statusspin.setSelection(3);
		statusspin.setSelection(mPrefs.getInt("querantstatus", 0));

		dp = (DatePicker)this.findViewById(R.id.birthdatepicker);

		Calendar today = Calendar.getInstance();
		// for example init to 1/27/2008, no callback 
		if (mPrefs.contains("birthyear"))
			dp.init(mPrefs.getInt("birthyear", today.get(Calendar.YEAR)), mPrefs.getInt("birthmonth", today.get(Calendar.MONTH)), mPrefs.getInt("birthday", today.get(Calendar.DAY_OF_MONTH)), this);        
		else
			dp.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), this);

		//dp.init(1976, 9, 4, this);
		laidout.add((RelativeLayout) this.findViewById(R.id.mainlayout));
		((ImageView) this.findViewById(R.id.randomcard)).setBackgroundDrawable(getResources().getDrawable(getRandomCard()));
		changeQuerant();

		initbutton = (Button) this.findViewById(R.id.initbutton);
		initbutton.setOnClickListener(this);

		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (gestureDetector.onTouchEvent(event)) {
					return true;
				}
				return false;
			}
		};
	}
	
	/* Creates the menu items */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_LOAD, 0, "Load").setIcon(android.R.drawable.ic_menu_set_as);
	    menu.add(0, MENU_SAVE, 1, "Save").setIcon(android.R.drawable.ic_menu_save);
	    menu.add(0, MENU_SHARE, 2, "Share").setIcon(android.R.drawable.ic_menu_share);
	    return true;
	}

	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case MENU_SAVE:
	        save();
	        return true;
	    case MENU_SHARE:
	        //quit();
	        return true;
	    case MENU_LOAD:
	        displaySaved();
	        return true;
	    }
	    return false;
	}
	
	private void displaySaved() {
		ArrayList<String[]> savedReadings = WebUtils.loadTarotBot(getApplicationContext());
		
		PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.individual,null),0,0);
        pw.showAtLocation(this.findViewById(R.id.activecard),Gravity.NO_GRAVITY, 20, 20);
        pw.update(50,50,300,400);
	}

	private String reversalsToString() {
		String toReturn = "";
	    for (int card: BotaInt.working) {
	    	String represent;
	    	if (BotaInt.isReversed(card))
	    		represent = "R";
	    	else
		        represent = "U";
		      toReturn += represent+",";
	    }
	    return toReturn;
	}
	
	public String spreadToString(){
	    String toReturn = "";
	    for (int card: BotaInt.working) {
	      String represent = String.valueOf(card);
	      if (represent.length() < 2)
	    	  represent = "10"+represent;
	      else
	    	  represent = "1"+represent;
	      toReturn += represent+",";
	    }
	    return toReturn;
	  }

	private void save() {
		
		saveTitle = "";
		saveResult = "";
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Label your reading");
		alert.setMessage("Message");

		// Set an EditText view to get user input 
		input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", this);
		alert.setNegativeButton("No Thanks", this); 
			 
		alert.show(); 		
	}

	private Drawable getSignificatorImage(Querant q) {
		switch (BotaInt.getSignificator()) {
		case 32: return getResources().getDrawable(R.drawable.wands_page);
		case 33: return getResources().getDrawable(R.drawable.wands_knight);
		case 34: return getResources().getDrawable(R.drawable.wands_queen);
		case 35: return getResources().getDrawable(R.drawable.wands_king);

		case 46: return getResources().getDrawable(R.drawable.cups_page);
		case 47: return getResources().getDrawable(R.drawable.cups_knight);
		case 48: return getResources().getDrawable(R.drawable.cups_queen);
		case 49: return getResources().getDrawable(R.drawable.cups_king);

		case 60: return getResources().getDrawable(R.drawable.swords_page);
		case 61: return getResources().getDrawable(R.drawable.swords_knight);
		case 62: return getResources().getDrawable(R.drawable.swords_queen);
		case 63: return getResources().getDrawable(R.drawable.swords_king);

		case 74: return getResources().getDrawable(R.drawable.pent_page);
		case 75: return getResources().getDrawable(R.drawable.pent_knight);
		case 76: return getResources().getDrawable(R.drawable.pent_queen);
		default: return getResources().getDrawable(R.drawable.pent_king);
		}

	}
	private void beginReading() {

		myInt = new BotaInt(new RiderWaiteDeck(), aq);
		setContentView(R.layout.anticipatequery);

		TextView anticipatory = (TextView) this.findViewById(R.id.anticipation);
		anticipatory.setText("The cards say:\n\n"+BotaInt.firstOperation());
		agreebutton = (Button) this.findViewById(R.id.agree);
		agreebutton.setClickable(true);
		agreebutton.setOnClickListener(this);

		disagreebutton = (Button) this.findViewById(R.id.disagree);
		disagreebutton.setClickable(true);
		disagreebutton.setOnClickListener(this);
		/*new AlertDialog.Builder(this)
	      .setMessage("The cards say:\n\n"+reading.firstOperation()+"\n\nIs this accurate?")
	      .setPositiveButton("Yes", this)
	      .setNegativeButton("No", this)
	      .show();*/

	}
	private void changeQuerant() {

		if (unselected) {			
			if (statusspin.getSelectedItem().toString().contains("Single Male")) {
				male = true;
				partnered=false;
			} else if (statusspin.getSelectedItem().toString().contains("Partnered Female")) {
				male=false;
				partnered = true;    	
			} else if (statusspin.getSelectedItem().toString().contains("Partnered Male")) {
				male = true;
				partnered = true;
			} else {
				male = false;
				partnered = false;
			}
		}
		//Toast.makeText(this, dp.getMonth(), Toast.LENGTH_SHORT).show();
		if (aq==null)
			aq = new Querant(male,partnered,new GregorianCalendar(dp.getYear(),dp.getMonth(),dp.getDayOfMonth()));
		else {
			aq.male=male;
			aq.partnered=partnered;
			aq.setBirthDate(new GregorianCalendar(dp.getYear(),dp.getMonth(),dp.getDayOfMonth()));
		}

		//mainlayout.setBackgroundDrawable(getSignificatorImage(aq));
		if(firstpass)
			//showInfo();
		firstpass=false;
	}
	private void showInfo() {
		if (firstpass) {			
			String interpretation = "Tarot Bot is a bit different than most tarot applications or even tarot readings you have probably seen.\n\n"+
			"Rather than attempting to mimic a deck of cards, Tarot Bot is an attempt to transform the archetypal divination system that is tarot cards into something much more accessible through the digital medium.\n\n"+
			"The Tarot Bot system is modeled on the book Oracle of the Tarot: A Course on Divination by Paul Foster Case.  This beta version has a complete implementation of the First and Second stage divinations as they are presented in the book along with interpretations derived from the same text.\n\n"+
			"The basic flow of the divinations is as follows:\n\n"+
			"Shuffle the Tarot, divide into four separate sets (cut the cards), and then select the set containing a card known as the 'significator' (the choice of significator depends on gender and marital status along with birthdate).\n\n"+
			"Each of the four sets represents a letter of the tetragrammaton, the holy name of Qabala, IHVH.  Each letter is ascribed a particular theme for the question at hand, whichever theme corresponds to the significator set is proposed to the querant (person with the question) and the querant is asked if they agree.\n\n"+
			"According to the text if the querant disagrees the divination should stop immediately and should not be resumed for at least two hours.\n\n"+
			"If the querant agrees with the theme proposed the second stage of divination begins.\n\n"+  
			"The set containing the significator is dealt in a circle with the significator at the top.  Depending on what card the significator is some number of cards is counted around the circle.  Wherever the counting stops is the next card in the divination.  From there you again begin counting around the circle – repeating the process until you land on the same card twice.\n\n"+
			"This series of cards (the ones landed on) represent the first story the tarot tells to answer the querant's question.  They are individual tarot are interpreted in the context of the cards which fall next to them.\n\n"+
			"Next the cards are paired beginning with the cards to the left and right of the significator and continuing until all the cards are paired or there is only one left other than the significator.  This series tells the second story to clarify the querant's question.  These are interpreted with the leftmost of the pair representing the past and the rightmost representing the future.\n\n"+
			"Rather than require the user of Tarot Bot memorize or even see the processes underlying this tarot reading technique (the shuffling, cutting, and dealing of the cards), Tarot Bot aims to provide a tarot experience that truly helps the user explore the depths tarot is able to convey.  With this as the goal, all of the design revolves around helping the user interpret and understand the archetypes tarot contains.\n\n"+
			"As Tarot Bot evolves there will be new decks and interpretations published and the final version should have many more features.\n\n"+
			"Thank you for trying this software, please visit our forum at http://liber.us and let us know what you think.\n\n";
			showing = inflater.inflate(R.layout.interpret, null);
			infotext = (TextView) showing.findViewById(R.id.interpretation);		
			infotext.setText("\n\n\n\n" +interpretation);
		} else { 
			infoDisplayed = true;
			int i = BotaInt.circles.get(secondSetIndex);
			String interpretation = BotaInt.secondOperationInterpretation(i,getApplicationContext());
			showing = inflater.inflate(R.layout.interpret, null);
			infotext = (TextView) showing.findViewById(R.id.interpretation);		
			infotext.setText("\n\n\n\nposition " + secondSetIndex +interpretation);			
		}
		/*closure = (Button) showing.findViewById(R.id.closeinterpretation);
		closure.setClickable(true);
		closure.setOnClickListener(this);*/
		ArrayList<View> toShow = new ArrayList<View>();
		toShow.add(showing);
		if (firstpass)
			laidout.get(0).addView(showing);
		else
			laidout.get(secondSetIndex+1).addView(showing);		
	}

	protected void setFullscreen() { 
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	}  

	private void incrementSecondSet(int index) {		
		int previous = index;
		if (index >= (BotaInt.circles.size()-1)) 
			secondSetIndex=0; 
		else 
			secondSetIndex++;
		flipper.startFlipping();
		preFlip(flipper.getChildAt(secondSetIndex));
		if (flipper != null) {
			flipper.setInAnimation(inFromRightAnimation());
	        flipper.setOutAnimation(outToLeftAnimation());	        
	        flipper.showNext();
		}
		postFlip(flipper.getChildAt(previous));
		flipper.stopFlipping();
	}


	private void decrementSecondSet(int index) {		
		int previous = index;
		if (index-1 < 0) {
			secondSetIndex = (BotaInt.circles.size()-1);
		}  else
			secondSetIndex--;
		flipper.startFlipping();
		preFlip(flipper.getChildAt(secondSetIndex));
		if (flipper != null) {
			flipper.setInAnimation(inFromLeftAnimation());
	        flipper.setOutAnimation(outToRightAnimation());
	        flipper.showPrevious();
		}
		postFlip(flipper.getChildAt(previous));
		flipper.stopFlipping();
	}
	private void beginSecondStage() {
		myInt = new BotaInt(new RiderWaiteDeck(), aq);
		BotaInt.firstOperation();
		BotaInt.secondOperation(getApplicationContext());
		secondSetIndex = 0;
		displaySecondStage(secondSetIndex);		
		Toast.makeText(this, "swipe your finger up or down to display interpretation, left or right to navigate the spread", 60).show();
	}

	private void displaySecondStage(int indexin) {
		setContentView(R.layout.transition);
		flipper = (ViewFlipper) this.findViewById(R.id.flipper);
		
		int counter = 1;  
		for (int index:BotaInt.circles) {			
			View activeView = inflater.inflate(R.layout.secondset, null);
			if (activeView.findViewById(R.id.secondsetlayout) != null)
				laidout.add((RelativeLayout) activeView.findViewById(R.id.secondsetlayout));
			else continue;
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
		View v = flipper.getChildAt(0);
		/*ImageView lefty = (ImageView)v.findViewById(R.id.left);
		ImageView ls = (ImageView)v.findViewById(R.id.leftshadow);
		{
			
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(BotaInt.getCardToTheLeft(flipdex.get(flipper.indexOfChild(v)))));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postScale(.3f, .3f);

			if (BotaInt.myDeck.reversed[BotaInt.getCardToTheLeft(flipdex.get(flipper.indexOfChild(v)))]) 			
				mtx.postRotate(180);

			Bitmap scaledBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(scaledBMP);
			lefty.setBackgroundDrawable(bmd);
			ls.setBackgroundColor(Color.BLACK);
			ls.setMinimumHeight(bmd.getBitmap().getHeight());
			ls.setMinimumWidth(bmd.getBitmap().getWidth());
			ls.setAlpha(100);
		}

		ImageView righty = (ImageView)v.findViewById(R.id.right);
		ImageView rs = (ImageView)v.findViewById(R.id.rightshadow);
		{
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(BotaInt.getCardToTheRight(flipdex.get(flipper.indexOfChild(v)))));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postScale(.3f, .3f);
			if (BotaInt.myDeck.reversed[BotaInt.getCardToTheRight(flipdex.get(flipper.indexOfChild(v)))]) 			
				mtx.postRotate(180);

			Bitmap scaledBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(scaledBMP);	
			righty.setBackgroundDrawable(bmd);
			rs.setBackgroundColor(Color.BLACK);
			rs.setMinimumHeight(bmd.getBitmap().getHeight());
			rs.setMinimumWidth(bmd.getBitmap().getWidth());
			rs.setAlpha(100);
		}*/
		
		ImageView divine = (ImageView) v.findViewById(R.id.divine);
		if (BotaInt.myDeck.reversed[flipdex.get(flipper.indexOfChild(v))]) {			
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(flipdex.get(flipper.indexOfChild(v))));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postRotate(180);
			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
			divine.setBackgroundDrawable(bmd);
		} else
			divine.setBackgroundDrawable(getResources().getDrawable(BotaInt.getCard(flipdex.get(flipper.indexOfChild(v)))));
		flipper.setAnimationCacheEnabled(false);
		flipper.setPersistentDrawingCache(ViewGroup.PERSISTENT_NO_CACHE);
		flipper.setDrawingCacheEnabled(false);
		flipper.setDisplayedChild(0);
		
		
	}

	

	private int getRandomCard() {
		return BotaInt.getCard((int)(Math.random() * 78));
	}


	@Override
	public void onClick(View v) {	
		if (v.equals(this.findViewById(R.id.initbutton)))
			beginSecondStage();//beginReading();	
		else if (v.equals(agreebutton))
			beginSecondStage();
		else if (v.equals(disagreebutton))
			this.finish();
		/*else if (v.equals(closure))
			redisplay();*/
		else
			incrementSecondSet(secondSetIndex);
	}
	protected void redisplay() {
		if (laidout.size() > 1)
			laidout.get(secondSetIndex+1).removeView(showing);
		else
			laidout.get(0).removeView(showing);
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

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (dialog instanceof AlertDialog) {
				switch (which) {
				case -1: { 					
					saveTitle = input.getText().toString();
					saveTitle = saveTitle.replaceAll("\\s+", "+");					
					WebUtils.saveTarotBot(spreadToString(),reversalsToString(),saveTitle,getApplicationContext());																
					break; 
				}
				case -2: { 
					WebUtils.saveTarotBot(spreadToString(),reversalsToString(),saveTitle,getApplicationContext());
					break; 
				}
			}
			Toast.makeText(this, saveResult, Toast.LENGTH_SHORT).show();
		} else {
			switch (which) {
				case -1: beginSecondStage(); break;
				case -2: Toast.makeText(this, "The cards are not ready to answer your question wait at least two hours before trying again.", Toast.LENGTH_LONG).show(); break;
			}		
		}
	}
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		statusselected = statusspin.getSelectedItemPosition();
		if (statusspin.getSelectedItem().toString().contains("Single Male")) {
			male = true;
			partnered=false;
		} else if (statusspin.getSelectedItem().toString().contains("Partnered Female")) {
			male=false;
			partnered = true;    	
		} else if (statusspin.getSelectedItem().toString().contains("Partnered Male")) {
			male = true;
			partnered = true;
		} else {
			male = false;
			partnered = false;
		}
		unselected=false;
		changeQuerant();

	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		changeQuerant();

	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (flipper != null) {
			for (int i = 0; i < flipper.getChildCount(); i++) {
				View v = flipper.getChildAt(i);
				v.destroyDrawingCache();
				v = null;
			}
			flipper.removeAllViews();
		}
		//BotaInt.nullify();
		laidout = null;
		inflater=null;
		gestureDetector = null;
		gestureListener = null;
		flipper = null;
		myInt = null;
		aq=null;
		System.gc();
		finish();
	}
	@Override
	protected void onPause() {
		super.onPause();
		
		SharedPreferences.Editor ed = mPrefs.edit();
		ed.putInt("birthyear", aq.birth.get(Calendar.YEAR));
		ed.putInt("birthmonth", aq.birth.get(Calendar.MONTH));
		ed.putInt("birthday", aq.birth.get(Calendar.DAY_OF_MONTH));
		ed.putInt("querantstatus", statusselected);

		ed.commit();

		if (flipper != null)
			flipper.stopFlipping();
				
	}

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			//try {
			if (Math.abs(e1.getY() - e2.getY()) < SWIPE_MAX_OFF_PATH) {
				// right to left swipe
				if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					if (laidout.get(secondSetIndex+1).findViewById(R.id.interpretation) != null)						
						redisplay();
					incrementSecondSet(secondSetIndex);
					return true;
					// left to right swipe
				}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					if (laidout.get(secondSetIndex+1).findViewById(R.id.interpretation) != null)						
						redisplay();
					decrementSecondSet(secondSetIndex);
					return true;
				}
			}

			if (Math.abs(e1.getX() - e2.getX()) < SWIPE_MAX_OFF_PATH) {
				// vertical swipe
				if (laidout.get(secondSetIndex+1).findViewById(R.id.interpretation) == null)
					showInfo();
				else
					redisplay();
				return true;		         
			}
			//} catch (Exception e) {
			// System.out.println(e.getMessage());
			//}
			return false;
		}

	}

	
	public void preFlip(View v) {
		
				/*ImageView lefty = (ImageView)v.findViewById(R.id.left);
				ImageView ls = (ImageView)v.findViewById(R.id.leftshadow);
				{
					
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(BotaInt.getCardToTheLeft(flipdex.get(flipper.indexOfChild(v)))));
					int w = bmp.getWidth();
					int h = bmp.getHeight();
					Matrix mtx = new Matrix();
					mtx.postScale(.3f, .3f);
		
					if (BotaInt.myDeck.reversed[BotaInt.getCardToTheLeft(flipdex.get(flipper.indexOfChild(v)))]) 			
						mtx.postRotate(180);
		
					Bitmap scaledBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
					BitmapDrawable bmd = new BitmapDrawable(scaledBMP);
					lefty.setBackgroundDrawable(bmd);
					//lefty.setClickable(true);
					//lefty.setOnClickListener(this);
					ls.setBackgroundColor(Color.BLACK);
					ls.setMinimumHeight(bmd.getBitmap().getHeight());
					ls.setMinimumWidth(bmd.getBitmap().getWidth());
					ls.setAlpha(100);
				}
		
				ImageView righty = (ImageView)v.findViewById(R.id.right);
				ImageView rs = (ImageView)v.findViewById(R.id.rightshadow);
				{
					
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(BotaInt.getCardToTheRight(flipdex.get(flipper.indexOfChild(v)))));
					int w = bmp.getWidth();
					int h = bmp.getHeight();
					Matrix mtx = new Matrix();
					mtx.postScale(.3f, .3f);
					if (BotaInt.myDeck.reversed[BotaInt.getCardToTheRight(flipdex.get(flipper.indexOfChild(v)))]) 			
						mtx.postRotate(180);
		
					Bitmap scaledBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
					BitmapDrawable bmd = new BitmapDrawable(scaledBMP);	
					righty.setBackgroundDrawable(bmd);
					//righty.setClickable(true);
					//righty.setOnClickListener(this);
					rs.setBackgroundColor(Color.BLACK);
					rs.setMinimumHeight(bmd.getBitmap().getHeight());
					rs.setMinimumWidth(bmd.getBitmap().getWidth());
					rs.setAlpha(100);
				}*/
				ImageView divine = (ImageView) v.findViewById(R.id.divine);
				if (BotaInt.myDeck.reversed[flipdex.get(flipper.indexOfChild(v))]) {			
					
					Bitmap bmp = BitmapFactory.decodeResource(getResources(), BotaInt.getCard(flipdex.get(flipper.indexOfChild(v))));
					int w = bmp.getWidth();
					int h = bmp.getHeight();
					Matrix mtx = new Matrix();
					mtx.postRotate(180);
					Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
					BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
					divine.setBackgroundDrawable(bmd);
				} else {
					
					divine.setBackgroundDrawable(getResources().getDrawable(BotaInt.getCard(flipdex.get(flipper.indexOfChild(v)))));
				}			
	}
	
	public void postFlip(View v) {

		if (type.get(flipper.indexOfChild(v)) == 0) {
			v = inflater.inflate(R.layout.secondset, null);
		} else {
			v = inflater.inflate(R.layout.secondsetpair, null);
		}
	}
}