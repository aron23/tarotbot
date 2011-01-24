package liberus.tarot.deck;
import java.util.ArrayList;
import java.util.HashMap;



public class FullTarotDeck extends Deck {

	
	public FullTarotDeck() {
		super();
	}
	public FullTarotDeck(Boolean[] rev) {
		super();
		establishReversal(rev);
	}
	@Override
	public void initStatic() {
		cards = new Integer[78];
	}

}
