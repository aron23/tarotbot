package liberus.tarot.spread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.querant.Querant;
import android.content.Context;

public class BotaSpread extends Spread {
	
	private static int significatorIn;
	public static Integer[] heh2;
	public static Integer[] vau;
	public static Integer[] heh1;
	public static Integer[] yod;
	private Integer secondSetStrongest;

	public BotaSpread(BotaInt in) {
		super(in);
	}
	
	
	protected static boolean hasSignificator(Integer[] deck) {
		for (int i = 0; i < deck.length; i++)
			if (isSignificator(deck[i]))
				return true;
		return false;
	}

	public void operate(Context context,boolean loading) {
		if (!loading) {
			Integer[] shuffled = myDeck.shuffle(new Integer[78],1);
			
			int firstCut = Deck.cut(shuffled);
			Integer[] yodPrime = new Integer[firstCut];
			Integer[] vauPrime = new Integer[shuffled.length-firstCut];
			for (int i=0; i < firstCut; i++)
				yodPrime[i] = shuffled[i];
			int vauCount = 0;
			for (int i=firstCut; i < shuffled.length; i++) {
				vauPrime[vauCount] = shuffled[i];
				vauCount++;
			}
			
			int secondCut = Deck.cut(yodPrime);		
			heh1 = new Integer[secondCut];
			yod = new Integer[yodPrime.length-secondCut];
			for (int i=0; i < secondCut; i++)
				heh1[i] = yodPrime[i];
			int yodCount = 0;
			for (int i=secondCut; i < yodPrime.length; i++) {
				yod[yodCount] = yodPrime[i];
				yodCount++;
			}
			
			int thirdCut = Deck.cut(vauPrime);		
			vau = new Integer[vauPrime.length-thirdCut];
			heh2 = new Integer[thirdCut];
			for (int i=0; i < thirdCut; i++)
				heh2[i] = vauPrime[i];
			vauCount = 0;
			for (int i=thirdCut; i < vauPrime.length; i++) {
				vau[vauCount] = vauPrime[i];
				vauCount++;
			}
			String toreturn = "";
			if (hasSignificator(yod)) {
				System.err.println("yod");
				significatorIn=0;
				working = new ArrayList<Integer> (Arrays.asList(yod));
			} else if (hasSignificator(heh1)) {
				System.err.println("heh1");
				significatorIn=1;		
				working = new ArrayList<Integer> (Arrays.asList(heh1));
			} else if (hasSignificator(vau)) {
				System.err.println("vau");
				significatorIn=2;		
				working = new ArrayList<Integer> (Arrays.asList(vau));
			} else if (hasSignificator(heh2)) {
				System.err.println("heh2");
				significatorIn=3;		
				working = new ArrayList<Integer> (Arrays.asList(heh2));
			} else {
				System.err.println("missing significator");
			}
		}
		
		
		int primaryCircle;
		int significatorIndex=79;				
		ArrayList<Integer> toChew = new ArrayList(working);
		Iterator<Integer> circler = working.iterator();
		
		circles = new ArrayList<Integer>();
		int circled = 79;
		
		boolean circling = true;
		boolean begun = false;
		ArrayList<Integer> hits = new ArrayList<Integer>();

		int circle_index = working.indexOf(getSignificator());
		while (circling) {
			if (circle_index == working.indexOf(getSignificator()) && circles.size() < 1) {
				begun = true;
				circles.add(working.get(circle_index));
				hits.add(circle_index);
				
			} else if (begun) {
				if (hits.contains(circle_index)) {
					secondSetStrongest = working.get(circle_index);  
					circling = false;
					continue;
				}
				circles.add(working.get(circle_index));
				hits.add(circle_index);
			} 
			circle_index += context.getResources().getInteger(BotaInt.getSecondOperationCount(working.get(circle_index)));
			if (circle_index >= working.size())
				circle_index -= working.size();
		
		}
		myLabels = new String[circles.size()];
		for (int i = 0; i < circles.size(); i++) {
			myLabels[i] = context.getString(R.string.position)+" "+i;
		}
		 
	}

