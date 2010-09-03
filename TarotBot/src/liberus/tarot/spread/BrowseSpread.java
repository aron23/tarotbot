package liberus.tarot.spread;

import java.util.Arrays;
import java.util.List;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.os.activity.TarotBotActivity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public class BrowseSpread extends Spread {
	
	
	private static int significatorIn;
	private int myNum;


	public BrowseSpread(BotaInt in) {
		super(in);
		myNum = 78;
		myLabels = new String[78];
	}
	
	@Override
	public void operate(Context context, boolean loading) {
	
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

	@Override
	public View navigate(View layout, TarotBotActivity act,Context ctx) {
		return populateSpread(layout,act,ctx);
	}

	private View populateSpread(View layout, TarotBotActivity act,Context ctx) {
		layout = populateTrumps(layout,act,ctx);
		layout = populateWands(layout,act,ctx);
		layout = populateCups(layout,act,ctx);
		layout = populateSwords(layout,act,ctx);
		layout = populatePentacles(layout,act,ctx);
		return layout;
	}

	private View populateWands(View layout, TarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.wands_01);
		card.setId(R.id.wands_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_02);
		card.setId(R.id.wands_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_03);
		card.setId(R.id.wands_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_04);
		card.setId(R.id.wands_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_05);
		card.setId(R.id.wands_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_06);
		card.setId(R.id.wands_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_07);
		card.setId(R.id.wands_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_08);
		card.setId(R.id.wands_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_09);
		card.setId(R.id.wands_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_10);
		card.setId(R.id.wands_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_page);
		card.setId(R.id.wands_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_knight);
		card.setId(R.id.wands_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_queen);
		card.setId(R.id.wands_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.wands_king);
		card.setId(R.id.wands_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}

	private View populateCups(View layout, TarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.cups_01);
		card.setId(R.id.cups_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_02);
		card.setId(R.id.cups_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_03);
		card.setId(R.id.cups_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_04);
		card.setId(R.id.cups_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_05);
		card.setId(R.id.cups_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_06);
		card.setId(R.id.cups_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_07);
		card.setId(R.id.cups_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_08);
		card.setId(R.id.cups_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_09);
		card.setId(R.id.cups_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_10);
		card.setId(R.id.cups_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_page);
		card.setId(R.id.cups_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_knight);
		card.setId(R.id.cups_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_queen);
		card.setId(R.id.cups_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.cups_king);
		card.setId(R.id.cups_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
	
	private View populateSwords(View layout, TarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.swords_01);
		card.setId(R.id.swords_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_02);
		card.setId(R.id.swords_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_03);
		card.setId(R.id.swords_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_04);
		card.setId(R.id.swords_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_05);
		card.setId(R.id.swords_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_06);
		card.setId(R.id.swords_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_07);
		card.setId(R.id.swords_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_08);
		card.setId(R.id.swords_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_09);
		card.setId(R.id.swords_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_10);
		card.setId(R.id.swords_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_page);
		card.setId(R.id.swords_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_knight);
		card.setId(R.id.swords_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_queen);
		card.setId(R.id.swords_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.swords_king);
		card.setId(R.id.swords_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
	
	private View populatePentacles(View layout, TarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.pent_01);
		card.setId(R.id.pent_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_02);
		card.setId(R.id.pent_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_03);
		card.setId(R.id.pent_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_04);
		card.setId(R.id.pent_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_05);
		card.setId(R.id.pent_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_06);
		card.setId(R.id.pent_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_07);
		card.setId(R.id.pent_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_08);
		card.setId(R.id.pent_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_09);
		card.setId(R.id.pent_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_10);
		card.setId(R.id.pent_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_page);
		card.setId(R.id.pent_page);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_knight);
		card.setId(R.id.pent_knight);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_queen);
		card.setId(R.id.pent_queen);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.pent_king);
		card.setId(R.id.pent_king);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
	
	private View populateTrumps(View layout, TarotBotActivity act,Context ctx) {
		ImageView card = (ImageView) layout.findViewById(R.id.the_fool_00);
		card.setId(R.id.the_fool_00);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_magician_01);
		card.setId(R.id.the_magician_01);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_high_priestess_02);
		card.setId(R.id.the_high_priestess_02);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_empress_03);
		card.setId(R.id.the_empress_03);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_emperor_04);
		card.setId(R.id.the_emperor_04);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_hierophant_05);
		card.setId(R.id.the_hierophant_05);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_lovers_06);
		card.setId(R.id.the_lovers_06);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_chariot_07);
		card.setId(R.id.the_chariot_07);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.strength_08);
		card.setId(R.id.strength_08);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_hermit_09);
		card.setId(R.id.the_hermit_09);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_wheel_of_fortune_10);
		card.setId(R.id.the_wheel_of_fortune_10);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.justice_11);
		card.setId(R.id.justice_11);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_hanged_man_12);
		card.setId(R.id.the_hanged_man_12);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.death_13);
		card.setId(R.id.death_13);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.temperance_14);
		card.setId(R.id.temperance_14);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_devil_15);
		card.setId(R.id.the_devil_15);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_tower_16);
		card.setId(R.id.the_tower_16);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_star_17);
		card.setId(R.id.the_star_17);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_moon_18);
		card.setId(R.id.the_moon_18);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_sun_19);
		card.setId(R.id.the_sun_19);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.judgement_20);
		card.setId(R.id.judgement_20);
		card.setOnClickListener(act);
		
		card = (ImageView) layout.findViewById(R.id.the_world_21);
		card.setId(R.id.the_world_21);
		card.setOnClickListener(act);
		
		//layout.setOnTouchListener(act);	
		return layout;
	}
}
