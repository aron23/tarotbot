package liberus.tarot.spread.gothic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.android.noads.R;
import liberus.tarot.spread.Spread;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnTouchListener;

public class GothicSeqSpread extends GothicSpread {
	
	
	private int myNum;
	private boolean isCardOfTheDay;
	private boolean isTrumpsOnly;


	public GothicSeqSpread(Interpretation myInt, String[] labels, boolean cardOfTheDay) {
		super(myInt);
		myNum = labels.length;
		myLabels = labels;
		isCardOfTheDay = cardOfTheDay;
		isTrumpsOnly = false;
	}
	public GothicSeqSpread(Interpretation myInt, String[] labels, boolean cardOfTheDay,boolean trumpsOnly) {
		super(myInt);
		myNum = labels.length;
		myLabels = labels;
		isCardOfTheDay = cardOfTheDay;
		isTrumpsOnly = trumpsOnly;
	}
	@Override
	public void operate(Context context, boolean loading) {
		if (!loading &! isCardOfTheDay) {
			Deck.cards = myDeck.shuffle(Deck.cards,3);			
			for (int i = 0; i < myNum; i++)
				Spread.working.add(Deck.cards[i]);
			Spread.circles = working;
		} else if (!loading) {
			Spread.working.clear();
			if (isTrumpsOnly)
				Deck.cards = Deck.orderedDeck(22);
			else
				Deck.cards = Deck.orderedDeck(78);
			
			
			SharedPreferences readingPrefs = context.getSharedPreferences("tarotbot.gothic.random", 0);

			Random rand = new Random();
			int seed = 0;
			if (isTrumpsOnly)
				seed = (readingPrefs.getInt("seed", BotaInt.getRandom(context).nextInt(22)));
			else
				seed = (readingPrefs.getInt("seed", BotaInt.getRandom(context).nextInt(78)));
			 
			
			Spread.working.add(seed);
			Spread.circles = working;
		}		
	}

	
	@Override
	public int getLayout() {
		return R.layout.arrowlayout;
	}

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		act.setBackground(layout);
		return layout;
	}




}
