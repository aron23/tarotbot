package liberus.tarot.os.activity;


import android.app.Activity;
import android.content.Context;
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
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
            // Should allow user access.
            //displayResult(getString(R.string.allow));
        }

        public void dontAllow() {
            if (isFinishing()) {
                // Don't update UI if Activity is finishing.
                return;
            }
           //displayResult("TarotBot premium apps are not intended to install ouside of the Android market");
            finish();
        }

		public void applicationError(ApplicationErrorCode errorCode) {
			// TODO Auto-generated method stub
			
		}
    }

}