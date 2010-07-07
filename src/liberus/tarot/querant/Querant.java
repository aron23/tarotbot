package liberus.tarot.querant;

import java.util.Calendar;
import java.util.GregorianCalendar;

import liberus.tarot.deck.RiderWaiteDeck;
import liberus.tarot.interpretation.BotaInt;
import liberus.utils.*;
public class Querant {
	public boolean male=false;
	public boolean partnered=false;
	public boolean youth=false;
	public char element;
	public GregorianCalendar birth;
	public Querant(boolean male, boolean partnered, GregorianCalendar birth) {
		this.male=male;
		this.partnered=partnered;
		this.birth = birth;
		Calendar calendar2 = GregorianCalendar.getInstance();
		youth = (DateUtils.daysDifferent(birth, calendar2) < 4745);
		
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
		BotaInt myBota = new BotaInt(new RiderWaiteDeck(), new Querant(true,true,new GregorianCalendar(1976,10,4)));
		myBota.findMeaning();
		
	}
	public int getElement() {
		return DateUtils.getElement(birth);
	}
	public void setBirthDate(GregorianCalendar gregorianCalendar) {
		birth = gregorianCalendar;
		youth = (DateUtils.daysDifferent(birth, GregorianCalendar.getInstance()) < 4745);// TODO Auto-generated method stub		
	}
}
