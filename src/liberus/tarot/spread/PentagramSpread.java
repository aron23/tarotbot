package liberus.tarot.spread;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.os.activity.TarotBotActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class PentagramSpread extends Spread {
	
	
	private static int significatorIn;
	private int myNum;


	public PentagramSpread(BotaInt in, String[] labels) {
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

	public String getInterpretation(int circled, Context appcontext) {
		
		int pos = working.indexOf(circled);
		int[] context = getContext(circled, appcontext);
		String returner = "";
		
		
		returner += "<big><b>"+appcontext.getString(BotaInt.getTitle(circled))+"</big></b><br/>";
		if (BotaInt.getKeyword(circled) > 0 && appcontext.getString(BotaInt.getKeyword(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getKeyword(circled))+"<br/>";
		if (BotaInt.getJourney(circled) > 0 && appcontext.getString(BotaInt.getJourney(circled)).length() > 0)
			returner += appcontext.getString(BotaInt.getJourney(circled))+"<br/>";
		
		if (circled==BotaInt.secondSetStrongest)
			returner += "<b>"+appcontext.getString(R.string.significant_label)+"</b><br/>";
		
		if (BotaInt.getAbst(circled) > 0 && appcontext.getString(BotaInt.getAbst(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.general_label)+"</i>: " + appcontext.getString(BotaInt.getAbst(circled))+"<br/>";
		if (significatorIn == 1 && BotaInt.getInSpiritualMatters(circled) > 0 && appcontext.getString(BotaInt.getInSpiritualMatters(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getInSpiritualMatters(circled))+"<br/>";
		else if (significatorIn == 3 && BotaInt.getInMaterialMatters(circled) > 0 && appcontext.getString(BotaInt.getInMaterialMatters(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getInMaterialMatters(circled))+"<br/>";
		else if (BotaInt.isWellDignified(context) &! BotaInt.isIllDignified(context) && BotaInt.getWellDignified(circled) > 0 && appcontext.getString(BotaInt.getWellDignified(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getWellDignified(circled))+"<br/>";
		else if (BotaInt.isIllDignified(context) &! BotaInt.isWellDignified(context) && BotaInt.getIllDignified(circled) > 0 && appcontext.getString(BotaInt.getIllDignified(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getIllDignified(circled))+"<br/>";
		else if (BotaInt.getMeanings(circled) > 0 && appcontext.getString(BotaInt.getMeanings(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(BotaInt.getMeanings(circled))+"<br/>";
		/*if (BotaInt.getOppositionNumber(circled) > 0) {
			List oppNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getOppositionNumber(circled)));
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (oppNumList != null && oppNumList.contains(lefty))
				returner += appcontext.getString(R.string.has_been_opposed_label)+": "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (oppNumList != null && oppNumList.contains(righty))
				returner += appcontext.getString(R.string.will_be_opposed_label)+": "+appcontext.getResources().getIntArray(BotaInt.getOppositionText(circled))[oppNumList.indexOf(righty)]+"<br/>";
		}
		if (BotaInt.getReinforcementNumber(circled) > 0) {
			List reNumList = Arrays.asList(appcontext.getResources().getIntArray(BotaInt.getReinforcementNumber(circled)));
			
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (reNumList != null && reNumList.contains(lefty))
				returner += appcontext.getString(R.string.has_been_reinforced_label)+": "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (reNumList != null && reNumList.contains(righty))
				returner += appcontext.getString(R.string.will_be_reinforced_label)+": "+appcontext.getResources().getIntArray(BotaInt.getReinforcementText(circled))[reNumList.indexOf(righty)]+"<br/>";
		}*/
		/*if (getActions(circled) > 0 && appcontext.getString(getActions(circled)).length() > 0)
			returner += appcontext.getString(getActions(circled))+"<br/><br/>";*/
		
		//returner += context;
		if (myDeck.isReversed(circled))
			returner += "<br/>"+appcontext.getString(R.string.reversed_label);
		return returner;
	}

	


	@Override
	public int getLayout() {
		return R.layout.pentagramlayout;
	}

	@Override
	public View navigate(View layout, TarotBotActivity act,Context ctx) {
		return populateSpread(layout,act,ctx);
	}

	private View populateSpread(View layout, TarotBotActivity act, Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.pentagram_fire);
		if (BotaInt.myDeck.reversed[act.flipdex.get(0)]) {			
			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCard(act.flipdex.get(0)));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postRotate(180);
			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
			card.setBackgroundDrawable(bmd);
		} else {
			card.setBackgroundDrawable(act.getResources().getDrawable(BotaInt.getCard(act.flipdex.get(0))));
		}	
		card.setId(0);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pentagram_water);
		if (BotaInt.myDeck.reversed[act.flipdex.get(1)]) {			
			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCard(act.flipdex.get(1)));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postRotate(180);
			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
			card.setBackgroundDrawable(bmd);
		} else {
			card.setBackgroundDrawable(act.getResources().getDrawable(BotaInt.getCard(act.flipdex.get(1))));
		}	
		card.setId(1);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pentagram_air);
		if (BotaInt.myDeck.reversed[act.flipdex.get(2)]) {			
			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCard(act.flipdex.get(2)));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postRotate(180);
			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
			card.setBackgroundDrawable(bmd);
		} else {
			card.setBackgroundDrawable(act.getResources().getDrawable(BotaInt.getCard(act.flipdex.get(2))));
		}	
		card.setId(2);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pentagram_earth);
		if (BotaInt.myDeck.reversed[act.flipdex.get(3)]) {			
			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCard(act.flipdex.get(3)));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postRotate(180);
			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
			card.setBackgroundDrawable(bmd);
		} else {
			card.setBackgroundDrawable(act.getResources().getDrawable(BotaInt.getCard(act.flipdex.get(3))));
		}	
		card.setId(3);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pentagram_spirit);
		if (BotaInt.myDeck.reversed[act.flipdex.get(4)]) {			
			Bitmap bmp = BitmapFactory.decodeResource(act.getResources(), BotaInt.getCard(act.flipdex.get(4)));
			int w = bmp.getWidth();
			int h = bmp.getHeight();
			Matrix mtx = new Matrix();
			mtx.postRotate(180);
			Bitmap rotatedBMP = Bitmap.createBitmap(bmp, 0, 0, w, h, mtx, true);
			BitmapDrawable bmd = new BitmapDrawable(rotatedBMP);			
			card.setBackgroundDrawable(bmd);
		} else {
			card.setBackgroundDrawable(act.getResources().getDrawable(BotaInt.getCard(act.flipdex.get(4))));
		}	
		card.setId(4);
		card.setOnClickListener(act);
		
		return layout;
	}


}
