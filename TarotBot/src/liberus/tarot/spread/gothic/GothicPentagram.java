package liberus.tarot.spread.gothic;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.android.noads.R;
import liberus.tarot.spread.Spread;
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

public class GothicPentagram extends GothicSpread {
	
	
	private static int significatorIn;
	private int myNum;


	public GothicPentagram(Interpretation in, String[] labels) {
		super(in);
		myNum = labels.length;
		myLabels = labels;
	}
	
	@Override
	public void operate(Context context, boolean loading) {
		if (!loading) {
			Deck.cards = myDeck.shuffle(Deck.cards,3);			
			for (int i = 0; i < myNum; i++)
				Spread.working.add(Deck.cards[i]);
			Spread.circles = working;
		}		
	}

	

	@Override
	public int getLayout() {
		return R.layout.gothicpentagramlayout;
	}	

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.goth_pent_one);
		placeImage(act.flipdex.get(0),card,ctx);
		card.setId(0);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 0)
			layout.findViewById(R.id.goth_pent_one_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.goth_pent_two);
		placeImage(act.flipdex.get(1),card,ctx);
		card.setId(1);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 1)
			layout.findViewById(R.id.goth_pent_two_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.goth_pent_three);
		placeImage(act.flipdex.get(2),card,ctx);
		card.setId(2);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 2)
			layout.findViewById(R.id.goth_pent_three_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.goth_pent_four);
		placeImage(act.flipdex.get(3),card,ctx);
		card.setId(3);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 3)
			layout.findViewById(R.id.goth_pent_four_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.goth_pent_five);
		placeImage(act.flipdex.get(4),card,ctx);
		card.setId(4);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 4)
			layout.findViewById(R.id.goth_pent_five_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.goth_pent_six);
		placeImage(act.flipdex.get(5),card,ctx);
		card.setId(5);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 5)
			layout.findViewById(R.id.goth_pent_six_back).setBackgroundColor(Color.RED);
		
		return layout;
	}

}
