package liberus.tarot.deck;
import java.util.ArrayList;
import java.util.HashMap;



public class TarotTrumpDeck extends Deck {

	
	public TarotTrumpDeck() {
		super();
	}
	public TarotTrumpDeck(Boolean[] rev) {
		super();
		establishReversal(rev);
	}
	@Override
	public void initStatic() {
		cards = new Integer[22];
	}

}
