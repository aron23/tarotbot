package liberus.tarot.spread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.querant.Querant;
import android.content.Context;
import android.text.Html;

public class SeqSpread extends Spread {
	
	
	private static int significatorIn;
	private int myNum;


	public SeqSpread(BotaInt in, String[] labels) {
		super(in);
		myNum = labels.length;
		myLabels = labels;
	}
	
	@Override
	public void operate(Context context, boolean loading) {
		if (!loading) {
			Integer[] shuffled = myDeck.shuffle(new Integer[78],3);
			Deck.shuffled = shuffled;
			for (int i = 0; i < myNum; i++)
				Spread.working.add(shuffled[i]);
			Spread.circles = working;
		}		
	}

	public String getInterpretation(int circled, Context appcontext) {
		int pos = working.indexOf(circled);
		String context = getContext(circled, appcontext);
		String returner = "";
		
		
		returner += "<b><big>"+appcontext.getString(BotaInt.getTitle(circled))+"</big></b>"+"<br/>";
		if (BotaInt.getKeyword(circled) > 0 && appcontext.getString(BotaInt.getKeyword(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getKeyword(circled))+"<br/>";
		if (BotaInt.getJourney(circled) > 0 && appcontext.getString(BotaInt.getJourney(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getJourney(circled))+"<br/>";
				
		if (BotaInt.getAbst(circled) > 0 && appcontext.getString(BotaInt.getAbst(circled)).length() > 0)
			returner += "<br/><i>In general</i>: " + appcontext.getString(BotaInt.getAbst(circled))+"<br/>";
		if (significatorIn == 1 && BotaInt.getInSpiritualMatters(circled) > 0 && appcontext.getString(BotaInt.getInSpiritualMatters(circled)).length() > 0)
			returner += "<br/><i>More directly</i>: "+ appcontext.getString(BotaInt.getInSpiritualMatters(circled))+"<br/>";
		else if (significatorIn == 3 && BotaInt.getInMaterialMatters(circled) > 0 && appcontext.getString(BotaInt.getInMaterialMatters(circled)).length() > 0)
			returner += "<br/><i>More directly</i>: "+ appcontext.getString(BotaInt.getInMaterialMatters(circled))+"<br/>";
		else if (BotaInt.isWellDignified(context) &! BotaInt.isIllDignified(context) && BotaInt.getWellDignified(circled) > 0 && appcontext.getString(BotaInt.getWellDignified(circled)).length() > 0)
			returner += "<br/><i>More directly</i>: "+ appcontext.getString(BotaInt.getWellDignified(circled))+"<br/>";
		else if (BotaInt.isIllDignified(context) &! BotaInt.isWellDignified(context) && BotaInt.getIllDignified(circled) > 0 && appcontext.getString(BotaInt.getIllDignified(circled)).length() > 0)
			returner += "<br/><i>More directly</i>: "+ appcontext.getString(BotaInt.getIllDignified(circled))+"<br/>";
		else if (BotaInt.getMeanings(circled) > 0 && appcontext.getString(BotaInt.getMeanings(circled)).length() > 0)
			returner += "<br/><i>More directly</i>: "+ appcontext.getString(BotaInt.getMeanings(circled))+"<br/>";
		if (BotaInt.getOppositionNumber(circled) > 0) {
			List oppNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getOppositionNumber(circled)));
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (oppNumList != null && oppNumList.contains(lefty))
				returner += "Has been opposed by: "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (oppNumList != null && oppNumList.contains(righty))
				returner += "Will be opposed by: "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(righty)]+"<br/>";
		}
		if (BotaInt.getReinforcementNumber(circled) > 0) {
			List reNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getReinforcementNumber(circled)));
			
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (reNumList != null && reNumList.contains(lefty))
				returner += "Has been reinforced by: "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (reNumList != null && reNumList.contains(righty))
				returner += "Will be reinforced by: "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(righty)]+"<br/>";
		}
		/*if (getActions(circled) > 0 && appcontext.getString(getActions(circled)).length() > 0)
			returner += appcontext.getString(getActions(circled))+"\n\n";*/
		
		//returner += context;
		if (myDeck.isReversed(circled))
			returner += "<br/>being reversed the energy of this card is muted, its potential is there but is either not currently being expressed or is effectively suppressed";
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
			returner  += "there will be much mental energy expended in the matter, some opposition, and possible quarrels<br/><br/>";
		if (cups > wands && cups > swords && cups > pentacles)
			returner += "intense emotional element is present; in general, intimate pleasure, social activity<br/><br/>";
		if (swords > cups && swords > wands && swords > pentacles)
			returner += "sickness, sadness, trouble, or death<br/><br/>";
		if (pentacles > cups && pentacles > swords && pentacles > wands)
			returner += "the emphasis will be on business, money or possessions<br/><br/>";
		if (trumps > wands && trumps > cups && trumps > swords && trumps > pentacles)
			returner += "strong forces are at work in the matter under consideration, likely beyond the one's control, whether for good or evil<br/><br/>";
		if (courted > (trumps+wands+cups+swords+pentacles-courted))
			returner += "the influence of others will be exerted in the shaping of things to come<br/><br/>";
		if (aces == 4)
			returner += "the forces in this reading are very strong<br/><br/>";
		
		if (kings == 4)
			returner += "meetings with the great<br/><br/>";
		if (kings == 3)
			returner += "rank and honor<br/><br/>";
		
		if (queens == 4)
			returner += "authority, influence, help from women<br/><br/>";
		if (queens == 3)
			returner += "strong friends, or partners<br/><br/>";
		
		if (knights == 4)
			returner += "the events in the matter move swiftly<br/><br/>";
		if (knights == 3)
			returner += "sudden changes, news, unexpected meetings<br/><br/>";
		
		if (pages == 4)
			returner += "new ideas or plans, youthful associates<br/><br/>";
		if (pages == 3)
			returner += "help from the young, or from inferiors<br/><br/>";
		
		if (tens == 4)
			returner += "the burden of anxiety; too many irons<br/><br/>";
		if (tens == 3)
			returner += "commerce, speculation, buying and selling.<br/><br/>";
		
		if (nines == 4)
			returner += "new responsibilities, firm foundations<br/><br/>";
		if (nines == 3)
			returner += "much correspondence, delays<br/><br/>";
		
		if (eights == 4)
			returner += "news, rumors, gossip<br/><br/>";
		if (eights == 3)
			returner += "much moving about, short journeys<br/><br/>";
		
		if (sevens == 4)
			returner += "disappointments<br/><br/>";
		if (sevens == 3)
			returner += "contracts, alliances, partnerships<br/><br/>";
		
		if (sixes == 4)
			returner += "pleasure, control over conditions.<br/><br/>";
		if (sixes == 3)
			returner += "success, gain, balance of power<br/><br/>";
		
		if (fives == 4)
			returner += "magic power, unusual strength.<br/><br/>";
		if (fives == 3)
			returner += "competition, fights, dissension.<br/><br/>";
		
		if (fours == 4)
			returner += "order, regularity, rest, peace<br/><br/>";
		if (fours == 3)
			returner += "abundance, industry<br/><br/>";
		
		if (threes == 4)
			returner += "determination, definite plans<br/><br/>";
		if (threes == 3)
			returner += "deceit, misunderstanding, uncertainty<br/><br/>";
		
		if (twos == 4)
			returner += "conferences, conversations, adjustments<br/><br/>";
		if (twos == 3)
			returner += "cliques, separations, reorganizations<br/><br/>";
		
		if (aces == 4)
			returner += "great power and force<br/><br/>";
		if (aces == 3)
			returner += "wealth, success.<br/><br/>";
		
		if (returner.length() > 0)
			returner = "<br/><br/><br/>"+returner;
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
			return "Strengthened in both the past and the future<br/>";
		else if (!(type.equals("trump")) && type.equals(lefttype))
			dignity += "Strengthened in the past;<br/>";
		else if (!(type.equals("trump")) && type.equals(righttype))
			dignity += "Strengthened in the future;<br/>";
		
		if (type.equals("Wands")) {
			if ((lefttype.equals("Cups"))&&(righttype.equals("Cups")))
				return "Ill-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Cups"))
					dignity += "Ill-Dignified in the past;<br/>";
			else if (righttype.equals("Cups"))
				dignity += "Ill-Dignified in the future;<br/>";
			if ((lefttype.equals("Swords")||lefttype.equals("Pentacles"))&&(righttype.equals("Swords")||righttype.equals("Pentacles")))
				return "Well-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Swords")||lefttype.equals("Pentacles"))
					dignity += "Well-Dignified in the past;<br/>";
			else if (righttype.equals("Swords")||righttype.equals("Pentacles"))
				dignity += "Well-Dignified in the future;<br/>";
		}
		else if (type.equals("Cups")) {
			if ((lefttype.equals("Wands"))&&(righttype.equals("Wands")))
				return "Ill-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Wands"))
					dignity += "Ill-Dignified in the past;<br/>";
			else if (righttype.equals("Wands"))
				dignity += "Ill-Dignified in the future;<br/>";
			if ((lefttype.equals("Swords")||lefttype.equals("Pentacles"))&&(righttype.equals("Swords")||righttype.equals("Pentacles")))
				return "Well-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Swords")||lefttype.equals("Pentacles"))
					dignity += "Well-Dignified in the past;<br/>";
			else if (righttype.equals("Swords")||righttype.equals("Pentacles"))
				dignity += "Well-Dignified in the future;<br/>";
		}
		else if (type.equals("Swords")) {
			if ((lefttype.equals("Pentacles"))&&(righttype.equals("Pentacles")))
				return "Ill-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Pentacles"))
					dignity += "Ill-Dignified in the past;<br/>";
			else if (righttype.equals("Pentacles"))
				dignity += "Ill-Dignified in the future;<br/>";
			if ((lefttype.equals("Wands")||lefttype.equals("Cups"))&&(righttype.equals("Wands")||righttype.equals("Cups")))
				return "Well-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Wands")||lefttype.equals("Cups"))
					dignity += "Well-Dignified in the past;<br/>";
			else if (righttype.equals("Wands")||righttype.equals("Cups"))
				dignity += "Well-Dignified in the future;<br/>";
		}
		else if (type.equals("Pentacles")) {
			if ((lefttype.equals("Swords"))&&(righttype.equals("Swords")))
				return "Ill-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Swords"))
					dignity += "Ill-Dignified in the past;<br/>";
			else if (righttype.equals("Swords"))
				dignity += "Ill-Dignified in the future;<br/>";
			if ((lefttype.equals("Wands")||lefttype.equals("Cups"))&&(righttype.equals("Wands")||righttype.equals("Cups")))
				return "Well-Dignified in both the past and the future;<br/>";
			else if (lefttype.equals("Wands")||lefttype.equals("Cups"))
					dignity += "Well-Dignified in the past;<br/>";
			else if (righttype.equals("Wands")||righttype.equals("Cups"))
				dignity += "Well-Dignified in the future;<br/>";
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
