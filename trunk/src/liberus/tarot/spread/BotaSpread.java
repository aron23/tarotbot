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
		String context = getContext(circled, appcontext);
		String returner = "";
		
		if (isSignificator(circled)) {
			String pre = getPre(appcontext);
			String post = "This card represents you.\n";
			
			if (pre.length() > 0)
				post = pre+"\n\n"+post;
			if (circled==BotaInt.secondSetStrongest)
				post += "this reading is particularly personal\n";
			
			returner = post;
			
			return returner;
		}
		returner += appcontext.getString(BotaInt.getTitle(circled))+":\n";
		if (BotaInt.getKeyword(circled) > 0 && appcontext.getString(BotaInt.getKeyword(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getKeyword(circled))+"\n";
		if (BotaInt.getJourney(circled) > 0 && appcontext.getString(BotaInt.getJourney(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getJourney(circled))+"\n";
		
		if (circled==BotaInt.secondSetStrongest)
			returner += "this card is the most significant of the reading\n";
		
		if (BotaInt.getAbst(circled) > 0 && appcontext.getString(BotaInt.getAbst(circled)).length() > 0)
			returner += "\nIn general: " + appcontext.getString(BotaInt.getAbst(circled))+"\n";
		if (significatorIn == 1 && BotaInt.getInSpiritualMatters(circled) > 0 && appcontext.getString(BotaInt.getInSpiritualMatters(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(BotaInt.getInSpiritualMatters(circled))+"\n";
		else if (significatorIn == 3 && BotaInt.getInMaterialMatters(circled) > 0 && appcontext.getString(BotaInt.getInMaterialMatters(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(BotaInt.getInMaterialMatters(circled))+"\n";
		else if (BotaInt.isWellDignified(context) &! BotaInt.isIllDignified(context) && BotaInt.getWellDignified(circled) > 0 && appcontext.getString(BotaInt.getWellDignified(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(BotaInt.getWellDignified(circled))+"\n";
		else if (BotaInt.isIllDignified(context) &! BotaInt.isWellDignified(context) && BotaInt.getIllDignified(circled) > 0 && appcontext.getString(BotaInt.getIllDignified(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(BotaInt.getIllDignified(circled))+"\n";
		else if (BotaInt.getMeanings(circled) > 0 && appcontext.getString(BotaInt.getMeanings(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(BotaInt.getMeanings(circled))+"\n";
		if (BotaInt.getOppositionNumber(circled) > 0) {
			List oppNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getOppositionNumber(circled)));
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (oppNumList != null && oppNumList.contains(lefty))
				returner += "Has been opposed by: "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(lefty)]+"\n";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (oppNumList != null && oppNumList.contains(righty))
				returner += "Will be opposed by: "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(righty)]+"\n";
		}
		if (BotaInt.getReinforcementNumber(circled) > 0) {
			List reNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getReinforcementNumber(circled)));
			
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (reNumList != null && reNumList.contains(lefty))
				returner += "Has been reinforced by: "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(lefty)]+"\n";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (reNumList != null && reNumList.contains(righty))
				returner += "Will be reinforced by: "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(righty)]+"\n";
		}
		/*if (getActions(circled) > 0 && appcontext.getString(getActions(circled)).length() > 0)
			returner += appcontext.getString(getActions(circled))+"\n\n";*/
		
		//returner += context;
		if (myDeck.isReversed(circled))
			returner += "\nbeing reversed the energy of this card is muted, its potential is there but is either not currently being expressed or is effectively suppressed";
		return returner;
	}

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
			int cardIndex = context.getResources().getInteger(BotaInt.getCardNum(i))-100;
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
			returner  += "there will be much mental energy expended in the matter, some opposition, and possible quarrels\n\n";
		if (cups > wands && cups > swords && cups > pentacles)
			returner += "intense emotional element is present; in general, intimate pleasure, social activity\n\n";
		if (swords > cups && swords > wands && swords > pentacles)
			returner += "sickness, sadness, trouble, or death\n\n";
		if (pentacles > cups && pentacles > swords && pentacles > wands)
			returner += "the emphasis will be on business, money or possessions\n\n";
		if (trumps > wands && trumps > cups && trumps > swords && trumps > pentacles)
			returner += "strong forces are at work in the matter under consideration, likely beyond the one's control, whether for good or evil\n\n";
		if (courted > (trumps+wands+cups+swords+pentacles-courted))
			returner += "the influence of others will be exerted in the shaping of things to come\n\n";
		if (aces == 4)
			returner += "the forces in this reading are very strong\n\n";
		
		if (kings == 4)
			returner += "meetings with the great\n\n";
		if (kings == 3)
			returner += "rank and honor\n\n";
		
		if (queens == 4)
			returner += "authority, influence, help from women\n\n";
		if (queens == 3)
			returner += "strong friends, or partners\n\n";
		
		if (knights == 4)
			returner += "the events in the matter move swiftly\n\n";
		if (knights == 3)
			returner += "sudden changes, news, unexpected meetings\n\n";
		
		if (pages == 4)
			returner += "new ideas or plans, youthful associates\n\n";
		if (pages == 3)
			returner += "help from the young, or from inferiors\n\n";
		
		if (tens == 4)
			returner += "the burden of anxiety; too many irons\n\n";
		if (tens == 3)
			returner += "commerce, speculation, buying and selling.\n\n";
		
		if (nines == 4)
			returner += "new responsibilities, firm foundations\n\n";
		if (nines == 3)
			returner += "much correspondence, delays\n\n";
		
		if (eights == 4)
			returner += "news, rumors, gossip\n\n";
		if (eights == 3)
			returner += "much moving about, short journeys\n\n";
		
		if (sevens == 4)
			returner += "disappointments\n\n";
		if (sevens == 3)
			returner += "contracts, alliances, partnerships\n\n";
		
		if (sixes == 4)
			returner += "pleasure, control over conditions.\n\n";
		if (sixes == 3)
			returner += "success, gain, balance of power\n\n";
		
		if (fives == 4)
			returner += "magic power, unusual strength.\n\n";
		if (fives == 3)
			returner += "competition, fights, dissension.\n\n";
		
		if (fours == 4)
			returner += "order, regularity, rest, peace\n\n";
		if (fours == 3)
			returner += "abundance, industry\n\n";
		
		if (threes == 4)
			returner += "determination, definite plans\n\n";
		if (threes == 3)
			returner += "deceit, misunderstanding, uncertainty\n\n";
		
		if (twos == 4)
			returner += "conferences, conversations, adjustments\n\n";
		if (twos == 3)
			returner += "cliques, separations, reorganizations\n\n";
		
		if (aces == 4)
			returner += "great power and force\n\n";
		if (aces == 3)
			returner += "wealth, success.\n\n";
		
		if (returner.length() > 0)
			returner = "\n\n\n"+returner;
		return returner;
	}

	public String getPost(Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String getContext(int mid, Context context) {

		Integer right;
		Integer left;
		String dignity = "";
		
		String entitle = context.getString(BotaInt.getEnTitle(mid));
		
		if (working.indexOf(mid)+1 < working.size())			
			right = working.get(working.indexOf(mid)+1);
		else
			right = working.get(0);
		
		if (working.indexOf(mid)-1 >= 0)			
			left = working.get(working.indexOf(mid)-1);
		else
			left = working.get(working.size()-1);
		
		Matcher suitmatch = BotaInt.suitpat.matcher(entitle);
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
		
		String entitleLeft = context.getString(BotaInt.getEnTitle(left));
		suitmatch = BotaInt.suitpat.matcher(entitleLeft);
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
		
		String entitleRight = context.getString(BotaInt.getEnTitle(right));
		suitmatch = BotaInt.suitpat.matcher(entitleRight);
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
		
		if (type.equals(lefttype) && type.equals(righttype))
			return "Strengthened in both the past and the future\n";
		else if (!(type.equals("trump")) && type.equals(lefttype))
			dignity += "Strengthened in the past;\n";
		else if (!(type.equals("trump")) && type.equals(righttype))
			dignity += "Strengthened in the future;\n";
		
		if (type.equals("Wands")) {
			if ((lefttype.equals("Cups"))&&(righttype.equals("Cups")))
				return "Ill-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Cups"))
					dignity += "Ill-Dignified in the past;\n";
			else if (righttype.equals("Cups"))
				dignity += "Ill-Dignified in the future;\n";
			if ((lefttype.equals("Swords")||lefttype.equals("Pentacles"))&&(righttype.equals("Swords")||righttype.equals("Pentacles")))
				return "Well-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Swords")||lefttype.equals("Pentacles"))
					dignity += "Well-Dignified in the past;\n";
			else if (righttype.equals("Swords")||righttype.equals("Pentacles"))
				dignity += "Well-Dignified in the future;\n";
		}
		else if (type.equals("Cups")) {
			if ((lefttype.equals("Wands"))&&(righttype.equals("Wands")))
				return "Ill-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Wands"))
					dignity += "Ill-Dignified in the past;\n";
			else if (righttype.equals("Wands"))
				dignity += "Ill-Dignified in the future;\n";
			if ((lefttype.equals("Swords")||lefttype.equals("Pentacles"))&&(righttype.equals("Swords")||righttype.equals("Pentacles")))
				return "Well-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Swords")||lefttype.equals("Pentacles"))
					dignity += "Well-Dignified in the past;\n";
			else if (righttype.equals("Swords")||righttype.equals("Pentacles"))
				dignity += "Well-Dignified in the future;\n";
		}
		else if (type.equals("Swords")) {
			if ((lefttype.equals("Pentacles"))&&(righttype.equals("Pentacles")))
				return "Ill-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Pentacles"))
					dignity += "Ill-Dignified in the past;\n";
			else if (righttype.equals("Pentacles"))
				dignity += "Ill-Dignified in the future;\n";
			if ((lefttype.equals("Wands")||lefttype.equals("Cups"))&&(righttype.equals("Wands")||righttype.equals("Cups")))
				return "Well-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Wands")||lefttype.equals("Cups"))
					dignity += "Well-Dignified in the past;\n";
			else if (righttype.equals("Wands")||righttype.equals("Cups"))
				dignity += "Well-Dignified in the future;\n";
		}
		else if (type.equals("Pentacles")) {
			if ((lefttype.equals("Swords"))&&(righttype.equals("Swords")))
				return "Ill-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Swords"))
					dignity += "Ill-Dignified in the past;\n";
			else if (righttype.equals("Swords"))
				dignity += "Ill-Dignified in the future;\n";
			if ((lefttype.equals("Wands")||lefttype.equals("Cups"))&&(righttype.equals("Wands")||righttype.equals("Cups")))
				return "Well-Dignified in both the past and the future;\n";
			else if (lefttype.equals("Wands")||lefttype.equals("Cups"))
					dignity += "Well-Dignified in the past;\n";
			else if (righttype.equals("Wands")||righttype.equals("Cups"))
				dignity += "Well-Dignified in the future;\n";
		}
		return dignity;
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

}
