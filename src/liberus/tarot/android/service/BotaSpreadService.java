package liberus.tarot.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BotaSpreadService extends Service {
	private static final String SPREADTAG = "BotaSpread";
	
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		
	}

	@Override
	public void onDestroy() {
		
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		
	}
}
