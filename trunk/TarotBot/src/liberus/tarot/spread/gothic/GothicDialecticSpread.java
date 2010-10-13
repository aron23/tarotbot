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
//		if (BotaInt.myDeck.reversed[act.flipdex.get(0)]) {			
//			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCardThumb(act.flipdex.get(0)));
//			int w = bmp.getWidth();
//			int h = bmp.getHeight();
//			Matrix mtx = new Matrix();
//			mtx.postRotate(180);
//			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
//			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
//			card.setImageDrawable(bmd);
//		} else {
//			card.setImageDrawable(act.getResources().getDrawable(BotaInt.getCardThumb(act.flipdex.get(0))));
//		}	
		card.setId(0);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 0)
			card.setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
		
		card = (ImageView) layout.findViewById(R.id.dialectic_antithesis);
		placeImage(act.flipdex.get(1),card,ctx);
//		if (BotaInt.myDeck.reversed[act.flipdex.get(1)]) {			
//			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCardThumb(act.flipdex.get(1)));
//			int w = bmp.getWidth();
//			int h = bmp.getHeight();
//			Matrix mtx = new Matrix();
//			mtx.postRotate(180);
//			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
//			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
//			card.setImageDrawable(bmd);
//		} else {
//			card.setImageDrawable(act.getResources().getDrawable(BotaInt.getCardThumb(act.flipdex.get(1))));
//		}	
		card.setId(1);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 1)
			card.setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
		
		card = (ImageView) layout.findViewById(R.id.dialectic_synthesis);
		placeImage(act.flipdex.get(2),card,ctx);
//		if (BotaInt.myDeck.reversed[act.flipdex.get(2)]) {			
//			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCardThumb(act.flipdex.get(2)));
//			int w = bmp.getWidth();
//			int h = bmp.getHeight();
//			Matrix mtx = new Matrix();
//			mtx.postRotate(180);
//			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
//			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
//			card.setImageDrawable(bmd);
//		} else {
//			card.setImageDrawable(act.getResources().getDrawable(BotaInt.getCardThumb(act.flipdex.get(2))));
//		}	
		card.setId(2);
		card.setOnClickListener(act);
		if (TarotBotActivity.secondSetIndex == 2)
			card.setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
		return layout;
	}


}
