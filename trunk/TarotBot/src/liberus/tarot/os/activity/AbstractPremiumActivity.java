package liberus.tarot.os.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.android.vending.licensing.AESObfuscator;
import com.android.vending.licensing.LicenseChecker;
import com.android.vending.licensing.LicenseCheckerCallback;
import com.android.vending.licensing.ServerManagedPolicy;


public abstract class AbstractPremiumActivity extends Activity  {
	protected ServerManagedPolicy myPolicy;
	protected Handler mHandler;
	protected LicenseChecker mChecker;
	protected MyLicenseCheckerCallback mLicenseCheckerCallback;
	protected boolean _active = true;
	// Generate 20 random bytes, and put them here.
    private static final byte[] SALT = new byte[] {
     -16, 68, 35, -28, -13, -57, 34, -25, 51, 77, -95,
     -45, 77, -117, -36, -113, -11, 32, -64, 35
     };
	
	protected void initPolicy(Context ctx, String id, String key) {
		mLicenseCheckerCallback = new MyLicenseCheckerCallback();
		mChecker = new LicenseChecker(
		        ctx, new ServerManagedPolicy(ctx,
		            new AESObfuscator(SALT, getPackageName(), id)),
		            key);	
		doCheck();
	}
	protected void doCheck() {
        setProgressBarIndeterminateVisibility(true);
        mChecker.checkAccess(mLicenseCheckerCallback);        
    }
	protected class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        public void allow() {
            Intent resultIntent = new Intent();
        	resultIntent.putExtra("completed", true);
        	setResult(Activity.RESULT_OK, resultIntent);
        	_active = false;
        	finish();            
        }

        public void dontAllow() {
            Intent resultIntent = new Intent();
        	resultIntent.putExtra("completed", true);
        	//setResult(Activity.RESULT_CANCELED, resultIntent);
        	setResult(Activity.RESULT_OK, resultIntent);
        	_active = false;
        	finish();
        }

		public void applicationError(ApplicationErrorCode errorCode) {
			Intent resultIntent = new Intent();
        	resultIntent.putExtra("completed", true);
        	setResult(Activity.RESULT_OK, resultIntent);
        	//setResult(Activity.RESULT_OK, resultIntent);
        	_active = false;
        	finish();		
        	}
    }

}