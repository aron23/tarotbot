package liberus.tarot.querant;

import java.util.Calendar;
import java.util.GregorianCalendar;

import liberus.tarot.deck.FullTarotDeck;
import liberus.tarot.interpretation.BotaInt;
import liberus.utils.*;
public class Querant {
	public boolean male=false;
	public boolean partnered=false;
	public boolean youth=false;
	public int element;
	public GregorianCalendar birth;
	private boolean loaded = false;
	public Querant(boolean male, boolean partnered, GregorianCalendar birth) {
		this.male=male;
		this.partnered=partnered;
		this.birth = birth;
		Calendar calendar2 = GregorianCalendar.getInstance();
		youth = (DateUtils.daysDifferent(birth, calendar2) < 4745);
	}
	
	public Querant(int carded) {
		if (carded == 35 || carded == 49 || carded == 63 || carded == 77) {
			male=true;
			partnered=true;
		} else if (carded == 34 || carded == 48 || carded == 62 || carded == 76) {
			male=false;
			partnered=true;
		} else if (carded == 33 || carded == 47 || carded == 61 || carded == 75) {
			male=true;
			partnered=false;
		} else if (carded == 32 || carded == 46 || carded == 60 || carded == 74) {
			male=false;
			partnered=false;
		}
		
		if (carded == 35 || carded == 34 || carded == 33 || carded == 32) {
			element=0;
		} else if (carded == 49 || carded == 48 || carded == 47 || carded == 46) {
			element=1;
		} else if (carded == 63 || carded == 62 || carded == 61 || carded == 60) {
			element=2;
		} else if (carded ==77  || carded == 76 || carded == 75 || carded == 74) {
			element=3;
		}
		birth=(GregorianCalendar) GregorianCalendar.getInstance();
		youth=false;	
		loaded = true;
	}
	public boolean isMale() {
		return male;
	}
	public void setMale(boolean male) {
		this.male = male;
	}
	public boolean isPartnered() {
		return partnered;
	}
	public void setPartnered(boolean partnered) {
		this.partnered = partnered;
	}
	public boolean isYouth() {
		return youth;
	}
	public void setYouth(boolean youth) {
		this.youth = youth;
	}
	public static void main(String[] args) {
		BotaInt myBota = new BotaInt(new FullTarotDeck(), new Querant(true,true,new GregorianCalendar(1976,10,4)));
		//myBota.findMeaning();
		
	}
	public int getElement() {
		if (loaded)
			return element;
		return DateUtils.getElement(birth);
	}
	public void setBirthDate(GregorianCalendar gregorianCalendar) {
		birth = gregorianCalendar;
		youth = (DateUtils.daysDifferent(birth, GregorianCalendar.getInstance()) < 4745);// TODO Auto-generated method stub		
	}
}
