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
import liberus.tarot.querant.Querant;


public class BotaInt extends Interpretation {

	protected static Pattern suitpat = Pattern.compile("(Wands|Cups|Swords|Pentacles)");
	public static int secondSetStrongest = 79;
	public static Deck myDeck;
	
	public static Integer[] yod;
	public static Integer[] heh1;
	public static Integer[] vau;
	public static Integer[] heh2;

	private static int significatorIn=79;
	public static Querant myQuerant;
	public static ArrayList<Integer> working;
	public static ArrayList<Integer> circles;
	
	
	public BotaInt(RiderWaiteDeck deck, Querant q) {
		super(deck);
		myDeck = deck;
		myQuerant = q;
	}
	
	public static String firstOperation() {
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
			toreturn = "Your question is about the beginning of some enterprise, about the root-ideas behind some matter. It is more concerned with causes than with outward conditions, and may have to do with the spiritual life.";
		} else if (hasSignificator(heh1)) {
			System.err.println("heh1");
			significatorIn=1;		
			toreturn = "Your question has to do with your desires and wishes, with the formation of plans, with some state of your emotions or affections, with matters in which your feelings are deeply affected.";
		} else if (hasSignificator(vau)) {
			System.err.println("vau");
			significatorIn=2;		
			toreturn = "You want to know what to do, what action is best to bring about some result, either to avoid some conflict with others, or to overcome some conflict which has already come up. Your question is somehow connected with inharmony, with disappointment, either actual or threatened.";
		} else if (hasSignificator(heh2)) {
			System.err.println("heh2");
			significatorIn=3;		
			toreturn = "Your question has to do with the things of the outer world, or practical life. It is almost wholly concerned with material affairs.";
		} else {
			System.err.println("missing significator");
		}
		return toreturn;
	}
	
	public static void secondOperation(Context context) {
		getSecondSetDeck();
		int primaryCircle;
		int significatorIndex=79;				
		ArrayList<Integer> toChew = new ArrayList(working);
		Iterator<Integer> circler = working.iterator();
		
		circles = new ArrayList<Integer>();
		int circled = 79;
		
		boolean circling = true;
		boolean begun = false;
		ArrayList<Integer> hits = new ArrayList<Integer>();
		System.err.println(BotaInt.getSignificator());
		int circle_index = working.indexOf(BotaInt.getSignificator());
		while (circling) {
			if (circle_index == working.indexOf(BotaInt.getSignificator()) && circles.size() < 1) {
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
			circle_index += context.getResources().getInteger(getSecondOperationCount(working.get(circle_index)));
			if (circle_index >= working.size())
				circle_index -= working.size();
		
		}
		
		/*while (circler.hasNext() &! circles.contains(circled)) {
				circled = circler.next();				
				if (isSignificator(circled)) {
					significatorIndex = working.indexOf(circled);
					if (!myDeck.getDirection(circled)) 
						Collections.reverse(working);
						int nextcircle = 79;
						while (!circles.contains(circled)) {
							circles.add(circled);		
							int secondOperationCount = context.getResources().getInteger(getSecondOperationCount(circled));
							for (int i=1;i<=secondOperationCount;i++) {							
								while (circler.hasNext() && i <= secondOperationCount) {
									nextcircle = circler.next();
									i++;
								}
								if (i < secondOperationCount || nextcircle == 79)
									circler = working.iterator();
								else {
									i=1;									
									circled = nextcircle;
									if (circled == 79)
										System.out.println("wtf");
									
									if (circles.contains(circled)) break;
									
									circles.add(circled);
									nextcircle = 79;
								}
							}
						}
				}						
		}
		primaryCircle = circled;
		secondSetStrongest = primaryCircle;*/
	}
	
	
	
	public static String thirdOperation(boolean male) {
		return null;
	}
	
	public static String secondOperationPreliminary(Context context) {

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
			int cardIndex = context.getResources().getInteger(getCardNum(i))-100;
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
	
	public static String secondOperationInterpretation(int circled, Context appcontext) {
				
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
		
		/*switch (house) {
		case 0: returner = "With regard to first impressions, general outlook into the world, ego, beginnings and initiatives:\n\n";break;
		case 1: returner = "With regard to material and immaterial things of certain value:\n\n";break;
		case 2: returner = "With regard to mental facilities, siblings, neighborhood matters, short, local travel and transportations:\n\n";break;
		case 3: returner = "With regard to ancestry, heritage, roots, early foundation and environment:\n\n";break;
		case 4: returner = "With regard to enjoyment and entertainment, games and gambling, children, love affairs and sex, creative self-expression:\n\n";break;
		case 5: returner = "With regard to duties, skills or training acquired, jobs and employments, health and overall well-being:\n\n";break;
		case 6: returner = "With regard to close, relationships, including known enemies, marriage and business partners, agreements and treaties, attraction to qualities we admire from the other partner:\n\n";break;
		case 7: returner = "With regard to death and rebirth, sexual and deeply committed relationships, occult, psychic and taboo matters, regeneration, self-transformation:\n\n";break;
		case 8: returner = "With regard to culture, long distance travels and journeys, religion, law and ethics, higher education, knowledge, experience through expansion:\n\n";break;
		case 9: returner = "With regard to ambitions, motivations, career, status in society, government, authority and authority figures:\n\n";break;
		case 10: returner = "With regard to friends and acquaintances of like-minded attitudes, groups, clubs and societies, higher associations, benefits and fortunes from career, hopes and wishes:\n\n";break;
		default: returner = "With regard to mysticism, places of seclusion, prisons and institutions, including self-imposed imprisonments, elusive, clandestine, secretive, retreat, reflection and self-sacrifice, unconscious/subconscious, unknown enemies:\n\n";
		}*/
		
		if (isSignificator(circled)) {
			String pre = secondOperationPreliminary(appcontext);
			String post = "This card represents you.\n";
			
			if (pre.length() > 0)
				post = pre+"\n\n"+post;
			//if (secondSetPairs == null || secondSetPairs.size() < 1 || secondSetPairs.get(secondSetPairs.size()-1)[0] == null || secondSetPairs.get(secondSetPairs.size()-1)[1]==null)
				//System.out.println("missing");
			/*if (secondSetPairs.get(secondSetPairs.size()-1)[0].equals(secondSetPairs.get(secondSetPairs.size()-1)[1]))
				context += "the solution of the Querant's problem depends largely on the interaction of forces surrounding, and not so much upon will, thought or action\n\n";*/
			
			if (circled==BotaInt.secondSetStrongest)
				post += "this reading is particularly personal\n";
			
			returner = post;
			
			return returner;
		}
		returner += appcontext.getString(getTitle(circled))+":\n";
		if (getKeyword(circled) > 0 && appcontext.getString(getKeyword(circled)).length() > 0)
			returner += appcontext.getString(getKeyword(circled))+"\n";
		if (getJourney(circled) > 0 && appcontext.getString(getJourney(circled)).length() > 0)
			returner += appcontext.getString(getJourney(circled))+"\n";
		//if (getTimePeriod(circled) > 0 && appcontext.getString(getTimePeriod(circled)).length() > 0)
			//returner += appcontext.getString(getTimePeriod(circled))+", ";
		//if (getAstrology(circled) > 0 && appcontext.getString(getAstrology(circled)).length() > 0)
			//returner += appcontext.getString(getAstrology(circled))+", ";
		//if (getArchetype(circled) > 0 && appcontext.getString(getArchetype(circled)).length() > 0)
			//returner += appcontext.getString(getArchetype(circled))+", ";
		
		if (circled==BotaInt.secondSetStrongest)
			returner += "this card is the most significant of the reading\n";
		
		if (getAbst(circled) > 0 && appcontext.getString(getAbst(circled)).length() > 0)
			returner += "\nIn general: " + appcontext.getString(getAbst(circled))+"\n";
		if (significatorIn == 1 && getInSpiritualMatters(circled) > 0 && appcontext.getString(getInSpiritualMatters(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(getInSpiritualMatters(circled))+"\n";
		else if (significatorIn == 3 && getInMaterialMatters(circled) > 0 && appcontext.getString(getInMaterialMatters(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(getInMaterialMatters(circled))+"\n";
		else if (isWellDignified(context) &! isIllDignified(context) && getWellDignified(circled) > 0 && appcontext.getString(getWellDignified(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(getWellDignified(circled))+"\n";
		else if (isIllDignified(context) &! isWellDignified(context) && getIllDignified(circled) > 0 && appcontext.getString(getIllDignified(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(getIllDignified(circled))+"\n";
		else if (getMeanings(circled) > 0 && appcontext.getString(getMeanings(circled)).length() > 0)
			returner += "\nMore directly: "+ appcontext.getString(getMeanings(circled))+"\n";
		if (getOppositionNumber(circled) > 0) {
			List oppNumList = Arrays.asList(appcontext.getResources().getIntArray(getOppositionNumber(circled)));
			String lefty = String.valueOf(BotaInt.getCardToTheLeft(circled));
			if (BotaInt.getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (oppNumList != null && oppNumList.contains(lefty))
				returner += "Has been opposed by: "+appcontext.getResources().getIntArray(getOppositionText(circled))[oppNumList.indexOf(lefty)]+"\n";
			
			String righty = String.valueOf(BotaInt.getCardToTheRight(circled));
			if (BotaInt.getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (oppNumList != null && oppNumList.contains(righty))
				returner += "Will be opposed by: "+appcontext.getResources().getIntArray(getOppositionText(circled))[oppNumList.indexOf(righty)]+"\n";
		}
		if (getReinforcementNumber(circled) > 0) {
			List reNumList = Arrays.asList(appcontext.getResources().getIntArray(getReinforcementNumber(circled)));
			
			String lefty = String.valueOf(BotaInt.getCardToTheLeft(circled));
			if (BotaInt.getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (reNumList != null && reNumList.contains(lefty))
				returner += "Has been reinforced by: "+appcontext.getResources().getIntArray(getReinforcementText(circled))[reNumList.indexOf(lefty)]+"\n";
			
			String righty = String.valueOf(BotaInt.getCardToTheRight(circled));
			if (BotaInt.getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (reNumList != null && reNumList.contains(righty))
				returner += "Will be reinforced by: "+appcontext.getResources().getIntArray(getReinforcementText(circled))[reNumList.indexOf(righty)]+"\n";
		}
		/*if (getActions(circled) > 0 && appcontext.getString(getActions(circled)).length() > 0)
			returner += appcontext.getString(getActions(circled))+"\n\n";*/
		
		//returner += context;
		if (myDeck.isReversed(circled))
			returner += "\nbeing reversed the energy of this card is muted, its potential is there but is either not currently being expressed or is effectively suppressed";
		return returner;
		//toReturn.put(circled,returner);
		//return toReturn;
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
	
	private static boolean isWellDignified(String context) {
		return (context.contains("Well-Dignified"));
	}
	private static boolean isIllDignified(String context) {
		return (context.contains("Ill-Dignified"));
	}
	private static String getContext(int mid, Context context) {

		Integer right;
		Integer left;
		String dignity = "";
		
		String entitle = context.getString(getEnTitle(mid));
		
		if (working.indexOf(mid)+1 < working.size())			
			right = working.get(working.indexOf(mid)+1);
		else
			right = working.get(0);
		
		if (working.indexOf(mid)-1 >= 0)			
			left = working.get(working.indexOf(mid)-1);
		else
			left = working.get(working.size()-1);
		
		Matcher suitmatch = suitpat.matcher(entitle);
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
		
		String entitleLeft = context.getString(getEnTitle(left));
		suitmatch = suitpat.matcher(entitleLeft);
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
		
		String entitleRight = context.getString(getEnTitle(right));
		suitmatch = suitpat.matcher(entitleRight);
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


	
	@Override
	public ArrayList<HashMap<String, String>> findMeaning() {
		return null;
	}		



	protected static boolean hasSignificator(Integer[] deck) {
		for (int i = 0; i < deck.length; i++)
			if (isSignificator(deck[i]))
				return true;
		return false;
	}
	protected static boolean isSignificator(int card) {
		switch (myQuerant.getElement()) {
		case 0: if (myQuerant.partnered) {
					if (myQuerant.male && card == 35)
						return true;
					else if (!myQuerant.male && card == 34)
						return true;
				} else {
					if (myQuerant.male && card == 33)
						return true;
					else if (!myQuerant.male && card == 32)
						return true;
				}
				break;
		case 1: if (myQuerant.partnered) {
					if (myQuerant.male && card == 49)
						return true;
					else if (!myQuerant.male && card == 48)
						return true;
				} else {
					if (myQuerant.male && card == 47)
						return true;
					else if (!myQuerant.male && card == 46)
						return true;
				}
				break;
		case 2: if (myQuerant.partnered) {
					if (myQuerant.male && card == 63)
						return true;
					else if (!myQuerant.male && card == 62)
						return true;
				} else {
					if (myQuerant.male && card == 61)
						return true;
					else if (!myQuerant.male && card == 60)
						return true;
				}
				break;
		case 3: if (myQuerant.partnered) {
					if (myQuerant.male && card == 77)
						return true;
					else if (!myQuerant.male && card == 76)
						return true;
				} else {
					if (myQuerant.male && card == 75)
						return true;
					else if (!myQuerant.male && card == 74)
						return true;
				}
				break;				
		}
		
		return false;
	}
	public static int getSignificator() {
		System.err.println(myQuerant.getElement());
		switch (myQuerant.getElement()) {
		case 0: if (myQuerant.partnered) {
					if (myQuerant.male)
						return 35;
					else if (!myQuerant.male) 
						return 34;
				} else {
					if (myQuerant.male)
						return 33;
					else if (!myQuerant.male)
						return 32;
				} break;				
		case 1: if (myQuerant.partnered) {
					if (myQuerant.male)
						return 49;
					else if (!myQuerant.male)
						return 48;
				} else {
					if (myQuerant.male)
						return 47;
					else if (!myQuerant.male)
						return 46;
				} break;				
		case 2: if (myQuerant.partnered) {
					if (myQuerant.male)
						return 63;
					else if (!myQuerant.male)
						return 62;
				} else {
					if (myQuerant.male)
						return 61;
					else if (!myQuerant.male)
						return 60;
				} break;
		case 3: if (myQuerant.partnered) {
					if (myQuerant.male)
						return 77;
					else if (!myQuerant.male)
						return 76;
				} else {
					if (myQuerant.male)
						return 75;
					else if (!myQuerant.male)
						return 74;
				} break;				
		}		
		return 0;
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

	public static void getSecondSetDeck() {		
		switch (significatorIn) {
		case 0: working = new ArrayList<Integer> (Arrays.asList(yod));System.err.println("second operation yod");break;
		case 1: working = new ArrayList<Integer> (Arrays.asList(heh1));System.err.println("second operation heh1");break;
		case 2: working = new ArrayList<Integer> (Arrays.asList(vau));System.err.println("second operation vau");break;
		case 3: working = new ArrayList<Integer> (Arrays.asList(heh2));System.err.println("second operation heh2");break;
		}
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
