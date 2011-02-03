package liberus.tarot.os.activity;


import liberus.tarot.android.noads.R;
import liberus.tarot.os.activity.TarotBotActivity;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;

import com.admob.android.ads.AdManager;
import com.admob.android.ads.AdView;



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
		myad.requestFreshAd();
	}	
}
