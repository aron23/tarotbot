package liberus.tarot.os.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import liberus.tarot.android.R;
import liberus.tarot.android.R.id;
import liberus.tarot.android.R.layout;
import liberus.tarot.deck.RiderWaiteDeck;
import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.os.activity.AbstractTarotBotActivity.MyGestureDetector;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CardForTheDayActivity extends Activity implements OnClickListener
{
	protected LayoutInflater inflater;    
	private static final int SWIPE_MIN_DISTANCE = 80;
    private static final int SWIPE_MAX_OFF_PATH = 180;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	private OnTouchListener gestureListener;
	private RelativeLayout secondlayout;
	private ImageView divine;
	private View showing;
	private Button closure;
	private int seed;
	private static BotaInt myInt;
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
	    super.onCreate(savedInstanceState);
	    setFullscreen();
	    setContentView(R.layout.reading);
     
        inflater = LayoutInflater.from(this);
        
        
        secondlayout = (RelativeLayout) this.findViewById(R.id.secondsetlayout);
        myInt = new BotaInt(new RiderWaiteDeck(), null);
        
        SharedPreferences readingPrefs = getSharedPreferences("tarotbot.random", 0);

		Random rand = new Random();
		seed = (readingPrefs.getInt("seed", BotaInt.getRandom(getApplicationContext()).nextInt()));

        ((ImageView) this.findViewById(R.id.divine)).setImageDrawable(getResources().getDrawable(BotaInt.getCardForTheDay(this.getApplicationContext(),seed)));
        //(getResources().getDrawable(BotaInt.getCardForTheDay(this.getApplicationContext())));
        
        ((ImageView) this.findViewById(R.id.divine)).setClickable(true);

        
        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
        
        ((ImageView) this.findViewById(R.id.divine)).setOnTouchListener(gestureListener);
        
        Toast.makeText(this, "swipe your finger up or down to display interpretation", 60).show();
    }


	private void showInfo(int cardForTheDay, boolean reverse) {
		int i = cardForTheDay;
		String interpretation = BotaInt.getGeneralInterpretation(i,reverse,getApplicationContext());
		showing = inflater.inflate(R.layout.interpretation, null);
		TextView infotext = (TextView) showing.findViewById(R.id.interpretation);		
		infotext.setText(Html.fromHtml(interpretation));
		/*closure = (Button) showing.findViewById(R.id.closeinterpretation);
		closure.setClickable(true);

		closure.setOnClickListener(this);*/
		secondlayout = (RelativeLayout) this.findViewById(R.id.secondsetlayout);
		ArrayList<View> toShow = new ArrayList<View>();
		toShow.add(showing);
		secondlayout.addView(showing);
	}
	protected void setFullscreen() { 
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
    }  
	
	public void onClick(View v) {	
		if (v.equals(closure))
			redisplay();
		
	}
	protected void redisplay() {
		secondlayout.removeView(showing);	
	}
	
	class MyGestureDetector extends SimpleOnGestureListener {
	    @Override
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	        //try {	            
	            
	            if (Math.abs(e1.getX() - e2.getX()) < SWIPE_MAX_OFF_PATH) {
	            	// vertical swipe
	            	
	            	if (secondlayout.findViewById(R.id.interpretation) == null)
	            		showInfo(BotaInt.getCardForTheDayIndex(getApplicationContext(),seed),false);
					else
						redisplay();
		            return true;		         
	            }
	        //} catch (Exception e) {
	           // System.out.println(e.getMessage());
	        //}
	        return false;
	    }

	}
}