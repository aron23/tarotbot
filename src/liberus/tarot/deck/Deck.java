package liberus.tarot.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import liberus.tarot.interpretation.Interpretation;


public abstract class Deck {
	protected static String[] deck = new String[78];
	protected boolean[] direction = new boolean[78];
	public boolean[] reversed = new boolean[78];
	protected Interpretation meaning;
	private static Integer[] cards = new Integer[78];
	public static ArrayList<Integer> firetrumps = new ArrayList<Integer>();
	public static ArrayList<Integer> watertrumps = new ArrayList<Integer>();
	public static ArrayList<Integer> airtrumps = new ArrayList<Integer>();
	public static ArrayList<Integer> earthtrumps = new ArrayList<Integer>();
	static {
		
		for (int i=0; i < cards.length; i++)
			cards[i] = i;
		
		firetrumps.add(4);
		firetrumps.add(11);
		firetrumps.add(14);
		firetrumps.add(16);
		firetrumps.add(19);
		firetrumps.add(20);
		
		watertrumps.add(7);
		watertrumps.add(10);
		watertrumps.add(12);
		watertrumps.add(13);
		watertrumps.add(18);
		
		airtrumps.add(0);
		airtrumps.add(1);
		airtrumps.add(6);
		airtrumps.add(8);
		airtrumps.add(9);
		airtrumps.add(17);
		
		earthtrumps.add(2);
		earthtrumps.add(3);
		earthtrumps.add(5);
		earthtrumps.add(15);
		earthtrumps.add(21);
		
		deck[0]="The Fool";
		deck[1]="The Magician";
		deck[2]="The High Priestess";
		deck[3]="The Empress";
		deck[4]="The Emperor";
		deck[5]="The Hierophant";
		deck[6]="The Lovers";
		deck[7]="The Chariot";
		deck[8]="Strength";
		deck[9]="The Hermit";
		deck[10]="Wheel of Fortune";
		deck[11]="Justice";
		deck[12]="The Hanged Man";
		deck[13]="Death";
		deck[14]="Temperance";
		deck[15]="The Devil";
		deck[16]="The Tower";
		deck[17]="The Star";
		deck[18]="The Moon";
		deck[19]="The Sun";
		deck[20]="Judgement";
		deck[21]="The World";
		
		deck[22]="Ace of Wands";
		deck[23]="Two of Wands";
		deck[24]="Three of Wands";
		deck[25]="Four of Wands";
		deck[26]="Five of Wands";
		deck[27]="Six of Wands";
		deck[28]="Seven of Wands";
		deck[29]="Eight of Wands";
		deck[30]="Nine of Wands";
		deck[31]="Ten of Wands";
		deck[32]="Page of Wands";
		deck[33]="Kight of Wands";
		deck[34]="Queen of Wands";
		deck[35]="King of Wands";
		
		deck[36]="Ace of Cups";
		deck[37]="Two of Cups";
		deck[38]="Three of Cups";
		deck[39]="Four of Cups";
		deck[40]="Five of Cups";
		deck[41]="Six of Cups";
		deck[42]="Seven of Cups";
		deck[43]="Eight of Cups";
		deck[44]="Nine of Cups";
		deck[45]="Ten of Cups";
		deck[46]="Page of Cups";
		deck[47]="Kight of Cups";
		deck[48]="Queen of Cups";
		deck[49]="King of Cups";
		
		deck[50]="Ace of Swords";
		deck[51]="Two of Swords";
		deck[52]="Three of Swords";
		deck[53]="Four of Swords";
		deck[54]="Five of Swords";
		deck[55]="Six of Swords";
		deck[56]="Seven of Swords";
		deck[57]="Eight of Swords";
		deck[58]="Nine of Swords";
		deck[59]="Ten of Swords";
		deck[60]="Page of Swords";
		deck[61]="Kight of Swords";
		deck[62]="Queen of Swords";
		deck[63]="King of Swords";
		
		deck[64]="Ace of Pentacles";
		deck[65]="Two of Pentacles";
		deck[66]="Three of Pentacles";
		deck[67]="Four of Pentacles";
		deck[68]="Five of Pentacles";
		deck[69]="Six of Pentacles";
		deck[70]="Seven of Pentacles";
		deck[71]="Eight of Pentacles";
		deck[72]="Nine of Pentacles";
		deck[73]="Ten of Pentacles";
		deck[74]="Page of Pentacles";
		deck[75]="Kight of Pentacles";
		deck[76]="Queen of Pentacles";
		deck[77]="King of Pentacles";
	}
	
	public Deck() {
		initDirection();
	}
	public abstract void initDirection();
	public abstract HashMap<Integer,String> getCards();
	public boolean getDirection(int circled) {
		return direction[circled];
	}
	
	public Integer[] shuffle(Integer[] toReturn, int count) {
		List<Integer> cardList;
		for (int i = 0; i < count; i++) {
			if (toReturn[0] == null)
				cardList = Arrays.asList(cards);
			else
				cardList = Arrays.asList(toReturn);
			Collections.shuffle(cardList);
			cardList.toArray(toReturn);
		}
		establishReversal();
		return toReturn;
	}
	
	private void establishReversal() {
		Random myRandom = new Random();
		for (int i=0; i < reversed.length; i++)
			reversed[i] = myRandom.nextBoolean();
	}
	public static int cut(Integer[] deck) {
		int min = (int)Math.floor((deck.length*(.50)));
		int max = (int)Math.ceil(deck.length*(.50));
		if (max > min)
			if (new Random().nextBoolean())
				return min + 1;
		return min;
	}
	public boolean isReversed(int circled) {
		// TODO Auto-generated method stub
		return reversed[circled];
	}
}
