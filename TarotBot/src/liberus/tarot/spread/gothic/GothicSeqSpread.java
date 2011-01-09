package liberus.tarot.spread.gothic;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.os.activity.TarotBotActivity;
import liberus.tarot.android.noads.R;
import liberus.tarot.spread.Spread;
import android.content.Context;
import android.view.View;
import android.view.View.OnTouchListener;

public class GothicSeqSpread extends GothicSpread {
	
	
	private static int significatorIn;
	private int myNum;


	public GothicSeqSpread(Interpretation myInt, String[] labels) {
		super(myInt);
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

	
	@Override
	public int getLayout() {
		return R.layout.arrowlayout;
	}

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		return layout;
	}




}
