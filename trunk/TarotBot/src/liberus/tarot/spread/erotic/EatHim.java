package liberus.tarot.spread.erotic;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.EroticInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.android.noads.R;
import liberus.tarot.spread.Spread;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class EatHim extends Spread {
	
	
	private static int significatorIn;
	private int myNum;


	public EatHim(Interpretation myInt, String[] labels) {
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
		
		
		returner += "<big><b>"+appcontext.getString(EroticInt.getTitle(circled))+"</big></b><br/>";
		if (EroticInt.getKeyword(circled) > 0 && appcontext.getString(EroticInt.getKeyword(circled)).length() > 0)
			returner += appcontext.getString(EroticInt.getKeyword(circled))+"<br/>";
		if (EroticInt.getJourney(circled) > 0 && appcontext.getString(EroticInt.getJourney(circled)).length() > 0)
			returner += appcontext.getString(EroticInt.getJourney(circled))+"<br/>";
		
		if (circled==EroticInt.secondSetStrongest)
			returner += "<b>"+appcontext.getString(R.string.significant_label)+"</b><br/>";
		
		if (EroticInt.getAbst(circled) > 0 && appcontext.getString(EroticInt.getAbst(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.general_label)+"</i>: " + appcontext.getString(EroticInt.getAbst(circled))+"<br/>";
		if (significatorIn == 1 && EroticInt.getInSpiritualMatters(circled) > 0 && appcontext.getString(EroticInt.getInSpiritualMatters(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(EroticInt.getInSpiritualMatters(circled))+"<br/>";
		else if (significatorIn == 3 && EroticInt.getInMaterialMatters(circled) > 0 && appcontext.getString(EroticInt.getInMaterialMatters(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(EroticInt.getInMaterialMatters(circled))+"<br/>";
		else if (EroticInt.isWellDignified(context) &! EroticInt.isIllDignified(context) && EroticInt.getWellDignified(circled) > 0 && appcontext.getString(EroticInt.getWellDignified(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(EroticInt.getWellDignified(circled))+"<br/>";
		else if (EroticInt.isIllDignified(context) &! EroticInt.isWellDignified(context) && EroticInt.getIllDignified(circled) > 0 && appcontext.getString(EroticInt.getIllDignified(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(EroticInt.getIllDignified(circled))+"<br/>";
		else if (EroticInt.getMeanings(circled) > 0 && appcontext.getString(EroticInt.getMeanings(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.directly_label)+"</i>: "+ appcontext.getString(EroticInt.getMeanings(circled))+"<br/>";
		/*if (EroticInt.getOppositionNumber(circled) > 0) {
			List oppNumList = Arrays.asList(appcontext.getResources().getIntArray(EroticInt.getOppositionNumber(circled)));
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (oppNumList != null && oppNumList.contains(lefty))
				returner += appcontext.getString(R.string.has_been_opposed_label)+": "+appcontext.getResources().getIntArray(EroticInt.getOppositionText(circled))[oppNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (oppNumList != null && oppNumList.contains(righty))
				returner += appcontext.getString(R.string.will_be_opposed_label)+": "+appcontext.getResources().getIntArray(EroticInt.getOppositionText(circled))[oppNumList.indexOf(righty)]+"<br/>";
		}
		if (EroticInt.getReinforcementNumber(circled) > 0) {
			List reNumList = Arrays.asList(appcontext.getResources().getIntArray(EroticInt.getReinforcementNumber(circled)));
			
			String lefty = String.valueOf(getCardToTheLeft(circled));
			if (getCardToTheLeft(circled) < 10)
				lefty = "10"+lefty;
			else
				lefty = "1"+lefty;
			if (reNumList != null && reNumList.contains(lefty))
				returner += appcontext.getString(R.string.has_been_reinforced_label)+": "+appcontext.getResources().getIntArray(EroticInt.getReinforcementText(circled))[reNumList.indexOf(lefty)]+"<br/>";
			
			String righty = String.valueOf(getCardToTheRight(circled));
			if (getCardToTheRight(circled) < 10)
				righty = "10"+righty;
			else
				righty = "1"+righty;
			if (reNumList != null && reNumList.contains(righty))
				returner += appcontext.getString(R.string.will_be_reinforced_label)+": "+appcontext.getResources().getIntArray(EroticInt.getReinforcementText(circled))[reNumList.indexOf(righty)]+"<br/>";
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
		return R.layout.eathimlayout;
	}

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.eat_him_erect);
		placeImage(act.flipdex.get(0),card,ctx);
		card.setId(0);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 0)
			card.setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
		
		card = (ImageView) layout.findViewById(R.id.eat_him_bobbing);
		placeCustomImage(310,act.flipdex.get(1),card,ctx);
		card.setId(1);
		card.setOnClickListener(act);
		if (act.secondSetIndex == 1)
			card.setColorFilter(0xFFFFFF00, PorterDuff.Mode.MULTIPLY);
		
		return layout;
	}


}
