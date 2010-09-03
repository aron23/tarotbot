package liberus.utils;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateUtils {
	public static int daysDifferent (Calendar calendar1, Calendar calendar2){    
	    long milliseconds1 = calendar1.getTimeInMillis();
	    long milliseconds2 = calendar2.getTimeInMillis();
	    long diff = (milliseconds2 - milliseconds1);
	    int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
	    return diffDays;
	}
	public static long hoursDifferent (Calendar calendar1, Calendar calendar2){    
	    long milliseconds1 = calendar1.getTimeInMillis();
	    long milliseconds2 = calendar2.getTimeInMillis();
	    long diff = milliseconds2 - milliseconds1;
	    long diffHours = diff / (60 * 60 * 1000);
	    return diffHours;
	}
	public static String[] signs = new String[12];
	public static String[] element = new String[4];
	static {
		element[0]="fire";
		element[1]="earth";
		element[2]="air";
		element[3]="water";
		
		signs[0]="aries";
		signs[1]="taurus";
		signs[2]="gemini";
		signs[3]="cancer";
		signs[4]="leo";
		signs[5]="virgo";
		signs[6]="libra";
		signs[7]="scorpio";
		signs[8]="sagittarius";
		signs[9]="capricorn";
		signs[10]="aquarius";
		signs[11]="pisces";
	}
	public static int getElement(Calendar date) {
		if (getTropicalZodiac(date) == 0 ||
				getTropicalZodiac(date) == 4 ||
				getTropicalZodiac(date) == 8)
			return 1;
		if (getTropicalZodiac(date) == 1 ||
				getTropicalZodiac(date) == 5 ||
				getTropicalZodiac(date) == 9)
			return 2;
		if (getTropicalZodiac(date) == 2 ||
				getTropicalZodiac(date) == 6 ||
				getTropicalZodiac(date) == 10)
			return 3;
		if (getTropicalZodiac(date) == 3 ||
				getTropicalZodiac(date) == 7 ||
				getTropicalZodiac(date) == 11)
			return 0;
		return -1;
	}
	public static int getTropicalZodiac(Calendar date) {
		if (date.get(Calendar.MONTH) == 2 && date.get(Calendar.DAY_OF_MONTH) <= 20 ||
				date.get(Calendar.MONTH) == 1 && date.get(Calendar.DAY_OF_MONTH) > 18)
			return 10;
		if (date.get(Calendar.MONTH) == 3 && date.get(Calendar.DAY_OF_MONTH) <= 20 ||
				date.get(Calendar.MONTH) == 2 && date.get(Calendar.DAY_OF_MONTH) > 20)
			return 11;
		if (date.get(Calendar.MONTH) == 4 && date.get(Calendar.DAY_OF_MONTH) <= 21 ||
				date.get(Calendar.MONTH) == 3 && date.get(Calendar.DAY_OF_MONTH) > 20)
			return 0;
		if (date.get(Calendar.MONTH) == 5 && date.get(Calendar.DAY_OF_MONTH) <= 21 ||
				date.get(Calendar.MONTH) == 4 && date.get(Calendar.DAY_OF_MONTH) > 21)
			return 1;
		if (date.get(Calendar.MONTH) == 6 && date.get(Calendar.DAY_OF_MONTH) <= 22 ||
				date.get(Calendar.MONTH) == 5 && date.get(Calendar.DAY_OF_MONTH) > 21)
			return 2;
		if (date.get(Calendar.MONTH) == 7 && date.get(Calendar.DAY_OF_MONTH) <= 23 ||
				date.get(Calendar.MONTH) == 6 && date.get(Calendar.DAY_OF_MONTH) > 22)
			return 3;
		if (date.get(Calendar.MONTH) == 8 && date.get(Calendar.DAY_OF_MONTH) <= 23 ||
				date.get(Calendar.MONTH) == 7 && date.get(Calendar.DAY_OF_MONTH) > 23)
			return 4;
		if (date.get(Calendar.MONTH) == 9 && date.get(Calendar.DAY_OF_MONTH) <= 23 ||
				date.get(Calendar.MONTH) == 8 && date.get(Calendar.DAY_OF_MONTH) > 23)
			return 5;
		if (date.get(Calendar.MONTH) == 10 && date.get(Calendar.DAY_OF_MONTH) <= 22 ||
				date.get(Calendar.MONTH) == 9 && date.get(Calendar.DAY_OF_MONTH) > 23)
			return 6;
		if (date.get(Calendar.MONTH) == 11 && date.get(Calendar.DAY_OF_MONTH) <= 22 ||
				date.get(Calendar.MONTH) == 10 && date.get(Calendar.DAY_OF_MONTH) > 22)
			return 7;
		if (date.get(Calendar.MONTH) == 0 && date.get(Calendar.DAY_OF_MONTH) <= 20 ||
				date.get(Calendar.MONTH) == 11 && date.get(Calendar.DAY_OF_MONTH) > 22)
			return 8;
		if (date.get(Calendar.MONTH) == 1 && date.get(Calendar.DAY_OF_MONTH) <= 18 ||
				date.get(Calendar.MONTH) == 0 && date.get(Calendar.DAY_OF_MONTH) > 20)
			return 9;
		return -1;
	}
}
