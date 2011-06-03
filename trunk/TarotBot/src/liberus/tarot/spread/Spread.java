package liberus.tarot.spread;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.android.noads.R;
import liberus.utils.MyProgressDialog;
import liberus.utils.TarotBotManager;
import liberus.utils.WebUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public abstract class Spread {
	
	protected static final int HIGHRES = 32;
	protected static final int MIDRES = 24;
	public Deck myDeck;
	public ViewGroup myview;
	public int paged = 0;
	protected static Interpretation myInt; 
	public String[] myLabels;
	public static ArrayList<Integer> working = new ArrayList<Integer>();
	public static ArrayList<Integer> circles = new ArrayList<Integer>();
	public boolean browsing = false;
	private ViewGroup mybrowseview;
	public Spread(Interpretation inInt) {		
		myInt = inInt;
		myDeck = Interpretation.myDeck;
	}
	public abstract void operate(Context context,boolean loaded);
	public abstract String getInterpretation(int card, Context context);
	public abstract int getLayout();
	
	public void clearView(ViewGroup v) {
		
		int children = v.getChildCount();
		for (int i = 0; i < children; i++) {
			View toClear = v.getChildAt(i);
			if (toClear instanceof ImageView) {
				if (((ImageView)toClear).getDrawable() instanceof BitmapDrawable)
					((BitmapDrawable)((ImageView)toClear).getDrawable()).getBitmap().recycle();
				v.removeView(toClear);
				toClear.destroyDrawingCache();
				if (((ImageView)toClear).getDrawable() != null)
					((ImageView)toClear).getDrawable().setCallback(null);
				((ImageView)toClear).setImageDrawable(null);
				((ImageView)toClear).getResources().flushLayoutCache();
				((ImageView)toClear).destroyDrawingCache();
				toClear=null;
			} else if (toClear instanceof ViewGroup) { 
				clearView((ViewGroup)toClear);
			}
		}
		v.destroyDrawingCache();		
	}
	
	public View navigate(View layout, AbstractTarotBotActivity abstractTarotBotActivity,Context ctx) {		
		if (myview != null) {
			clearView(myview);
		}
			myview = (ViewGroup) populateSpread(layout,abstractTarotBotActivity,ctx);
			return myview;
		
	}
	
	public abstract View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx);
	
	public String getPre(Context context) {
		int trumps = 0;
		int wands = 0;
		int cups = 0;
		int swords = 0;
		int pentacles = 0;
		int courted = 0;
		int aces = 0;
		int kings = 0;
		int queens = 0;
		int knights = 0;
		int pages =0;
		int tens = 0;
		int nines = 0;
		int eights =0 ;
		int sevens = 0;
		int sixes = 0;
		int fives =0;
		int fours = 0;
		int threes = 0;
		int twos = 0;
		
		
		
		for (int i: working) {
			int cardIndex = context.getResources().getInteger(Interpretation.getCardNum(i))-100;
			//String title = context.getString(getEnTitle(i)); 
			if (cardIndex >= 22 && cardIndex < 36)
				wands++;
			if (cardIndex >= 36 && cardIndex < 50)
				cups++;
			if (cardIndex >= 50 && cardIndex < 64)
				swords++;
			if (cardIndex >= 64)
				pentacles++;
			if (cardIndex < 22) {
				trumps++;
				if (Deck.firetrumps.contains(i))
					wands++;
				if (Deck.watertrumps.contains(i))
					cups++;
				if (Deck.airtrumps.contains(i))
					swords++;
				if (Deck.earthtrumps.contains(i))
					pentacles++;
			}
			else if (cardIndex == 35 || cardIndex == 49 || cardIndex == 63 || cardIndex == 77) {
				kings++;
				courted++;
			} else if (cardIndex == 34 || cardIndex == 48 || cardIndex == 62 || cardIndex == 76) {
				queens++;
				courted++;
			} else if (cardIndex == 33 || cardIndex == 47 || cardIndex == 61 || cardIndex == 75) {
				knights++;
				courted++;
			} else if (cardIndex == 32 || cardIndex == 46 || cardIndex == 60 || cardIndex == 74) {
				pages++;
				courted++;
			} else if (cardIndex == 31 || cardIndex == 45 || cardIndex == 59 || cardIndex == 73)
				tens++;
			else if (cardIndex == 30 || cardIndex == 44 || cardIndex == 58 || cardIndex == 72)
				nines++;
			else if (cardIndex == 29 || cardIndex == 43 || cardIndex == 57 || cardIndex == 71)
				eights++;
			else if (cardIndex == 28 || cardIndex == 42 || cardIndex == 56 || cardIndex == 70)
				sevens++;
			else if (cardIndex == 27 || cardIndex == 41 || cardIndex == 55 || cardIndex == 69)
				sixes++;
			else if (cardIndex == 26 || cardIndex == 40 || cardIndex == 54 || cardIndex == 68)
				fives++;
			else if (cardIndex == 25 || cardIndex == 39 || cardIndex == 53 || cardIndex == 67)
				fours++;
			else if (cardIndex == 24 || cardIndex == 38 || cardIndex == 52 || cardIndex == 66)
				threes++;
			else if (cardIndex == 23 || cardIndex == 37 || cardIndex == 51 || cardIndex == 65)
				twos++;
			else if (cardIndex == 22 || cardIndex == 36 || cardIndex == 50 || cardIndex == 64)
				aces++;
		}
		String returner = "";
		if (wands > cups && wands > swords && wands > pentacles)
			returner  += "<br/><i>"+context.getString(R.string.wand_major_label)+"</i><br/>";
		if (cups > wands && cups > swords && cups > pentacles)
			returner += "<br/><i>"+context.getString(R.string.cup_major_label)+"</i><br/>";
		if (swords > cups && swords > wands && swords > pentacles)
			returner += "<br/><i>"+context.getString(R.string.sword_major_label)+"</i><br/>";
		if (pentacles > cups && pentacles > swords && pentacles > wands)
			returner += "<br/><i>"+context.getString(R.string.pent_major_label)+"</i><br/>";
		if (trumps > wands && trumps > cups && trumps > swords && trumps > pentacles)
			returner += "<br/><i>"+context.getString(R.string.trump_major_label)+"</i><br/>";
		if (courted > (trumps+wands+cups+swords+pentacles-courted))
			returner += "<br/><i>"+context.getString(R.string.court_major_label)+"</i><br/>";
		
		if (kings == 4)
			returner += "<br/><i>"+context.getString(R.string.four_kings)+"</i><br/>";
		if (kings == 3)
			returner += "<br/><i>"+context.getString(R.string.three_kings)+"</i><br/>";
		
		if (queens == 4)
			returner += "<br/><i>"+context.getString(R.string.four_queens)+"</i><br/>";
		if (queens == 3)
			returner += "<br/><i>"+context.getString(R.string.three_queens)+"</i><br/>";
		
		if (knights == 4)
			returner += "<br/><i>"+context.getString(R.string.four_knights)+"</i><br/>";
		if (knights == 3)
			returner += "<br/><i>"+context.getString(R.string.three_knights)+"</i><br/>";
		
		if (pages == 4)
			returner += "<br/><i>"+context.getString(R.string.four_pages)+"</i><br/>";
		if (pages == 3)
			returner += "<br/><i>"+context.getString(R.string.three_pages)+"</i><br/>";
		
		if (tens == 4)
			returner += "<br/><i>"+context.getString(R.string.four_tens)+"</i><br/>";
		if (tens == 3)
			returner += "<br/><i>"+context.getString(R.string.three_tens)+"</i><br/>";
		
		if (nines == 4)
			returner += "<br/><i>"+context.getString(R.string.four_nines)+"</i><br/>";
		if (nines == 3)
			returner += "<br/><i>"+context.getString(R.string.three_nines)+"</i><br/>";
		
		if (eights == 4)
			returner += "<br/><i>"+context.getString(R.string.four_eights)+"</i><br/>";
		if (eights == 3)
			returner += "<br/><i>"+context.getString(R.string.three_eights)+"</i><br/>";
		
		if (sevens == 4)
			returner += "<br/><i>"+context.getString(R.string.four_sevens)+"</i><br/>";
		if (sevens == 3)
			returner += "<br/><i>"+context.getString(R.string.three_sevens)+"</i><br/>";
		
		if (sixes == 4)
			returner += "<br/><i>"+context.getString(R.string.four_sixes)+"</i><br/>";
		if (sixes == 3)
			returner += "<br/><i>"+context.getString(R.string.three_sixes)+"</i><br/>";
		
		if (fives == 4)
			returner += "<br/><i>"+context.getString(R.string.four_fives)+"</i><br/>";
		if (fives == 3)
			returner += "<br/><i>"+context.getString(R.string.three_fives)+"</i><br/>";
		
		if (fours == 4)
			returner += "<br/><i>"+context.getString(R.string.four_fours)+"</i><br/>";
		if (fours == 3)
			returner += "<br/><i>"+context.getString(R.string.three_fours)+"</i><br/>";
		
		if (threes == 4)
			returner += "<br/><i>"+context.getString(R.string.four_threes)+"</i><br/>";
		if (threes == 3)
			returner += "<br/><i>"+context.getString(R.string.three_threes)+"</i><br/>";
		
		if (twos == 4)
			returner += "<br/><i>"+context.getString(R.string.four_twos)+"</i><br/>";
		if (twos == 3)
			returner += "<br/><i>"+context.getString(R.string.three_twos)+"</i><br/>";
		
		if (aces == 4)
			returner += "<br/><i>"+context.getString(R.string.four_aces)+"</i><br/>";
		if (aces == 3)
			returner += "<br/><i>"+context.getString(R.string.three_aces)+"</i><br/>";
		
		
		return returner;
	}
		
	private ImageView postPositioning(int index, int rotation, boolean changed, Matrix mtx, Bitmap bmp, int w, int h, ImageView toPlace) {
		if (myDeck.reversed[index]) {		
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
		return toPlace;
	}
	
	private Bitmap preImagePlacement(int index, Context con) {
		SharedPreferences displayPrefs = con.getSharedPreferences("tarotbot.display", 0);
		Bitmap bmp = null;
		
		BitmapFactory.Options options;
		options=new BitmapFactory.Options();	
    	options.inDither=false;
	   
		if (browsing)
			if (TarotBotManager.hasEnoughMemory(HIGHRES,con))
				options.inSampleSize = 2;
//			else if (TarotBotManager.hasEnoughMemory(MIDRES,con))
//				options.inSampleSize = 3;
			else
				options.inSampleSize = getMySampleSize();
		if (bmp == null) {	
			File customFile = new File(Environment.getExternalStorageDirectory()+"/tarotbot.custom/"+Interpretation.getCardName(index).replaceAll("(\\.jpg)", "_th$1"));
	        if (customFile.exists() &! TarotBotManager.hasEnoughMemory(HIGHRES,con) && displayPrefs.getBoolean("custom.deck", false)) 
	        	bmp = BitmapFactory.decodeFile(customFile.getPath());
	        else
	        	customFile = new File(Environment.getExternalStorageDirectory()+"/tarotbot.custom/"+Interpretation.getCardName(index));
	        
	        if (bmp == null && customFile.exists() && displayPrefs.getBoolean("custom.deck", false)) 
	        	bmp = BitmapFactory.decodeFile(customFile.getPath());
	        
	        if (bmp == null && TarotBotManager.hasEnoughMemory(HIGHRES,con))
				bmp = BitmapFactory.decodeResource(con.getResources(), BotaInt.getCard(index),options);
			else if (bmp == null)
				bmp = BitmapFactory.decodeResource(con.getResources(), BotaInt.getCardThumb(index),options);
	    }		    
		return bmp;
	}
	
	public int getMySampleSize() {
		// TODO Auto-generated method stub
		return 1;
	}
	public ImageView placeImage(int index, ImageView toPlace, Context con) {
		
        Bitmap bmp = preImagePlacement(index,con);
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
		
		return postPositioning(index, rotation, changed, mtx, bmp, w, h, toPlace);
	}
	
	public ImageView placeLandscapeImage(int index, ImageView toPlace, Context con) {
		Bitmap bmp = preImagePlacement(index,con);
		int w = bmp.getWidth();
        int h = bmp.getHeight();
        
        boolean changed = false;
        int rotation = 0;
        
        Matrix mtx = new Matrix();
        int diff = h-w;
        
        if (diff > (h/4)*-1) {
        	rotation+=90;
        	changed = true;        	
        }		
        return postPositioning(index, rotation, changed, mtx, bmp, w, h, toPlace);
	}
	
	public ImageView placeCustomImage(int angle, int index, ImageView toPlace, Context con) {
		Bitmap bmp = null;
		BitmapFactory.Options options;
		options=new BitmapFactory.Options();
		options.inDither=false;
	    
		//if (Runtime.getRuntime().maxMemory() < 20165824)// && 
		if (browsing)
			if (TarotBotManager.hasEnoughMemory(HIGHRES,con))
				options.inSampleSize = 2;
//			else if (TarotBotManager.hasEnoughMemory(MIDRES,con))
//				options.inSampleSize = 3;
//			else
//				options.inSampleSize = 4;
		if (bmp == null) {	
			if (TarotBotManager.hasEnoughMemory(HIGHRES,con))
				bmp = BitmapFactory.decodeResource(con.getResources(), BotaInt.getCard(index),options);
			else
				bmp = BitmapFactory.decodeResource(con.getResources(), BotaInt.getCardThumb(index),options);
	    }
		
		
		//bmp = BitmapFactory.decodeResource(con.getResources(), Interpretation.getCard(index),options);
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
		
		if (Interpretation.myDeck.reversed[index]) {		
			mtx = new Matrix();
			mtx.postRotate(180);
			bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
		} 
		
		w = bmp.getWidth();
		h = bmp.getHeight();
		
		mtx = new Matrix();
		mtx.postRotate(angle);
		bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
				
		BitmapDrawable bmd = new BitmapDrawable(bmp);			
		toPlace.setImageDrawable(bmd);
		return toPlace;
	}
	
	public String getPost(Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static int getCardToTheRight(Integer mid) {
		if (working.indexOf(mid)+1 < working.size())			
			return working.get(working.indexOf(mid)+1);
		else
			return working.get(0);
	}

	public static int getCardToTheLeft(Integer mid) {
		if (working.indexOf(mid)-1 >= 0)			
			return working.get(working.indexOf(mid)-1);
		else
			return working.get(working.size()-1);
	}
	
	protected static int[] getContext(int mid, Context context) {

		Integer right;
		Integer left;
		int[] dignity = new int[]{0,0};
		
		String entitle = context.getString(Interpretation.getEnTitle(mid));
		
		if (working.indexOf(mid)+1 < working.size())			
			right = working.get(working.indexOf(mid)+1);
		else
			right = working.get(0);
		
		if (working.indexOf(mid)-1 >= 0)			
			left = working.get(working.indexOf(mid)-1);
		else
			left = working.get(working.size()-1);
						
		Matcher suitmatch = Interpretation.suitpat.matcher(entitle);
		String type;
		if (suitmatch.find()) 
			type = suitmatch.group(1);
		else {
			if (Deck.firetrumps.contains(mid))
				type = "Wands";
			else if (Deck.watertrumps.contains(mid))
				type = "Cups";
			else if (Deck.airtrumps.contains(mid))
				type = "Swords";
			else 
				type = "Pentacles";
		}
		
		String entitleLeft = context.getString(Interpretation.getEnTitle(left));
		suitmatch = Interpretation.suitpat.matcher(entitleLeft);
		String lefttype;
		if (suitmatch.find()) 
			lefttype = suitmatch.group(1);
		else {
			if (Deck.firetrumps.contains(left))
				lefttype = "Wands";
			else if (Deck.watertrumps.contains(left))
				lefttype = "Cups";
			else if (Deck.airtrumps.contains(left))
				lefttype = "Swords";
			else 
				lefttype = "Pentacles";
		}
		
		String entitleRight = context.getString(Interpretation.getEnTitle(right));
		suitmatch = Interpretation.suitpat.matcher(entitleRight);
		String righttype;
		if (suitmatch.find()) 
			righttype = suitmatch.group(1);
		else { 
			if (Deck.firetrumps.contains(right))
				righttype = "Wands";
			else if (Deck.watertrumps.contains(right))
				righttype = "Cups";
			else if (Deck.airtrumps.contains(right))
				righttype = "Swords";
			else 
				righttype = "Pentacles";
		}
		
		if (type.equals("Wands")) {
			if ((lefttype.equals("Cups"))&&(righttype.equals("Cups")))
				return new int[]{-1,-1};
			else if (lefttype.equals("Cups"))
					dignity[0] = -1;
			else if (righttype.equals("Cups"))
				dignity[1] = -1;
			if ((lefttype.equals("Swords")||lefttype.equals("Pentacles"))&&(righttype.equals("Swords")||righttype.equals("Pentacles")))
				return new int[]{1,1};
			else if (lefttype.equals("Swords")||lefttype.equals("Pentacles"))
					dignity[0] = 1;
			else if (righttype.equals("Swords")||righttype.equals("Pentacles"))
				dignity[1] = 1;
		}
		else if (type.equals("Cups")) {
			if ((lefttype.equals("Wands"))&&(righttype.equals("Wands")))
				return new int[]{-1,-1};
			else if (lefttype.equals("Wands"))
					dignity[0] = -1;
			else if (righttype.equals("Wands"))
				dignity[1] = -1;
			if ((lefttype.equals("Swords")||lefttype.equals("Pentacles"))&&(righttype.equals("Swords")||righttype.equals("Pentacles")))
				return new int[]{1,1};
			else if (lefttype.equals("Swords")||lefttype.equals("Pentacles"))
					dignity[0] = 1;
			else if (righttype.equals("Swords")||righttype.equals("Pentacles"))
				dignity[1] = 1;
		}
		else if (type.equals("Swords")) {
			if ((lefttype.equals("Pentacles"))&&(righttype.equals("Pentacles")))
				return new int[]{-1,-1};
			else if (lefttype.equals("Pentacles"))
					dignity[0] = -1;
			else if (righttype.equals("Pentacles"))
				dignity[1] = -1;
			if ((lefttype.equals("Wands")||lefttype.equals("Cups"))&&(righttype.equals("Wands")||righttype.equals("Cups")))
				return new int[]{1,1};
			else if (lefttype.equals("Wands")||lefttype.equals("Cups"))
					dignity[0] = 1;
			else if (righttype.equals("Wands")||righttype.equals("Cups"))
				dignity[1] = 1;
		}
		else if (type.equals("Pentacles")) {
			if ((lefttype.equals("Swords"))&&(righttype.equals("Swords")))
				return new int[]{-1,-1};
			else if (lefttype.equals("Swords"))
					dignity[0] = -1;
			else if (righttype.equals("Swords"))
				dignity[1] = -1;
			if ((lefttype.equals("Wands")||lefttype.equals("Cups"))&&(righttype.equals("Wands")||righttype.equals("Cups")))
				return new int[]{1,1};
			else if (lefttype.equals("Wands")||lefttype.equals("Cups"))
					dignity[0] = 1;
			else if (righttype.equals("Wands")||righttype.equals("Cups"))
				dignity[1] = 1;
		}
		return dignity;
	}
	public String getCardTitle(int i, Context applicationContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
