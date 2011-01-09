package liberus.tarot.spread.gothic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.os.activity.TarotBotActivity;
import liberus.tarot.android.noads.R;
import liberus.tarot.querant.Querant;
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

public class GothicBotaSpread extends GothicSpread {
	
	private static int significatorIn;
	public static Integer[] heh2;
	public static Integer[] vau;
	public static Integer[] heh1;
	public static Integer[] yod;
	private Integer secondSetStrongest;

	public GothicBotaSpread(Interpretation myInt) {
		super(myInt);
	}
	
	
	protected static boolean hasSignificator(Integer[] deck) {
		for (int i = 0; i < deck.length; i++)
			if (isSignificator(deck[i]))
				return true;
		return false;
	}

	public void operate(Context context,boolean loading) {
		if (!loading) {
			Integer[] shuffled = myDeck.shuffle(new Integer[78],1);
			
			int firstCut = Deck.cut(shuffled);
			Integer[] yodPrime = new Integer[firstCut];
			Integer[] vauPrime = new Integer[shuffled.length-firstCut];
			for (int i=0; i < firstCut; i++)
				yodPrime[i] = shuffled[i];
			int vauCount = 0;
			for (int i=firstCut; i < shuffled.length; i++) {
				vauPrime[vauCount] = shuffled[i];
				vauCount++;
			}
			
			int secondCut = Deck.cut(yodPrime);		
			heh1 = new Integer[secondCut];
			yod = new Integer[yodPrime.length-secondCut];
			for (int i=0; i < secondCut; i++)
				heh1[i] = yodPrime[i];
			int yodCount = 0;
			for (int i=secondCut; i < yodPrime.length; i++) {
				yod[yodCount] = yodPrime[i];
				yodCount++;
			}
			
			int thirdCut = Deck.cut(vauPrime);		
			vau = new Integer[vauPrime.length-thirdCut];
			heh2 = new Integer[thirdCut];
			for (int i=0; i < thirdCut; i++)
				heh2[i] = vauPrime[i];
			vauCount = 0;
			for (int i=thirdCut; i < vauPrime.length; i++) {
				vau[vauCount] = vauPrime[i];
				vauCount++;
			}
			String toreturn = "";
			if (hasSignificator(yod)) {
				System.err.println("yod");
				significatorIn=0;
				working = new ArrayList<Integer> (Arrays.asList(yod));
			} else if (hasSignificator(heh1)) {
				System.err.println("heh1");
				significatorIn=1;		
				working = new ArrayList<Integer> (Arrays.asList(heh1));
			} else if (hasSignificator(vau)) {
				System.err.println("vau");
				significatorIn=2;		
				working = new ArrayList<Integer> (Arrays.asList(vau));
			} else if (hasSignificator(heh2)) {
				System.err.println("heh2");
				significatorIn=3;		
				working = new ArrayList<Integer> (Arrays.asList(heh2));
			} else {
				System.err.println("missing significator");
			}
		}
		
		
		int primaryCircle;
		int significatorIndex=79;				
		ArrayList<Integer> toChew = new ArrayList(working);
		Iterator<Integer> circler = working.iterator();
		
		circles = new ArrayList<Integer>();
		int circled = 79;
		
		boolean circling = true;
		boolean begun = false;
		ArrayList<Integer> hits = new ArrayList<Integer>();

		int circle_index = working.indexOf(getSignificator());
		while (circling) {
			if (circle_index == working.indexOf(getSignificator()) && circles.size() < 1) {
				begun = true;
				circles.add(working.get(circle_index));
				hits.add(circle_index);
				
			} else if (begun) {
				if (hits.contains(circle_index)) {
					secondSetStrongest = working.get(circle_index);  
					circling = false;
					continue;
				}
				circles.add(working.get(circle_index));
				hits.add(circle_index);
			} 
			circle_index += context.getResources().getInteger(BotaInt.getSecondOperationCount(working.get(circle_index)));
			if (circle_index >= working.size())
				circle_index -= working.size();
		
		}
		myLabels = new String[circles.size()];
		for (int i = 0; i < circles.size(); i++) {
			myLabels[i] = context.getString(R.string.position)+" "+i;
		}
		 
	}
	@Override
	public String getInterpretation(int circled, Context appcontext) {
		double factor = (double)12/working.size();
		int sig = working.indexOf(getSignificator());
		int pos = working.indexOf(circled);
		int offset = pos-sig;
		int house;
		if (offset > 0)
			house = (int) Math.floor(factor*(offset+1));
		else
			house = (int) Math.floor(factor*(working.size()+offset));
		int[] context = getContext(circled, appcontext);
		String returner = "";
		
		if (isSignificator(circled)) {
			String pre = getPre(appcontext);
			String post = appcontext.getString(R.string.querant_label)+"<br/>";
			
			if (pre.length() > 0)
				post = pre+"<br/><br/>"+post;
			if (circled==BotaInt.secondSetStrongest)
				post += "<br/><i>"+appcontext.getString(R.string.personal_label)+"</i><br/>";
			
			returner = post;
			
			return returner;
		}
		returner += "<big><b>"+appcontext.getString(BotaInt.getTitle(circled))+"</b></big><br/>";
		
		if (circled==BotaInt.secondSetStrongest)
			returner += "<b>"+appcontext.getString(R.string.significant_label)+"</b><br/>";
		
		if (myDeck.isReversed(circled) && appcontext.getString(BotaInt.getReversed(circled)).length() > 0)
			returner += "<br/>"+ appcontext.getString(BotaInt.getReversed(circled))+"<br/>";
		else 
			returner += "<br/>"+appcontext.getString(BotaInt.getMeanings(circled))+"<br/>";
		
		if (BotaInt.getAbst(circled) > 0 && appcontext.getString(BotaInt.getAbst(circled)).length() > 0)
			returner += "<br/>"+ appcontext.getString(BotaInt.getAbst(circled))+"<br/>";
		return returner;
	}

	
	
	

