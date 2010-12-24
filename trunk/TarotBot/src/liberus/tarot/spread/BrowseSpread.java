package liberus.tarot.spread;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.os.activity.TarotBotActivity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class BrowseSpread extends Spread {
	
	
	private static int significatorIn;
	private int myNum;


	public BrowseSpread(Interpretation in) {
		super(in);
		myNum = 78;
		myLabels = new String[78];
		browsing = true;
	}
	
	@Override
	public void operate(Context context, boolean loading) {
		for (int i = 0; i < 78; i++)
			working.add(i);
		Spread.circles = working;
	}
	@Override
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
		return R.layout.browserlayout;
	}

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		layout = populateTrumps(layout,act,ctx);
		layout = populateWands(layout,act,ctx);
		layout = populateCups(layout,act,ctx);
		layout = populateSwords(layout,act,ctx);
		layout = populatePentacles(layout,act,ctx);
		return layout;
	}

	private View populateWands(View layout, AbstractTarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.wands_01);
		placeImage(Interpretation.getCardIndex(R.id.wands_01),card,ctx);
		card.setId(R.id.wands_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_02);
		placeImage(Interpretation.getCardIndex(R.id.wands_02),card,ctx);
		card.setId(R.id.wands_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_03);
		placeImage(Interpretation.getCardIndex(R.id.wands_03),card,ctx);
		card.setId(R.id.wands_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_04);
		placeImage(Interpretation.getCardIndex(R.id.wands_04),card,ctx);
		card.setId(R.id.wands_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_05);
		placeImage(Interpretation.getCardIndex(R.id.wands_05),card,ctx);
		card.setId(R.id.wands_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_06);
		placeImage(Interpretation.getCardIndex(R.id.wands_06),card,ctx);
		card.setId(R.id.wands_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_07);
		placeImage(Interpretation.getCardIndex(R.id.wands_07),card,ctx);
		card.setId(R.id.wands_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_08);
		placeImage(Interpretation.getCardIndex(R.id.wands_08),card,ctx);
		card.setId(R.id.wands_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_09);
		placeImage(Interpretation.getCardIndex(R.id.wands_09),card,ctx);
		card.setId(R.id.wands_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_10);
		placeImage(Interpretation.getCardIndex(R.id.wands_10),card,ctx);
		card.setId(R.id.wands_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_page);
		placeImage(Interpretation.getCardIndex(R.id.wands_page),card,ctx);
		card.setId(R.id.wands_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_knight);
		placeImage(Interpretation.getCardIndex(R.id.wands_knight),card,ctx);
		card.setId(R.id.wands_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_queen);
		placeImage(Interpretation.getCardIndex(R.id.wands_queen),card,ctx);
		card.setId(R.id.wands_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_king);
		placeImage(Interpretation.getCardIndex(R.id.wands_king),card,ctx);
		card.setId(R.id.wands_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}

	private View populateCups(View layout, AbstractTarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.cups_01);
		placeImage(Interpretation.getCardIndex(R.id.cups_01),card,ctx);
		card.setId(R.id.cups_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_02);
		placeImage(Interpretation.getCardIndex(R.id.cups_02),card,ctx);
		card.setId(R.id.cups_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_03);
		placeImage(Interpretation.getCardIndex(R.id.cups_03),card,ctx);
		card.setId(R.id.cups_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_04);
		placeImage(Interpretation.getCardIndex(R.id.cups_04),card,ctx);
		card.setId(R.id.cups_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_05);
		placeImage(Interpretation.getCardIndex(R.id.cups_05),card,ctx);
		card.setId(R.id.cups_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_06);
		placeImage(Interpretation.getCardIndex(R.id.cups_06),card,ctx);
		card.setId(R.id.cups_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_07);
		placeImage(Interpretation.getCardIndex(R.id.cups_07),card,ctx);
		card.setId(R.id.cups_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_08);
		placeImage(Interpretation.getCardIndex(R.id.cups_08),card,ctx);
		card.setId(R.id.cups_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_09);
		placeImage(Interpretation.getCardIndex(R.id.cups_09),card,ctx);
		card.setId(R.id.cups_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_10);
		placeImage(Interpretation.getCardIndex(R.id.cups_10),card,ctx);
		card.setId(R.id.cups_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_page);
		placeImage(Interpretation.getCardIndex(R.id.cups_page),card,ctx);
		card.setId(R.id.cups_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_knight);
		placeImage(Interpretation.getCardIndex(R.id.cups_knight),card,ctx);
		card.setId(R.id.cups_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_queen);
		placeImage(Interpretation.getCardIndex(R.id.cups_queen),card,ctx);
		card.setId(R.id.cups_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_king);
		placeImage(Interpretation.getCardIndex(R.id.cups_king),card,ctx);
		card.setId(R.id.cups_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
	
	private View populateSwords(View layout, AbstractTarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.swords_01);
		placeImage(Interpretation.getCardIndex(R.id.swords_01),card,ctx);
		card.setId(R.id.swords_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_02);
		placeImage(Interpretation.getCardIndex(R.id.swords_02),card,ctx);
		card.setId(R.id.swords_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_03);
		placeImage(Interpretation.getCardIndex(R.id.swords_03),card,ctx);
		card.setId(R.id.swords_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_04);
		placeImage(Interpretation.getCardIndex(R.id.swords_04),card,ctx);
		card.setId(R.id.swords_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_05);
		placeImage(Interpretation.getCardIndex(R.id.swords_05),card,ctx);
		card.setId(R.id.swords_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_06);
		placeImage(Interpretation.getCardIndex(R.id.swords_06),card,ctx);
		card.setId(R.id.swords_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_07);
		placeImage(Interpretation.getCardIndex(R.id.swords_07),card,ctx);
		card.setId(R.id.swords_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_08);
		placeImage(Interpretation.getCardIndex(R.id.swords_08),card,ctx);
		card.setId(R.id.swords_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_09);
		placeImage(Interpretation.getCardIndex(R.id.swords_09),card,ctx);
		card.setId(R.id.swords_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_10);
		placeImage(Interpretation.getCardIndex(R.id.swords_10),card,ctx);
		card.setId(R.id.swords_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_page);
		placeImage(Interpretation.getCardIndex(R.id.swords_page),card,ctx);
		card.setId(R.id.swords_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_knight);
		placeImage(Interpretation.getCardIndex(R.id.swords_knight),card,ctx);
		card.setId(R.id.swords_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_queen);
		placeImage(Interpretation.getCardIndex(R.id.swords_queen),card,ctx);
		card.setId(R.id.swords_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_king);
		placeImage(Interpretation.getCardIndex(R.id.swords_king),card,ctx);
		card.setId(R.id.swords_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
	
	private View populatePentacles(View layout, AbstractTarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.pent_01);
		placeImage(Interpretation.getCardIndex(R.id.pent_01),card,ctx);
		card.setId(R.id.pent_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_02);
		placeImage(Interpretation.getCardIndex(R.id.pent_02),card,ctx);
		card.setId(R.id.pent_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_03);
		placeImage(Interpretation.getCardIndex(R.id.pent_03),card,ctx);
		card.setId(R.id.pent_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_04);
		placeImage(Interpretation.getCardIndex(R.id.pent_04),card,ctx);
		card.setId(R.id.pent_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_05);
		placeImage(Interpretation.getCardIndex(R.id.pent_05),card,ctx);
		card.setId(R.id.pent_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_06);
		placeImage(Interpretation.getCardIndex(R.id.pent_06),card,ctx);
		card.setId(R.id.pent_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_07);
		placeImage(Interpretation.getCardIndex(R.id.pent_07),card,ctx);
		card.setId(R.id.pent_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_08);
		placeImage(Interpretation.getCardIndex(R.id.pent_08),card,ctx);
		card.setId(R.id.pent_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_09);
		placeImage(Interpretation.getCardIndex(R.id.pent_09),card,ctx);
		card.setId(R.id.pent_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_10);
		placeImage(Interpretation.getCardIndex(R.id.pent_10),card,ctx);
		card.setId(R.id.pent_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_page);
		placeImage(Interpretation.getCardIndex(R.id.pent_page),card,ctx);
		card.setId(R.id.pent_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_knight);
		placeImage(Interpretation.getCardIndex(R.id.pent_knight),card,ctx);
		card.setId(R.id.pent_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_queen);
		placeImage(Interpretation.getCardIndex(R.id.pent_queen),card,ctx);
		card.setId(R.id.pent_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_king);
		placeImage(Interpretation.getCardIndex(R.id.pent_king),card,ctx);
		card.setId(R.id.pent_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
	
	private View populateTrumps(View layout, AbstractTarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.trumps_01);
		placeImage(Interpretation.getCardIndex(R.id.trumps_01),card,ctx);
		card.setId(R.id.trumps_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_02);
		placeImage(Interpretation.getCardIndex(R.id.trumps_02),card,ctx);
		card.setId(R.id.trumps_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_03);
		placeImage(Interpretation.getCardIndex(R.id.trumps_03),card,ctx);
		card.setId(R.id.trumps_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_04);
		placeImage(Interpretation.getCardIndex(R.id.trumps_04),card,ctx);
		card.setId(R.id.trumps_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_05);
		placeImage(Interpretation.getCardIndex(R.id.trumps_05),card,ctx);
		card.setId(R.id.trumps_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_06);
		placeImage(Interpretation.getCardIndex(R.id.trumps_06),card,ctx);
		card.setId(R.id.trumps_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_07);
		placeImage(Interpretation.getCardIndex(R.id.trumps_07),card,ctx);
		card.setId(R.id.trumps_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_08);
		placeImage(Interpretation.getCardIndex(R.id.trumps_08),card,ctx);
		card.setId(R.id.trumps_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_09);
		placeImage(Interpretation.getCardIndex(R.id.trumps_09),card,ctx);
		card.setId(R.id.trumps_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_10);
		placeImage(Interpretation.getCardIndex(R.id.trumps_10),card,ctx);
		card.setId(R.id.trumps_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_11);
		placeImage(Interpretation.getCardIndex(R.id.trumps_11),card,ctx);
		card.setId(R.id.trumps_11);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_12);
		placeImage(Interpretation.getCardIndex(R.id.trumps_12),card,ctx);
		card.setId(R.id.trumps_12);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_13);
		placeImage(Interpretation.getCardIndex(R.id.trumps_13),card,ctx);
		card.setId(R.id.trumps_13);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_14);
		placeImage(Interpretation.getCardIndex(R.id.trumps_14),card,ctx);
		card.setId(R.id.trumps_14);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_15);
		placeImage(Interpretation.getCardIndex(R.id.trumps_15),card,ctx);
		card.setId(R.id.trumps_15);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_16);
		placeImage(Interpretation.getCardIndex(R.id.trumps_16),card,ctx);
		card.setId(R.id.trumps_16);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_17);
		placeImage(Interpretation.getCardIndex(R.id.trumps_17),card,ctx);
		card.setId(R.id.trumps_17);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_18);
		placeImage(Interpretation.getCardIndex(R.id.trumps_18),card,ctx);
		card.setId(R.id.trumps_18);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_19);
		placeImage(Interpretation.getCardIndex(R.id.trumps_19),card,ctx);
		card.setId(R.id.trumps_19);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_20);
		placeImage(Interpretation.getCardIndex(R.id.trumps_20),card,ctx);
		card.setId(R.id.trumps_20);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_21);
		placeImage(Interpretation.getCardIndex(R.id.trumps_21),card,ctx);
		card.setId(R.id.trumps_21);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.trumps_22);
		placeImage(Interpretation.getCardIndex(R.id.trumps_22),card,ctx);
		card.setId(R.id.trumps_22);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
}
