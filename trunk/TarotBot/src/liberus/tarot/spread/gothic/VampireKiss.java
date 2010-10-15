package liberus.tarot.spread.gothic;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.os.activity.TarotBotActivity;
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

public class VampireKiss extends GothicSpread {
	
	
	private static int significatogrIn;
	private int myNum;


	public VampireKiss(Interpretation in, String[] labels) {
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

	@Override
	public int getLayout() {
		return R.layout.vampireskisslayout;
	}	

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.vampire_one);
		placeImage(act.flipdex.get(0),card,ctx);
		card.setId(0);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 0)
			layout.findViewById(R.id.vampire_one_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.vampire_two);
		placeImage(act.flipdex.get(1),card,ctx);
		card.setId(1);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 1)
			layout.findViewById(R.id.vampire_two_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.vampire_three);
		placeImage(act.flipdex.get(2),card,ctx);
		card.setId(2);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 2)
			layout.findViewById(R.id.vampire_three_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.vampire_four);
		placeImage(act.flipdex.get(3),card,ctx);
		card.setId(3);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 3)
			layout.findViewById(R.id.vampire_four_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.vampire_five);
		placeImage(act.flipdex.get(4),card,ctx);
		card.setId(4);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 4)
			layout.findViewById(R.id.vampire_five_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.vampire_six);
		placeImage(act.flipdex.get(5),card,ctx);
		card.setId(5);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 5)
			layout.findViewById(R.id.vampire_six_back).setBackgroundColor(Color.RED);

		card = (ImageView) layout.findViewById(R.id.vampire_seven);
		placeImage(act.flipdex.get(6),card,ctx);
		card.setId(6);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 6)
			layout.findViewById(R.id.vampire_seven_back).setBackgroundColor(Color.RED);
		
		return layout;
	}

}
