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

import liberus.tarot.android.R;
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
	

	
	public static boolean isWellDignified(int[] context) {
		return (context[0] == 1 || context[1] == 1);
	}
	public static boolean isIllDignified(int[] context) {
		return (context[0] == -1 || context[1] == -1);
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
