package liberus.tarot.spread.gothic;

import java.util.ArrayList;
import java.util.regex.Matcher;

import liberus.tarot.android.R;
import liberus.tarot.deck.Deck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.interpretation.Interpretation;
import liberus.tarot.os.activity.AbstractTarotBotActivity;
import liberus.tarot.os.activity.TarotBotActivity;
import liberus.tarot.spread.Spread;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

public abstract class GothicSpread extends Spread {
	
public GothicSpread(Interpretation inInt) {
		super(inInt);
		// TODO Auto-generated constructor stub
	}
@Override
public String getInterpretation(int circled, Context appcontext) {
		
		int pos = working.indexOf(circled);
		int[] context = getContext(circled, appcontext);
		String returner = "";
		
		
		returner += "<big><b>"+appcontext.getString(BotaInt.getTitle(circled))+"</big></b><br/>";
		
		if (myDeck.isReversed(circled) && appcontext.getString(BotaInt.getReversed(circled)).length() > 0)
			returner += "<br/><i>"+ appcontext.getString(BotaInt.getReversed(circled))+"<br/>";
		else 
			returner += "<br/><i>"+appcontext.getString(BotaInt.getMeanings(circled))+"<br/>";
		
		if (BotaInt.getAbst(circled) > 0 && appcontext.getString(BotaInt.getAbst(circled)).length() > 0)
			returner += "<br/><i>"+appcontext.getString(R.string.general_label)+"</i>: " + appcontext.getString(BotaInt.getAbst(circled))+"<br/>";
		
		return returner;
	}
}
