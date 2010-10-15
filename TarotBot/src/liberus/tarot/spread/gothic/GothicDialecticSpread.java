package liberus.tarot.spread.gothic;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.os.activity.TarotBotActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class GothicDialecticSpread extends GothicSpread {
	
	
	private static int significatorIn;
	private int myNum;


	public GothicDialecticSpread(Interpretation myInt, String[] labels) {
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
				GothicSpread.working.add(shuffled[i]);
			GothicSpread.circles = working;
		}		
	}
	
	@Override
	public int getLayout() {
		return R.layout.dialecticlayout;
	}

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.dialectic_thesis);
		placeImage(act.flipdex.get(0),card,ctx);
		card.setId(0);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 0)
			layout.findViewById(R.id.dialectic_thesis_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.dialectic_antithesis);
		placeImage(act.flipdex.get(1),card,ctx);

		card.setId(1);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 1)
			layout.findViewById(R.id.dialectic_antithesis_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.dialectic_synthesis);
		placeImage(act.flipdex.get(2),card,ctx);
		card.setId(2);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 2)
			layout.findViewById(R.id.dialectic_synthesis_back).setBackgroundColor(Color.RED);
		return layout;
	}


}
