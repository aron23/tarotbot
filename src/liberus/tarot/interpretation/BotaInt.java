package liberus.tarot.interpretation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import liberus.tarot.os.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.deck.RiderWaiteDeck;
import liberus.tarot.os.activity.TarotBotActivity;
import liberus.tarot.querant.Querant;


public class BotaInt extends Interpretation {

	
	public static int secondSetStrongest = 79;
	
	
	public static Integer[] yod;
	public static Integer[] heh1;
	public static Integer[] vau;
	public static Integer[] heh2;

	private static int significatorIn=79;
	
	public static ArrayList<Integer> working;
	public static ArrayList<Integer> circles;
	public static boolean loaded = false;
	private static int loadedsignificator;
	
	
	public BotaInt(RiderWaiteDeck deck, Querant q) {
		super(deck);
		myDeck = deck;
		myQuerant = q;
		loaded=false;
	}
	
	public BotaInt(RiderWaiteDeck deck, Querant q, ArrayList<Integer> loading) {
		super(deck);
		myDeck = deck;
		myQuerant = q;
		working = loading;
		loaded = true;
	}
	
	public static String getGeneralInterpretation(int i, boolean reverse, Context context) {
		String returner = "\n\n";
		
		if (getTitle(i) > 0 && context.getString(getTitle(i)).length() > 0)
			returner += context.getString(getTitle(i))+"\n\n";
		if (getJourney(i) > 0 && context.getString(getJourney(i)).length() > 0)
			returner += context.getString(getJourney(i))+"\n";
		if (getTimePeriod(i) > 0 && context.getString(getTimePeriod(i)).length() > 0)
			returner += "time period: "+context.getString(getTimePeriod(i))+"\n";
		if (getAstrology(i) > 0 && context.getString(getAstrology(i)).length() > 0)
			returner += "astrology: "+context.getString(getAstrology(i))+"\n";
		if (getArchetype(i) > 0 && context.getString(getArchetype(i)).length() > 0)
			returner += "Jungian archetype: "+context.getString(getArchetype(i))+", ";
		if (getKeyword(i) > 0 && context.getString(getKeyword(i)).length() > 0)
			returner += "keyword: "+context.getString(getKeyword(i))+"\n";
		if (getElement(i) > 0 && context.getString(getElement(i)).length() > 0)
			returner += "element: "+context.getString(getElement(i))+"\n";
		if (getHebrew(i) > 0 && context.getString(getHebrew(i)).length() > 0)
			returner += "hebrew: "+context.getString(getHebrew(i))+"\n";
		returner += "\n\n";
		
		if (context.getString(getOccultTitle(i)).length() > 0)
			returner += context.getString(getOccultTitle(i))+"\n\n";
		if (context.getString(getAbst(i)).length() > 0)
			returner += context.getString(getAbst(i))+"\n\n";
		if (context.getString(getMeanings(i)).length() > 0)
			returner += context.getString(getMeanings(i))+"\n\n";
		if (context.getString(getInSpiritualMatters(i)).length() > 0)
			returner += "in spiritual matters:\n"+context.getString(getInSpiritualMatters(i))+"\n\n";
		if (context.getString(getInMaterialMatters(i)).length() > 0)
			returner += "in material matters:\n"+context.getString(getInMaterialMatters(i))+"\n\n";
		if (context.getString(getWellDignified(i)).length() > 0 &! reverse)
			returner += "When stregthened by surrounding cards: "+context.getString(getWellDignified(i))+"\n\n";
		if (context.getString(getIllDignified(i)).length() > 0 && reverse)
			returner += "When negatively impacted by surrounding cards: "+context.getString(getIllDignified(i))+"\n\n";
		
		if (context.getString(getActions(i)).length() > 0)
			returner += "related behaviors: "+context.getString(getActions(i))+"\n\n";
		
		return returner;
	}
	
	public static boolean isWellDignified(String context) {
		return (context.contains("Well-Dignified"));
	}
	public static boolean isIllDignified(String context) {
		return (context.contains("Ill-Dignified"));
	}

	public static boolean isReversed(int i) {
		return myDeck.reversed[i];
	}
	
	public static int getCardForTheDay(Context context) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.MILLISECOND,0);
		begin.set(Calendar.SECOND,0);
		begin.set(Calendar.MINUTE,0);
		begin.set(Calendar.HOUR,-16);
		TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE); 		
		Random r = new Random(begin.get(Calendar.DAY_OF_MONTH)*(begin.get(Calendar.MONTH)+1)*begin.get(Calendar.YEAR)*(Long.valueOf((tel.getLine1Number().replaceAll("\\D", "")+"1"))));
		return getCard(r.nextInt(78));		 
	}
	
	public static int getCardForTheDayIndex(Context context) {
		Calendar begin = Calendar.getInstance();		
		
		
		begin.set(Calendar.MILLISECOND,0);
		begin.set(Calendar.SECOND,0);
		begin.set(Calendar.MINUTE,0);
		begin.set(Calendar.HOUR,-16);
		//Toast.makeText(context, begin.getTime().toGMTString(), Toast.LENGTH_SHORT).show();
		TelephonyManager tel = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE); 		
		Random r = new Random(begin.get(Calendar.DAY_OF_MONTH)*(begin.get(Calendar.MONTH)+1)*begin.get(Calendar.YEAR)*(Long.valueOf((tel.getLine1Number().replaceAll("\\D", "")+"1"))));
		return r.nextInt(78);		 
	}
	
	public static boolean randomReversed(Context context) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR,0);
		begin.set(Calendar.MINUTE,0);
		begin.set(Calendar.SECOND,0);
		begin.set(Calendar.MILLISECOND,0);
		TelephonyManager tel = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE); 		
		Random r = new Random(begin.get(Calendar.DAY_OF_MONTH)*(begin.get(Calendar.MONTH)+1)*begin.get(Calendar.YEAR)*(Long.valueOf((tel.getLine1Number().replaceAll("\\D", "")+"1"))));
		return r.nextBoolean();		 
	}

	public static void setMyQuerant(Querant aq) {
		myQuerant = aq;
		
	}
}