	public static int getSignificator() {
		int num = 0;

		switch (Interpretation.myQuerant.getElement()) {
		case 0: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =35;
					else if (!Interpretation.myQuerant.male) 
						num =34;
				} else {
					if (Interpretation.myQuerant.male)
						num =33;
					else if (!Interpretation.myQuerant.male)
						num =32;
				} break;				
		case 1: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =49;
					else if (!Interpretation.myQuerant.male)
						num =48;
				} else {
					if (Interpretation.myQuerant.male)
						num =47;
					else if (!Interpretation.myQuerant.male)
						num =46;
				} break;				
		case 2: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =63;
					else if (!Interpretation.myQuerant.male)
						num =62;
				} else {
					if (Interpretation.myQuerant.male)
						num =61;
					else if (!Interpretation.myQuerant.male)
						num =60;
				} break;
		case 3: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male)
						num =77;
					else if (!Interpretation.myQuerant.male)
						num =76;
				} else {
					if (Interpretation.myQuerant.male)
						num =75;
					else if (!Interpretation.myQuerant.male)
						num =74;
				} break;				
		}		
		return num;
	}
	
	protected static boolean isSignificator(int card) {
		
		switch (Interpretation.myQuerant.getElement()) {
		case 0: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 35)
						return true;
					else if (!Interpretation.myQuerant.male && card == 34)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 33)
						return true;
					else if (!Interpretation.myQuerant.male && card == 32)
						return true;
				}
				break;
		case 1: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 49)
						return true;
					else if (!Interpretation.myQuerant.male && card == 48)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 47)
						return true;
					else if (!Interpretation.myQuerant.male && card == 46)
						return true;
				}
				break;
		case 2: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 63)
						return true;
					else if (!Interpretation.myQuerant.male && card == 62)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 61)
						return true;
					else if (!Interpretation.myQuerant.male && card == 60)
						return true;
				}
				break;
		case 3: if (Interpretation.myQuerant.partnered) {
					if (Interpretation.myQuerant.male && card == 77)
						return true;
					else if (!Interpretation.myQuerant.male && card == 76)
						return true;
				} else {
					if (Interpretation.myQuerant.male && card == 75)
						return true;
					else if (!Interpretation.myQuerant.male && card == 74)
						return true;
				}
				break;				
		}
		
		return false;
	}


	@Override
	public int getLayout() {
		if (working.size() == 19)
			return R.layout.botalayout19;
		return R.layout.botalayout20;
	}

	public View populateSpread(View layout, AbstractTarotBotActivity act, Context ctx) {
		
		ImageView card = (ImageView) layout.findViewById(R.id.bota_00);
		placeImage(working.get(0),card,ctx);	
		
		if (circles.contains(working.get(0))) {
			card.setId(circles.indexOf(working.get(0)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(0)))
				layout.findViewById(R.id.bota_00_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_01);
		placeImage(working.get(1),card,ctx);
		if (circles.contains(working.get(1))) {
			card.setId(circles.indexOf(working.get(1)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(1)))
				layout.findViewById(R.id.bota_01_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_02);
		placeImage(working.get(2),card,ctx);
		if (circles.contains(working.get(2))) {
			card.setId(circles.indexOf(working.get(2)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(2)))
				layout.findViewById(R.id.bota_02_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_03);
		placeImage(working.get(3),card,ctx);
		if (circles.contains(working.get(3))) {
			card.setId(circles.indexOf(working.get(3)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(3)))
				layout.findViewById(R.id.bota_03_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_04);
		placeImage(working.get(4),card,ctx);
		if (circles.contains(working.get(4))) {
			card.setId(circles.indexOf(working.get(4)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(4)))
				layout.findViewById(R.id.bota_04_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_05);
		placeImage(working.get(5),card,ctx);
		if (circles.contains(working.get(5))) {
			card.setId(circles.indexOf(working.get(5)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(5)))
				layout.findViewById(R.id.bota_05_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_06);
		placeImage(working.get(6),card,ctx);
		if (circles.contains(working.get(6))) {
			card.setId(circles.indexOf(working.get(6)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(6)))
				layout.findViewById(R.id.bota_06_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_07);
		placeImage(working.get(7),card,ctx);
		if (circles.contains(working.get(7))) {
			card.setId(circles.indexOf(working.get(7)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(7)))
				layout.findViewById(R.id.bota_07_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_08);
		placeImage(working.get(8),card,ctx);
		if (circles.contains(working.get(8))) {
			card.setId(circles.indexOf(working.get(8)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(8)))
				layout.findViewById(R.id.bota_08_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_09);
		placeImage(working.get(9),card,ctx);
		if (circles.contains(working.get(9))) {
			card.setId(circles.indexOf(working.get(9)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(9)))
				layout.findViewById(R.id.bota_09_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_10);
		placeImage(working.get(10),card,ctx);
		if (circles.contains(working.get(10))) {
			card.setId(circles.indexOf(working.get(10)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(10)))
				layout.findViewById(R.id.bota_10_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_11);
		placeImage(working.get(11),card,ctx);
		if (circles.contains(working.get(11))) {
			card.setId(circles.indexOf(working.get(11)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(11)))
				layout.findViewById(R.id.bota_11_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_12);
		placeImage(working.get(12),card,ctx);
		if (circles.contains(working.get(12))) {
			card.setId(circles.indexOf(working.get(12)));

			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(12)))
				layout.findViewById(R.id.bota_12_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_13);
		placeImage(working.get(13),card,ctx);
		if (circles.contains(working.get(13))) {
			card.setId(circles.indexOf(working.get(13)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(13)))
				layout.findViewById(R.id.bota_13_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_14);
		placeImage(working.get(14),card,ctx);
		if (circles.contains(working.get(14))) {
			card.setId(circles.indexOf(working.get(14)));

			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(14)))
				layout.findViewById(R.id.bota_14_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		
		card = (ImageView) layout.findViewById(R.id.bota_15);
		placeImage(working.get(15),card,ctx);
		if (circles.contains(working.get(15))) {
			card.setId(circles.indexOf(working.get(15)));

			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(15)))
				layout.findViewById(R.id.bota_15_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_16);
		placeImage(working.get(16),card,ctx);
		if (circles.contains(working.get(16))) {
			card.setId(circles.indexOf(working.get(16)));

			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(16)))
				layout.findViewById(R.id.bota_16_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_17);
		placeImage(working.get(17),card,ctx);
		if (circles.contains(working.get(17))) {
			card.setId(circles.indexOf(working.get(17)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(17)))
				layout.findViewById(R.id.bota_17_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		card = (ImageView) layout.findViewById(R.id.bota_18);
		placeImage(working.get(18),card,ctx);
		if (circles.contains(working.get(18))) {
			card.setId(circles.indexOf(working.get(18)));
			card.setOnClickListener(act);
			if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(18)))
				layout.findViewById(R.id.bota_18_back).setBackgroundColor(Color.RED);
		} else {
			card.setAlpha(75);
		}
		
		if (working.size() == 20) {
			card = (ImageView) layout.findViewById(R.id.bota_19);
			placeImage(working.get(19),card,ctx);
			if (circles.contains(working.get(19))) {
				card.setId(circles.indexOf(working.get(19)));
				card.setOnClickListener(act);
				if (TarotBotActivity.secondSetIndex == circles.indexOf(working.get(19)))
					layout.findViewById(R.id.bota_19_back).setBackgroundColor(Color.RED);
			} else {
				card.setAlpha(75);
		}
		}
		return layout;
	}
	
}
