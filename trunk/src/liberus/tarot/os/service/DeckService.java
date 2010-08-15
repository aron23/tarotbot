package liberus.tarot.os.service;

import liberus.tarot.os.service.IDeckService;
import liberus.tarot.interpretation.BotaInt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class DeckService extends Service {
	@Override
	public void onCreate() {
		super.onCreate();
		;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	/**
     * The IAdderService is defined through IDL
     */
    private final IDeckService.Stub binder = 
			new IDeckService.Stub() {
		public int getCard(int i) throws RemoteException {
			return BotaInt.getCard(i);
		}
    };

}
