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
import android.content.SharedPreferences;
//import android.telephony.TelephonyManager;
import android.widget.Toast;

import liberus.tarot.deck.Deck;
import liberus.tarot.deck.FullTarotDeck;
import liberus.tarot.os.activity.TarotBotActivity;
import liberus.tarot.android.noads.R;
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
	
	
	public BotaInt(Deck deck, Querant q) {
		super(deck);
		myQuerant = q;
		loaded=false;
	}
	
	public BotaInt(Deck deck, Querant q, ArrayList<Integer> loading) {
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

	public boolean isReversed(int i) {
		return myDeck.reversed[i];
	}
	
	public static int getCardForTheDay(Context context, int seed) {
		return getCard(getRandom(context).nextInt(78));		 
	}
	
	public static int getCardForTheDay(Context context, int seed, boolean trumpsonly) {
		if (trumpsonly)
			return getCard(getRandom(context).nextInt(22));
		else
			return getCard(getRandom(context).nextInt(78));
	}
	
	public static int getCardIndexForTheDay(Context context, int seed) {
		return getCardIndex(getRandom(context).nextInt(78));		 
	}
	
	public static Random getRandom(Context context) {
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.MILLISECOND,0);
		begin.set(Calendar.SECOND,0);
		begin.set(Calendar.MINUTE,0);
		begin.set(Calendar.HOUR,0);		
		//TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		//String seed = tel.getDeviceId();
		
		SharedPreferences readingPrefs = context.getSharedPreferences("tarotbot.reading", 0);
		int seedy = readingPrefs.getInt("seed", new Random().nextInt());
		
		SharedPreferences.Editor readingPrefsEd = readingPrefs.edit();
		readingPrefsEd.putInt("seed", seedy);
		readingPrefsEd.commit();
		
		String seed = String.valueOf(seedy);
		
		if (seed == null || seed.length() < 1 || seed.matches("^0+$"))
			seed = "10000";
		seed = seed.replaceAll("[\\D0]", "1");
		while (seed.length() < 10)
			seed += "0";
		return new Random(Integer.valueOf(seed.substring(seed.length()-8))*(begin.get(Calendar.DAY_OF_MONTH))*(begin.get(Calendar.MONTH)+1)*begin.get(Calendar.YEAR));
	}
	
	public static int getCardForTheDayIndex(Context context, int seed) {
		 		
		return getRandom(context).nextInt(78);		 
	}
	
	public static boolean randomReversed(Context context, int seed) {

		return getRandom(context).nextBoolean();		 
	}

	public static void setMyQuerant(Querant aq) {
		myQuerant = aq;
	}

	
}
