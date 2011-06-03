package liberus.tarot.os.activity;


import liberus.tarot.android.noads.R;
import android.view.View;

import com.google.ads.AdView;
import com.google.ads.AdRequest;



//public class TarotBotAdLib extends TarotBotActivity {
public class TarotBotAdLib extends TarotBotActivity {
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
		View menulay = inflater.inflate(menu, null);
		setBackground(menulay);
		setContentView(menulay);
		AdView myad = (AdView)findViewById(R.id.ad);
		AdRequest myreq = new AdRequest();
	}	
}
