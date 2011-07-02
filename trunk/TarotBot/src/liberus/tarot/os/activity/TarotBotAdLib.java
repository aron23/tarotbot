package liberus.tarot.os.activity;


import liberus.tarot.android.noads.R;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AdRequest;



//public class TarotBotAdLib extends TarotBotActivity {
public abstract class TarotBotAdLib extends TarotBotActivity {
	@Override
	public String getMyType() {
		return "liberus.tarot.android.adlib";
	}

	@Override
	public long getHiResZipSize() {
		// TODO Auto-generated method stub
		return 14456061;
	}

	@Override
	public String getMyFolder() {
		// TODO Auto-generated method stub
		return "TarotBotAdLib";
	}
	
	@Override
	public void establishMenu(int menu) {
		if (menu == R.layout.mainmenu) {
			menu = R.layout.mainmenuad;
		} else if (menu == R.layout.spreadmenu) {
			menu = R.layout.spreadmenuad;
		} else if (menu == R.layout.optionsmenu) {
			menu = R.layout.optionsmenuad;
		}
		View menulay = inflater.inflate(menu, null);
		setBackground(menulay);
		setContentView(menulay);
		
		AdView adView = new AdView(this, AdSize.BANNER, getMyAdmobID());
		LinearLayout layout = (LinearLayout)findViewById(R.id.ad);
	    // Add the adView to it
	    layout.addView(adView);
	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());
	}

	public abstract String getMyAdmobID();	
}
