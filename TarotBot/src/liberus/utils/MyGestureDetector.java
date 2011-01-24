package liberus.utils;

import liberus.tarot.os.activity.AbstractTarotBotActivity;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;

public class MyGestureDetector extends SimpleOnGestureListener implements OnGestureListener {

	AbstractTarotBotActivity tarot;
	public MyGestureDetector(AbstractTarotBotActivity myTarot) {
		tarot = myTarot;
	}
	public boolean onDoubleTap(MotionEvent e) {
		if (!tarot.state.equals("single"))
			tarot.navigate();
		return true; 
	}
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		//try {
		if (Math.abs(e1.getY() - e2.getY()) < tarot.SWIPE_MAX_OFF_PATH) {
			// right to left swipe
			if(e1.getX() - e2.getX() > tarot.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > tarot.SWIPE_THRESHOLD_VELOCITY) {
				/*if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE && laidout.get(secondSetIndex+1).findViewById(R.id.interpretation) != null)						
					redisplay();*/
				tarot.incrementSecondSet(tarot.secondSetIndex);
				return true;
				// left to right swipe
			}  else if (e2.getX() - e1.getX() > tarot.SWIPE_MIN_DISTANCE && Math.abs(velocityX) > tarot.SWIPE_THRESHOLD_VELOCITY) {
				/*if (laidout.get(secondSetIndex+1).findViewById(R.id.interpretation) != null)						
					redisplay();*/
				tarot.decrementSecondSet(tarot.secondSetIndex);
				return true;
			}
		}

		if (Math.abs(e1.getX() - e2.getX()) < tarot.SWIPE_MAX_OFF_PATH) {
			if (tarot.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				if (!tarot.infoDisplayed)
					tarot.showInfo(tarot.getResources().getConfiguration().orientation);
				else
					tarot.redisplay();		
			}
			
			return true;		         
		}
		//} catch (Exception e) {
		// System.out.println(e.getMessage());
		//}
		return false;
	}

}
