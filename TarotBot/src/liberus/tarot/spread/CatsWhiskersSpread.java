package liberus.tarot.spread;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.android.noads.R;
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

public class CatsWhiskersSpread extends Spread {
	
	
	private static int significatorIn;
	private int myNum;


	public CatsWhiskersSpread(Interpretation myInt, String[] labels) {
		super(myInt);
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
		return R.layout.catswhiskerslayout;
	}

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		act.setBackground(layout);
		ImageView card = (ImageView) layout.findViewById(R.id.past_cause);
		placeImage(act.flipdex.get(0),card,ctx);
		card.setId(0);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 0)
			layout.findViewById(R.id.past_cause_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.past_neg);
		placeImage(act.flipdex.get(1),card,ctx);	
		card.setId(1);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 1)
			layout.findViewById(R.id.past_neg_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.past_pos);
		placeImage(act.flipdex.get(2),card,ctx);	
		card.setId(2);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 2)
			layout.findViewById(R.id.past_pos_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.need);
		placeImage(act.flipdex.get(3),card,ctx);	
		card.setId(3);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 3)
			layout.findViewById(R.id.need_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.future_neg);
		placeImage(act.flipdex.get(4),card,ctx);	
		card.setId(4);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 4)
			layout.findViewById(R.id.future_neg_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.future_pos);
		placeImage(act.flipdex.get(5),card,ctx);	
		card.setId(5);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 5)
			layout.findViewById(R.id.future_pos_back).setBackgroundColor(Color.RED);
		
		card = (ImageView) layout.findViewById(R.id.future_advice);
		placeImage(act.flipdex.get(6),card,ctx);	
		card.setId(6);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 6)
			layout.findViewById(R.id.future_advice_back).setBackgroundColor(Color.RED);
		
		return layout;
	}


}