	public String getInterpretation(int circled, Context appcontext) {
		double factor = (double)12/working.size();
		int sig = working.indexOf(getSignificator());
		int pos = working.indexOf(circled);
		int offset = pos-sig;
		int house;
		if (offset > 0)
			house = (int) Math.floor(factor*(offset+1));
		else
			house = (int) Math.floor(factor*(working.size()+offset));
		int[] context = getContext(circled, appcontext);
		String returner = "";
		
		if (isSignificator(circled)) {
			String pre = getPre(appcontext);
			String post = appcontext.getString(R.string.querant_label)+"<br/>";
			
			if (pre.length() > 0)
				post = pre+"<br/><br/>"+post;
			if (circled==BotaInt.secondSetStrongest)
				post += "<br/><i>"+appcontext.getString(R.string.personal_label)+"</i><br/>";
			
			returner = post;
			
			return returner;
		}
		returner += "<big><b>"+appcontext.getString(BotaInt.getTitle(circled))+"</big></b><br/>";
		if (BotaInt.getKeyword(circled) > 0 && appcontext.getString(BotaInt.getKeyword(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getKeyword(circled))+"<br/>";
		if (BotaInt.getJourney(circled) > 0 && appcontext.getString(BotaInt.getJourney(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getJourney(circled))+"<br/>";
		
		if (circled==BotaInt.secondSetStrongest)
			returner += "<b>"+appcontext.getString(R.string.significant_label)+"</b><br/>";
		
		if (BotaInt.getAbst(circled) > 0 && appcontext.getString(BotaInt.getAbst(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.general_label)+"</i>: " + appcontext.getString(BotaInt.getAbst(circled))+"<br/>";
		if (significatorIn == 1 && BotaInt.getInSpiritualMatters(circled) > 0 && appcontext.getString(BotaInt.getInSpiritualMatters(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getInSpiritualMatters(circled))+"<br/>";
		else if (significatorIn == 3 && BotaInt.getInMaterialMatters(circled) > 0 && appcontext.getString(BotaInt.getInMaterialMatters(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getInMaterialMatters(circled))+"<br/>";
		else if (BotaInt.isWellDignified(context) &! BotaInt.isIllDignified(context) && BotaInt.getWellDignified(circled) > 0 && appcontext.getString(BotaInt.getWellDignified(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getWellDignified(circled))+"<br/>";
		else if (BotaInt.isIllDignified(context) &! BotaInt.isWellDignified(context) && BotaInt.getIllDignified(circled) > 0 && appcontext.getString(BotaInt.getIllDignified(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getIllDignified(circled))+"<br/>";
		else if (BotaInt.getMeanings(circled) > 0 && appcontext.getString(BotaInt.getMeanings(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getMeanings(circled))+"<br/>";
		if (BotaInt.getOppositionNumber(circled) > 0) {
			List oppNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getOppositionNumber(circled)));
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (oppNumList != null && oppNumList.contains(lefty))
				returner += appcontext.getString(R.string.has_been_opposed_label)+": "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (oppNumList != null && oppNumList.contains(righty))
				returner += appcontext.getString(R.string.will_be_opposed_label)+": "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(righty)]+"<br/>";
		}
		if (BotaInt.getReinforcementNumber(circled) > 0) {
			List reNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getReinforcementNumber(circled)));
			
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (reNumList != null && reNumList.contains(lefty))
				returner += appcontext.getString(R.string.has_been_reinforced_label)+": "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (reNumList != null && reNumList.contains(righty))
				returner += appcontext.getString(R.string.will_be_reinforced_label)+": "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(righty)]+"<br/>";
		}
		/*if (getActions(circled) > 0 && appcontext.getString(getActions(circled)).length() > 0)
			returner += appcontext.getString(getActions(circled))+"<br/><br/>";*/
		
		//returner += context;
		if (myDeck.isReversed(circled))
			returner += "<br/>"+appcontext.getString(R.string.reversed_label);
		return returner;
	}

	
	
	

	public static int getSignificator() {
		int num = 0;

		switch (Interpretation.myQuerant.getElement()) {
		case 0: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =35;
					else if (!Interpretation.myQuerant.male) 
						num =34;
				} else {
					if (Interpretation.myQuerant.male)
						num =33;
					else if (!Interpretation.myQuerant.male)
						num =32;
				} break;				
		case 1: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =49;
					else if (!Interpretation.myQuerant.male)
						num =48;
				} else {
					if (Interpretation.myQuerant.male)
						num =47;
					else if (!Interpretation.myQuerant.male)
						num =46;
				} break;				
		case 2: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =63;
					else if (!Interpretation.myQuerant.male)
						num =62;
				} else {
					if (Interpretation.myQuerant.male)
						num =61;
					else if (!Interpretation.myQuerant.male)
						num =60;
				} break;
		case 3: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =77;
					else if (!Interpretation.myQuerant.male)
						num =76;
				} else {
					if (Interpretation.myQuerant.male)
						num =75;
					else if (!Interpretation.myQuerant.male)
						num =74;
				} break;				
		}		
		return num;
	}
	
	protected static boolean isSignificator(int card) {
		
		switch (Interpretation.myQuerant.getElement()) {
		case 0: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 35)
						return true;
					else if (!Interpretation.myQuerant.male && card == 34)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 33)
						return true;
					else if (!Interpretation.myQuerant.male && card == 32)
						return true;
				}
				break;
		case 1: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 49)
						return true;
					else if (!Interpretation.myQuerant.male && card == 48)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 47)
						return true;
					else if (!Interpretation.myQuerant.male && card == 46)
						return true;
				}
				break;
		case 2: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 63)
						return true;
					else if (!Interpretation.myQuerant.male && card == 62)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 61)
						return true;
					else if (!Interpretation.myQuerant.male && card == 60)
						return true;
				}
				break;
		case 3: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 77)
						return true;
					else if (!Interpretation.myQuerant.male && card == 76)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 75)
						return true;
					else if (!Interpretation.myQuerant.male && card == 74)
						return true;
				}
				break;				
		}
		
		return false;
	}
	

}
