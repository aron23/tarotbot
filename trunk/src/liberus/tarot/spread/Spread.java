package liberus.tarot.spread;

import java.util.ArrayList;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.Interpretation;
import android.content.Context;

public abstract class Spread {
	
	public Deck myDeck;
	protected static Interpretation myInt; 
	public String[] myLabels;
	public static ArrayList<Integer> working = new ArrayList<Integer>();
	public static ArrayList<Integer> circles = new ArrayList<Integer>();
	public Spread(Interpretation inInt) {		
		myInt = inInt;
		myDeck = Interpretation.myDeck;
	}
	public abstract void operate(Context context,boolean loaded);
	public abstract String getInterpretation(int card, Context context);
	public abstract String getPre(Context context);
	public abstract String getPost(Context context);
}
