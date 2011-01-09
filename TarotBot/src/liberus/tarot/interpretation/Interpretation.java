package liberus.tarot.interpretation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;

import liberus.tarot.deck.Deck;
import liberus.tarot.android.noads.R;
import liberus.tarot.querant.Querant;


public abstract class Interpretation {
	protected static Integer[] cards = new Integer[78];
	public static Querant myQuerant;
	public static Pattern suitpat = Pattern.compile("(Wands|Cups|Swords|Pentacles)");
	public static Deck myDeck;
	
	public Interpretation(Deck deck) {
		myDeck = deck;
	}
		
	public void setQuerant(Querant q) {
		myQuerant = q;
	}
	public Querant getQuerant() {
		return myQuerant;
	}
	
	public static String getGeneralInterpretation(int i, boolean reverse, Context context) {
		String returner = "<br/>";
		
		if (getTitle(i) > 0 && context.getString(getTitle(i)).length() > 0)
			returner += "<big><b>"+context.getString(getTitle(i))+"</b></big><br/>";
		if (getJourney(i) > 0 && context.getString(getJourney(i)).length() > 0)
			returner += "<i>"+context.getString(getJourney(i))+"</i><br/><br/>";
		if (getAstrology(i) > 0 && context.getString(getAstrology(i)).length() > 0)
			returner += "&nbsp;&nbsp;<i>"+context.getString(R.string.astrology_label)+":</i> "+context.getString(getAstrology(i))+"<br/>";
		if (getArchetype(i) > 0 && context.getString(getArchetype(i)).length() > 0)
			returner += "&nbsp;&nbsp;<i>"+context.getString(R.string.archetype_label)+":</i> "+context.getString(getArchetype(i))+"<br/>";
		if (getKeyword(i) > 0 && context.getString(getKeyword(i)).length() > 0)
			returner += "&nbsp;&nbsp;<i>"+context.getString(R.string.keyword_label)+":</i> "+context.getString(getKeyword(i))+"<br/>";
		if (getElement(i) > 0 && context.getString(getElement(i)).length() > 0)
			returner += "&nbsp;&nbsp;<i>"+context.getString(R.string.element_label)+":</i> "+context.getString(getElement(i))+"<br/>";
		returner += "<br/>";
		
		if (context.getString(getOccultTitle(i)).length() > 0)
			returner += "<b>"+context.getString(getOccultTitle(i))+"</b><br/>";
		if (context.getString(getAbst(i)).length() > 0)
			returner += context.getString(getAbst(i))+"<br/><br/>";
		if (context.getString(getMeanings(i)).length() > 0)
			returner += context.getString(getMeanings(i))+"<br/><br/>";
		
		return returner;
	}
	
	public static int getCardThumb(int index) {
		switch (index) {
			case 0:return R.drawable.trumps_01_th;
			case 1:return R.drawable.trumps_02_th;
			case 2:return R.drawable.trumps_03_th;
			case 3:return R.drawable.trumps_04_th;
			case 4:return R.drawable.trumps_05_th;
			case 5:return R.drawable.trumps_06_th;
			case 6:return R.drawable.trumps_07_th;
			case 7:return R.drawable.trumps_08_th;
			case 8:return R.drawable.trumps_09_th;
			case 9:return R.drawable.trumps_10_th;
			case 10:return R.drawable.trumps_11_th;
			case 11:return R.drawable.trumps_12_th;
			case 12:return R.drawable.trumps_13_th;
			case 13:return R.drawable.trumps_14_th;
			case 14:return R.drawable.trumps_15_th;
			case 15:return R.drawable.trumps_16_th;
			case 16:return R.drawable.trumps_17_th;
			case 17:return R.drawable.trumps_18_th;
			case 18:return R.drawable.trumps_19_th;		
			case 19:return R.drawable.trumps_20_th;
			case 20:return R.drawable.trumps_21_th;
			case 21:return R.drawable.trumps_22_th;
	
			case 22:return R.drawable.wands_01_th;
			case 23:return R.drawable.wands_02_th;
			case 24:return R.drawable.wands_03_th;
			case 25:return R.drawable.wands_04_th;
			case 26:return R.drawable.wands_05_th;
			case 27:return R.drawable.wands_06_th;
			case 28:return R.drawable.wands_07_th;
			case 29:return R.drawable.wands_08_th;
			case 30:return R.drawable.wands_09_th;
			case 31:return R.drawable.wands_10_th;
			case 32:return R.drawable.wands_page_th;
			case 33:return R.drawable.wands_knight_th;
			case 34:return R.drawable.wands_queen_th;
			case 35:return R.drawable.wands_king_th;
	
			case 36:return R.drawable.cups_01_th;
			case 37:return R.drawable.cups_02_th;
			case 38:return R.drawable.cups_03_th;
			case 39:return R.drawable.cups_04_th;
			case 40:return R.drawable.cups_05_th;
			case 41:return R.drawable.cups_06_th;
			case 42:return R.drawable.cups_07_th;
			case 43:return R.drawable.cups_08_th;
			case 44:return R.drawable.cups_09_th;
			case 45:return R.drawable.cups_10_th;
			case 46:return R.drawable.cups_page_th;
			case 47:return R.drawable.cups_knight_th;
			case 48:return R.drawable.cups_queen_th;
			case 49:return R.drawable.cups_king_th;
	
			case 50:return R.drawable.swords_01_th;
			case 51:return R.drawable.swords_02_th;
			case 52:return R.drawable.swords_03_th;
			case 53:return R.drawable.swords_04_th;
			case 54:return R.drawable.swords_05_th;
			case 55:return R.drawable.swords_06_th;
			case 56:return R.drawable.swords_07_th;
			case 57:return R.drawable.swords_08_th;
			case 58:return R.drawable.swords_09_th;
			case 59:return R.drawable.swords_10_th;
			case 60:return R.drawable.swords_page_th;
			case 61:return R.drawable.swords_knight_th;
			case 62:return R.drawable.swords_queen_th;
			case 63:return R.drawable.swords_king_th;
	
			case 64:return R.drawable.pent_01_th;
			case 65:return R.drawable.pent_02_th;
			case 66:return R.drawable.pent_03_th;
			case 67:return R.drawable.pent_04_th;
			case 68:return R.drawable.pent_05_th;
			case 69:return R.drawable.pent_06_th;
			case 70:return R.drawable.pent_07_th;
			case 71:return R.drawable.pent_08_th;
			case 72:return R.drawable.pent_09_th;
			case 73:return R.drawable.pent_10_th;
			case 74:return R.drawable.pent_page_th;
			case 75:return R.drawable.pent_knight_th;
			case 76:return R.drawable.pent_queen_th;
			case 77:return R.drawable.pent_king_th;				
		}
		return 0;
	}
	
	public static int getCard(int index) {
		switch (index) {
			case 0:return R.drawable.trumps_01;
			case 1:return R.drawable.trumps_02;
			case 2:return R.drawable.trumps_03;
			case 3:return R.drawable.trumps_04;
			case 4:return R.drawable.trumps_05;
			case 5:return R.drawable.trumps_06;
			case 6:return R.drawable.trumps_07;
			case 7:return R.drawable.trumps_08;
			case 8:return R.drawable.trumps_09;
			case 9:return R.drawable.trumps_10;
			case 10:return R.drawable.trumps_11;
			case 11:return R.drawable.trumps_12;
			case 12:return R.drawable.trumps_13;
			case 13:return R.drawable.trumps_14;
			case 14:return R.drawable.trumps_15;
			case 15:return R.drawable.trumps_16;
			case 16:return R.drawable.trumps_17;
			case 17:return R.drawable.trumps_18;
			case 18:return R.drawable.trumps_19;		
			case 19:return R.drawable.trumps_20;
			case 20:return R.drawable.trumps_21;
			case 21:return R.drawable.trumps_22;
	
			case 22:return R.drawable.wands_01;
			case 23:return R.drawable.wands_02;
			case 24:return R.drawable.wands_03;
			case 25:return R.drawable.wands_04;
			case 26:return R.drawable.wands_05;
			case 27:return R.drawable.wands_06;
			case 28:return R.drawable.wands_07;
			case 29:return R.drawable.wands_08;
			case 30:return R.drawable.wands_09;
			case 31:return R.drawable.wands_10;
			case 32:return R.drawable.wands_page;
			case 33:return R.drawable.wands_knight;
			case 34:return R.drawable.wands_queen;
			case 35:return R.drawable.wands_king;
	
			case 36:return R.drawable.cups_01;
			case 37:return R.drawable.cups_02;
			case 38:return R.drawable.cups_03;
			case 39:return R.drawable.cups_04;
			case 40:return R.drawable.cups_05;
			case 41:return R.drawable.cups_06;
			case 42:return R.drawable.cups_07;
			case 43:return R.drawable.cups_08;
			case 44:return R.drawable.cups_09;
			case 45:return R.drawable.cups_10;
			case 46:return R.drawable.cups_page;
			case 47:return R.drawable.cups_knight;
			case 48:return R.drawable.cups_queen;
			case 49:return R.drawable.cups_king;
	
			case 50:return R.drawable.swords_01;
			case 51:return R.drawable.swords_02;
			case 52:return R.drawable.swords_03;
			case 53:return R.drawable.swords_04;
			case 54:return R.drawable.swords_05;
			case 55:return R.drawable.swords_06;
			case 56:return R.drawable.swords_07;
			case 57:return R.drawable.swords_08;
			case 58:return R.drawable.swords_09;
			case 59:return R.drawable.swords_10;
			case 60:return R.drawable.swords_page;
			case 61:return R.drawable.swords_knight;
			case 62:return R.drawable.swords_queen;
			case 63:return R.drawable.swords_king;
	
			case 64:return R.drawable.pent_01;
			case 65:return R.drawable.pent_02;
			case 66:return R.drawable.pent_03;
			case 67:return R.drawable.pent_04;
			case 68:return R.drawable.pent_05;
			case 69:return R.drawable.pent_06;
			case 70:return R.drawable.pent_07;
			case 71:return R.drawable.pent_08;
			case 72:return R.drawable.pent_09;
			case 73:return R.drawable.pent_10;
			case 74:return R.drawable.pent_page;
			case 75:return R.drawable.pent_knight;
			case 76:return R.drawable.pent_queen;
			case 77:return R.drawable.pent_king;				
		}
		return 0;
	}
	
	public static String getCardName(int index) {
		switch (index) {
			case 0:return "trumps_01.jpg";
			case 1:return "trumps_02.jpg";
			case 2:return "trumps_03.jpg";
			case 3:return "trumps_04.jpg";
			case 4:return "trumps_05.jpg";
			case 5:return "trumps_06.jpg";
			case 6:return "trumps_07.jpg";
			case 7:return "trumps_08.jpg";
			case 8:return "trumps_09.jpg";
			case 9:return "trumps_10.jpg";
			case 10:return "trumps_11.jpg";
			case 11:return "trumps_12.jpg";
			case 12:return "trumps_13.jpg";
			case 13:return "trumps_14.jpg";
			case 14:return "trumps_15.jpg";
			case 15:return "trumps_16.jpg";
			case 16:return "trumps_17.jpg";
			case 17:return "trumps_18.jpg";
			case 18:return "trumps_19.jpg";		
			case 19:return "trumps_20.jpg";
			case 20:return "trumps_21.jpg";
			case 21:return "trumps_22.jpg";
	
			case 22:return "wands_01.jpg";
			case 23:return "wands_02.jpg";
			case 24:return "wands_03.jpg";
			case 25:return "wands_04.jpg";
			case 26:return "wands_05.jpg";
			case 27:return "wands_06.jpg";
			case 28:return "wands_07.jpg";
			case 29:return "wands_08.jpg";
			case 30:return "wands_09.jpg";
			case 31:return "wands_10.jpg";
			case 32:return "wands_page.jpg";
			case 33:return "wands_knight.jpg";
			case 34:return "wands_queen.jpg";
			case 35:return "wands_king.jpg";
	
			case 36:return "cups_01.jpg";
			case 37:return "cups_02.jpg";
			case 38:return "cups_03.jpg";
			case 39:return "cups_04.jpg";
			case 40:return "cups_05.jpg";
			case 41:return "cups_06.jpg";
			case 42:return "cups_07.jpg";
			case 43:return "cups_08.jpg";
			case 44:return "cups_09.jpg";
			case 45:return "cups_10.jpg";
			case 46:return "cups_page.jpg";
			case 47:return "cups_knight.jpg";
			case 48:return "cups_queen.jpg";
			case 49:return "cups_king.jpg";
	
			case 50:return "swords_01.jpg";
			case 51:return "swords_02.jpg";
			case 52:return "swords_03.jpg";
			case 53:return "swords_04.jpg";
			case 54:return "swords_05.jpg";
			case 55:return "swords_06.jpg";
			case 56:return "swords_07.jpg";
			case 57:return "swords_08.jpg";
			case 58:return "swords_09.jpg";
			case 59:return "swords_10.jpg";
			case 60:return "swords_page.jpg";
			case 61:return "swords_knight.jpg";
			case 62:return "swords_queen.jpg";
			case 63:return "swords_king.jpg";
	
			case 64:return "pent_01.jpg";
			case 65:return "pent_02.jpg";
			case 66:return "pent_03.jpg";
			case 67:return "pent_04.jpg";
			case 68:return "pent_05.jpg";
			case 69:return "pent_06.jpg";
			case 70:return "pent_07.jpg";
			case 71:return "pent_08.jpg";
			case 72:return "pent_09.jpg";
			case 73:return "pent_10.jpg";
			case 74:return "pent_page.jpg";
			case 75:return "pent_knight.jpg";
			case 76:return "pent_queen.jpg";
			case 77:return "pent_king.jpg";				
		}
		return "";
	}
	
	public static int getCardIndex(int card) {
		switch (card) {
			case R.id.trumps_01:return 0;
			case R.id.trumps_02:return 1;
			case R.id.trumps_03:return 2;
			case R.id.trumps_04:return 3;
			case R.id.trumps_05:return 4;
			case R.id.trumps_06:return 5;
			case R.id.trumps_07:return 6;
			case R.id.trumps_08:return 7;
			case R.id.trumps_09:return 8;
			case R.id.trumps_10:return 9;
			case R.id.trumps_11:return 10;
			case R.id.trumps_12:return 11;
			case R.id.trumps_13:return 12;
			case R.id.trumps_14:return 13;
			case R.id.trumps_15:return 14;
			case R.id.trumps_16:return 15;
			case R.id.trumps_17:return 16;
			case R.id.trumps_18:return 17;
			case R.id.trumps_19:return 18;		
			case R.id.trumps_20:return 19;
			case R.id.trumps_21:return 20;
			case R.id.trumps_22:return 21;
	
			case R.id.wands_01:return 22;
			case R.id.wands_02:return 23;
			case R.id.wands_03:return 24;
			case R.id.wands_04:return 25;
			case R.id.wands_05:return 26;
			case R.id.wands_06:return 27;
			case R.id.wands_07:return 28;
			case R.id.wands_08:return 29;
			case R.id.wands_09:return 30;
			case R.id.wands_10:return 31;
			case R.id.wands_page:return 32;
			case R.id.wands_knight:return 33;
			case R.id.wands_queen:return 34;
			case R.id.wands_king:return 35;
	
			case R.id.cups_01:return 36;
			case R.id.cups_02:return 37;
			case R.id.cups_03:return 38;
			case R.id.cups_04:return 39;
			case R.id.cups_05:return 40;
			case R.id.cups_06:return 41;
			case R.id.cups_07:return 42;
			case R.id.cups_08:return 43;
			case R.id.cups_09:return 44;
			case R.id.cups_10:return 45;
			case R.id.cups_page:return 46;
			case R.id.cups_knight:return 47;
			case R.id.cups_queen:return 48;
			case R.id.cups_king:return 49;
	
			case R.id.swords_01:return 50;
			case R.id.swords_02:return 51;
			case R.id.swords_03:return 52;
			case R.id.swords_04:return 53;
			case R.id.swords_05:return 54;
			case R.id.swords_06:return 55;
			case R.id.swords_07:return 56;
			case R.id.swords_08:return 57;
			case R.id.swords_09:return 58;
			case R.id.swords_10:return 59;
			case R.id.swords_page:return 60;
			case R.id.swords_knight:return 61;
			case R.id.swords_queen:return 62;
			case R.id.swords_king:return 63;
	
			case R.id.pent_01:return 64;
			case R.id.pent_02:return 65;
			case R.id.pent_03:return 66;
			case R.id.pent_04:return 67;
			case R.id.pent_05:return 68;
			case R.id.pent_06:return 69;
			case R.id.pent_07:return 70;
			case R.id.pent_08:return 71;
			case R.id.pent_09:return 72;
			case R.id.pent_10:return 73;
			case R.id.pent_page:return 74;
			case R.id.pent_knight:return 75;
			case R.id.pent_queen:return 76;
			case R.id.pent_king:return 77;				
		}
		return -1;
	}
	
	public static int getCardNum(int index) {
		switch (index) {
			case 0:return R.integer.trumps_01_index;
			case 1:return R.integer.trumps_02_index;
			case 2:return R.integer.trumps_03_index;
			case 3:return R.integer.trumps_04_index;
			case 4:return R.integer.trumps_05_index;
			case 5:return R.integer.trumps_06_index;
			case 6:return R.integer.trumps_07_index;
			case 7:return R.integer.trumps_08_index;
			case 8:return R.integer.trumps_09_index;
			case 9:return R.integer.trumps_10_index;
			case 10:return R.integer.trumps_11_index;
			case 11:return R.integer.trumps_12_index;
			case 12:return R.integer.trumps_13_index;
			case 13:return R.integer.trumps_14_index;
			case 14:return R.integer.trumps_15_index;
			case 15:return R.integer.trumps_16_index;
			case 16:return R.integer.trumps_17_index;
			case 17:return R.integer.trumps_18_index;
			case 18:return R.integer.trumps_19_index;		
			case 19:return R.integer.trumps_20_index;
			case 20:return R.integer.trumps_21_index;
			case 21:return R.integer.trumps_22_index;
	
			case 22:return R.integer.wands_01_index;
			case 23:return R.integer.wands_02_index;
			case 24:return R.integer.wands_03_index;
			case 25:return R.integer.wands_04_index;
			case 26:return R.integer.wands_05_index;
			case 27:return R.integer.wands_06_index;
			case 28:return R.integer.wands_07_index;
			case 29:return R.integer.wands_08_index;
			case 30:return R.integer.wands_09_index;
			case 31:return R.integer.wands_10_index;
			case 32:return R.integer.wands_page_index;
			case 33:return R.integer.wands_knight_index;
			case 34:return R.integer.wands_queen_index;
			case 35:return R.integer.wands_king_index;
	
			case 36:return R.integer.cups_01_index;
			case 37:return R.integer.cups_02_index;
			case 38:return R.integer.cups_03_index;
			case 39:return R.integer.cups_04_index;
			case 40:return R.integer.cups_05_index;
			case 41:return R.integer.cups_06_index;
			case 42:return R.integer.cups_07_index;
			case 43:return R.integer.cups_08_index;
			case 44:return R.integer.cups_09_index;
			case 45:return R.integer.cups_10_index;
			case 46:return R.integer.cups_page_index;
			case 47:return R.integer.cups_knight_index;
			case 48:return R.integer.cups_queen_index;
			case 49:return R.integer.cups_king_index;
	
			case 50:return R.integer.swords_01_index;
			case 51:return R.integer.swords_02_index;
			case 52:return R.integer.swords_03_index;
			case 53:return R.integer.swords_04_index;
			case 54:return R.integer.swords_05_index;
			case 55:return R.integer.swords_06_index;
			case 56:return R.integer.swords_07_index;
			case 57:return R.integer.swords_08_index;
			case 58:return R.integer.swords_09_index;
			case 59:return R.integer.swords_10_index;
			case 60:return R.integer.swords_page_index;
			case 61:return R.integer.swords_knight_index;
			case 62:return R.integer.swords_queen_index;
			case 63:return R.integer.swords_king_index;
	
			case 64:return R.integer.pentacles_01_index;
			case 65:return R.integer.pentacles_02_index;
			case 66:return R.integer.pentacles_03_index;
			case 67:return R.integer.pentacles_04_index;
			case 68:return R.integer.pentacles_05_index;
			case 69:return R.integer.pentacles_06_index;
			case 70:return R.integer.pentacles_07_index;
			case 71:return R.integer.pentacles_08_index;
			case 72:return R.integer.pentacles_09_index;
			case 73:return R.integer.pentacles_10_index;
			case 74:return R.integer.pentacles_page_index;
			case 75:return R.integer.pentacles_knight_index;
			case 76:return R.integer.pentacles_queen_index;
			case 77:return R.integer.pentacles_king_index;				
		}
		return 0;
	}
	
	public static int getTitle(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_title;
			case 1:return R.string.trumps_02_title;
			case 2:return R.string.trumps_03_title;
			case 3:return R.string.trumps_04_title;
			case 4:return R.string.trumps_05_title;
			case 5:return R.string.trumps_06_title;
			case 6:return R.string.trumps_07_title;
			case 7:return R.string.trumps_08_title;
			case 8:return R.string.trumps_09_title;
			case 9:return R.string.trumps_10_title;
			case 10:return R.string.trumps_11_title;
			case 11:return R.string.trumps_12_title;
			case 12:return R.string.trumps_13_title;
			case 13:return R.string.trumps_14_title;
			case 14:return R.string.trumps_15_title;
			case 15:return R.string.trumps_16_title;
			case 16:return R.string.trumps_17_title;
			case 17:return R.string.trumps_18_title;
			case 18:return R.string.trumps_19_title;		
			case 19:return R.string.trumps_20_title;
			case 20:return R.string.trumps_21_title;
			case 21:return R.string.trumps_22_title;
	
			case 22:return R.string.wands_01_title;
			case 23:return R.string.wands_02_title;
			case 24:return R.string.wands_03_title;
			case 25:return R.string.wands_04_title;
			case 26:return R.string.wands_05_title;
			case 27:return R.string.wands_06_title;
			case 28:return R.string.wands_07_title;
			case 29:return R.string.wands_08_title;
			case 30:return R.string.wands_09_title;
			case 31:return R.string.wands_10_title;
			case 32:return R.string.wands_page_title;
			case 33:return R.string.wands_knight_title;
			case 34:return R.string.wands_queen_title;
			case 35:return R.string.wands_king_title;
	
			case 36:return R.string.cups_01_title;
			case 37:return R.string.cups_02_title;
			case 38:return R.string.cups_03_title;
			case 39:return R.string.cups_04_title;
			case 40:return R.string.cups_05_title;
			case 41:return R.string.cups_06_title;
			case 42:return R.string.cups_07_title;
			case 43:return R.string.cups_08_title;
			case 44:return R.string.cups_09_title;
			case 45:return R.string.cups_10_title;
			case 46:return R.string.cups_page_title;
			case 47:return R.string.cups_knight_title;
			case 48:return R.string.cups_queen_title;
			case 49:return R.string.cups_king_title;
	
			case 50:return R.string.swords_01_title;
			case 51:return R.string.swords_02_title;
			case 52:return R.string.swords_03_title;
			case 53:return R.string.swords_04_title;
			case 54:return R.string.swords_05_title;
			case 55:return R.string.swords_06_title;
			case 56:return R.string.swords_07_title;
			case 57:return R.string.swords_08_title;
			case 58:return R.string.swords_09_title;
			case 59:return R.string.swords_10_title;
			case 60:return R.string.swords_page_title;
			case 61:return R.string.swords_knight_title;
			case 62:return R.string.swords_queen_title;
			case 63:return R.string.swords_king_title;
	
			case 64:return R.string.pentacles_01_title;
			case 65:return R.string.pentacles_02_title;
			case 66:return R.string.pentacles_03_title;
			case 67:return R.string.pentacles_04_title;
			case 68:return R.string.pentacles_05_title;
			case 69:return R.string.pentacles_06_title;
			case 70:return R.string.pentacles_07_title;
			case 71:return R.string.pentacles_08_title;
			case 72:return R.string.pentacles_09_title;
			case 73:return R.string.pentacles_10_title;
			case 74:return R.string.pentacles_page_title;
			case 75:return R.string.pentacles_knight_title;
			case 76:return R.string.pentacles_queen_title;
			case 77:return R.string.pentacles_king_title;				
		}
		return 0;
	}
	
	public static int getEnTitle(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_en_title;
			case 1:return R.string.trumps_02_en_title;
			case 2:return R.string.trumps_03_en_title;
			case 3:return R.string.trumps_04_en_title;
			case 4:return R.string.trumps_05_en_title;
			case 5:return R.string.trumps_06_en_title;
			case 6:return R.string.trumps_07_en_title;
			case 7:return R.string.trumps_08_en_title;
			case 8:return R.string.trumps_09_en_title;
			case 9:return R.string.trumps_10_en_title;
			case 10:return R.string.trumps_11_en_title;
			case 11:return R.string.trumps_12_en_title;
			case 12:return R.string.trumps_13_en_title;
			case 13:return R.string.trumps_14_en_title;
			case 14:return R.string.trumps_15_en_title;
			case 15:return R.string.trumps_16_en_title;
			case 16:return R.string.trumps_17_en_title;
			case 17:return R.string.trumps_18_en_title;
			case 18:return R.string.trumps_19_en_title;		
			case 19:return R.string.trumps_20_en_title;
			case 20:return R.string.trumps_21_en_title;
			case 21:return R.string.trumps_22_en_title;
	
			case 22:return R.string.wands_01_en_title;
			case 23:return R.string.wands_02_en_title;
			case 24:return R.string.wands_03_en_title;
			case 25:return R.string.wands_04_en_title;
			case 26:return R.string.wands_05_en_title;
			case 27:return R.string.wands_06_en_title;
			case 28:return R.string.wands_07_en_title;
			case 29:return R.string.wands_08_en_title;
			case 30:return R.string.wands_09_en_title;
			case 31:return R.string.wands_10_en_title;
			case 32:return R.string.wands_page_en_title;
			case 33:return R.string.wands_knight_en_title;
			case 34:return R.string.wands_queen_en_title;
			case 35:return R.string.wands_king_en_title;
	
			case 36:return R.string.cups_01_en_title;
			case 37:return R.string.cups_02_en_title;
			case 38:return R.string.cups_03_en_title;
			case 39:return R.string.cups_04_en_title;
			case 40:return R.string.cups_05_en_title;
			case 41:return R.string.cups_06_en_title;
			case 42:return R.string.cups_07_en_title;
			case 43:return R.string.cups_08_en_title;
			case 44:return R.string.cups_09_en_title;
			case 45:return R.string.cups_10_en_title;
			case 46:return R.string.cups_page_en_title;
			case 47:return R.string.cups_knight_en_title;
			case 48:return R.string.cups_queen_en_title;
			case 49:return R.string.cups_king_en_title;
	
			case 50:return R.string.swords_01_en_title;
			case 51:return R.string.swords_02_en_title;
			case 52:return R.string.swords_03_en_title;
			case 53:return R.string.swords_04_en_title;
			case 54:return R.string.swords_05_en_title;
			case 55:return R.string.swords_06_en_title;
			case 56:return R.string.swords_07_en_title;
			case 57:return R.string.swords_08_en_title;
			case 58:return R.string.swords_09_en_title;
			case 59:return R.string.swords_10_en_title;
			case 60:return R.string.swords_page_en_title;
			case 61:return R.string.swords_knight_en_title;
			case 62:return R.string.swords_queen_en_title;
			case 63:return R.string.swords_king_en_title;
	
			case 64:return R.string.pentacles_01_en_title;
			case 65:return R.string.pentacles_02_en_title;
			case 66:return R.string.pentacles_03_en_title;
			case 67:return R.string.pentacles_04_en_title;
			case 68:return R.string.pentacles_05_en_title;
			case 69:return R.string.pentacles_06_en_title;
			case 70:return R.string.pentacles_07_en_title;
			case 71:return R.string.pentacles_08_en_title;
			case 72:return R.string.pentacles_09_en_title;
			case 73:return R.string.pentacles_10_en_title;
			case 74:return R.string.pentacles_page_en_title;
			case 75:return R.string.pentacles_knight_en_title;
			case 76:return R.string.pentacles_queen_en_title;
			case 77:return R.string.pentacles_king_en_title;				
		}
		return 0;
	}
	
	public static int getSecondOperationCount(int index) {
		switch (index) {
			case 0:return R.integer.trumps_01_second_operation_count;
			case 1:return R.integer.trumps_02_second_operation_count;
			case 2:return R.integer.trumps_03_second_operation_count;
			case 3:return R.integer.trumps_04_second_operation_count;
			case 4:return R.integer.trumps_05_second_operation_count;
			case 5:return R.integer.trumps_06_second_operation_count;
			case 6:return R.integer.trumps_07_second_operation_count;
			case 7:return R.integer.trumps_08_second_operation_count;
			case 8:return R.integer.trumps_09_second_operation_count;
			case 9:return R.integer.trumps_10_second_operation_count;
			case 10:return R.integer.trumps_11_second_operation_count;
			case 11:return R.integer.trumps_12_second_operation_count;
			case 12:return R.integer.trumps_13_second_operation_count;
			case 13:return R.integer.trumps_14_second_operation_count;
			case 14:return R.integer.trumps_15_second_operation_count;
			case 15:return R.integer.trumps_16_second_operation_count;
			case 16:return R.integer.trumps_17_second_operation_count;
			case 17:return R.integer.trumps_18_second_operation_count;
			case 18:return R.integer.trumps_19_second_operation_count;		
			case 19:return R.integer.trumps_20_second_operation_count;
			case 20:return R.integer.trumps_21_second_operation_count;
			case 21:return R.integer.trumps_22_second_operation_count;
	
			case 22:return R.integer.wands_01_second_operation_count;
			case 23:return R.integer.wands_02_second_operation_count;
			case 24:return R.integer.wands_03_second_operation_count;
			case 25:return R.integer.wands_04_second_operation_count;
			case 26:return R.integer.wands_05_second_operation_count;
			case 27:return R.integer.wands_06_second_operation_count;
			case 28:return R.integer.wands_07_second_operation_count;
			case 29:return R.integer.wands_08_second_operation_count;
			case 30:return R.integer.wands_09_second_operation_count;
			case 31:return R.integer.wands_10_second_operation_count;
			case 32:return R.integer.wands_page_second_operation_count;
			case 33:return R.integer.wands_knight_second_operation_count;
			case 34:return R.integer.wands_queen_second_operation_count;
			case 35:return R.integer.wands_king_second_operation_count;
	
			case 36:return R.integer.cups_01_second_operation_count;
			case 37:return R.integer.cups_02_second_operation_count;
			case 38:return R.integer.cups_03_second_operation_count;
			case 39:return R.integer.cups_04_second_operation_count;
			case 40:return R.integer.cups_05_second_operation_count;
			case 41:return R.integer.cups_06_second_operation_count;
			case 42:return R.integer.cups_07_second_operation_count;
			case 43:return R.integer.cups_08_second_operation_count;
			case 44:return R.integer.cups_09_second_operation_count;
			case 45:return R.integer.cups_10_second_operation_count;
			case 46:return R.integer.cups_page_second_operation_count;
			case 47:return R.integer.cups_knight_second_operation_count;
			case 48:return R.integer.cups_queen_second_operation_count;
			case 49:return R.integer.cups_king_second_operation_count;
	
			case 50:return R.integer.swords_01_second_operation_count;
			case 51:return R.integer.swords_02_second_operation_count;
			case 52:return R.integer.swords_03_second_operation_count;
			case 53:return R.integer.swords_04_second_operation_count;
			case 54:return R.integer.swords_05_second_operation_count;
			case 55:return R.integer.swords_06_second_operation_count;
			case 56:return R.integer.swords_07_second_operation_count;
			case 57:return R.integer.swords_08_second_operation_count;
			case 58:return R.integer.swords_09_second_operation_count;
			case 59:return R.integer.swords_10_second_operation_count;
			case 60:return R.integer.swords_page_second_operation_count;
			case 61:return R.integer.swords_knight_second_operation_count;
			case 62:return R.integer.swords_queen_second_operation_count;
			case 63:return R.integer.swords_king_second_operation_count;
	
			case 64:return R.integer.pentacles_01_second_operation_count;
			case 65:return R.integer.pentacles_02_second_operation_count;
			case 66:return R.integer.pentacles_03_second_operation_count;
			case 67:return R.integer.pentacles_04_second_operation_count;
			case 68:return R.integer.pentacles_05_second_operation_count;
			case 69:return R.integer.pentacles_06_second_operation_count;
			case 70:return R.integer.pentacles_07_second_operation_count;
			case 71:return R.integer.pentacles_08_second_operation_count;
			case 72:return R.integer.pentacles_09_second_operation_count;
			case 73:return R.integer.pentacles_10_second_operation_count;
			case 74:return R.integer.pentacles_page_second_operation_count;
			case 75:return R.integer.pentacles_knight_second_operation_count;
			case 76:return R.integer.pentacles_queen_second_operation_count;
			case 77:return R.integer.pentacles_king_second_operation_count;				
		}
		return 0;
	}
	
	public static int getAstrology(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_astrology;
			case 1:return R.string.trumps_02_astrology;
			case 2:return R.string.trumps_03_astrology;
			case 3:return R.string.trumps_04_astrology;
			case 4:return R.string.trumps_05_astrology;
			case 5:return R.string.trumps_06_astrology;
			case 6:return R.string.trumps_07_astrology;
			case 7:return R.string.trumps_08_astrology;
			case 8:return R.string.trumps_09_astrology;
			case 9:return R.string.trumps_10_astrology;
			case 10:return R.string.trumps_11_astrology;
			case 11:return R.string.trumps_12_astrology;
			case 12:return R.string.trumps_13_astrology;
			case 13:return R.string.trumps_14_astrology;
			case 14:return R.string.trumps_15_astrology;
			case 15:return R.string.trumps_16_astrology;
			case 16:return R.string.trumps_17_astrology;
			case 17:return R.string.trumps_18_astrology;
			case 18:return R.string.trumps_19_astrology;		
			case 19:return R.string.trumps_20_astrology;
			case 20:return R.string.trumps_21_astrology;
			case 21:return R.string.trumps_22_astrology;
	
			case 22:return R.string.wands_01_astrology;
			case 23:return R.string.wands_02_astrology;
			case 24:return R.string.wands_03_astrology;
			case 25:return R.string.wands_04_astrology;
			case 26:return R.string.wands_05_astrology;
			case 27:return R.string.wands_06_astrology;
			case 28:return R.string.wands_07_astrology;
			case 29:return R.string.wands_08_astrology;
			case 30:return R.string.wands_09_astrology;
			case 31:return R.string.wands_10_astrology;
			case 32:return R.string.wands_page_astrology;
			case 33:return R.string.wands_knight_astrology;
			case 34:return R.string.wands_queen_astrology;
			case 35:return R.string.wands_king_astrology;
	
			case 36:return R.string.cups_01_astrology;
			case 37:return R.string.cups_02_astrology;
			case 38:return R.string.cups_03_astrology;
			case 39:return R.string.cups_04_astrology;
			case 40:return R.string.cups_05_astrology;
			case 41:return R.string.cups_06_astrology;
			case 42:return R.string.cups_07_astrology;
			case 43:return R.string.cups_08_astrology;
			case 44:return R.string.cups_09_astrology;
			case 45:return R.string.cups_10_astrology;
			case 46:return R.string.cups_page_astrology;
			case 47:return R.string.cups_knight_astrology;
			case 48:return R.string.cups_queen_astrology;
			case 49:return R.string.cups_king_astrology;
	
			case 50:return R.string.swords_01_astrology;
			case 51:return R.string.swords_02_astrology;
			case 52:return R.string.swords_03_astrology;
			case 53:return R.string.swords_04_astrology;
			case 54:return R.string.swords_05_astrology;
			case 55:return R.string.swords_06_astrology;
			case 56:return R.string.swords_07_astrology;
			case 57:return R.string.swords_08_astrology;
			case 58:return R.string.swords_09_astrology;
			case 59:return R.string.swords_10_astrology;
			case 60:return R.string.swords_page_astrology;
			case 61:return R.string.swords_knight_astrology;
			case 62:return R.string.swords_queen_astrology;
			case 63:return R.string.swords_king_astrology;
	
			case 64:return R.string.pentacles_01_astrology;
			case 65:return R.string.pentacles_02_astrology;
			case 66:return R.string.pentacles_03_astrology;
			case 67:return R.string.pentacles_04_astrology;
			case 68:return R.string.pentacles_05_astrology;
			case 69:return R.string.pentacles_06_astrology;
			case 70:return R.string.pentacles_07_astrology;
			case 71:return R.string.pentacles_08_astrology;
			case 72:return R.string.pentacles_09_astrology;
			case 73:return R.string.pentacles_10_astrology;
			case 74:return R.string.pentacles_page_astrology;
			case 75:return R.string.pentacles_knight_astrology;
			case 76:return R.string.pentacles_queen_astrology;
			case 77:return R.string.pentacles_king_astrology;				
		}
		return 0;
	}
	
	public static int getTimePeriod(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_time_period;
			case 1:return R.string.trumps_02_time_period;
			case 2:return R.string.trumps_03_time_period;
			case 3:return R.string.trumps_04_time_period;
			case 4:return R.string.trumps_05_time_period;
			case 5:return R.string.trumps_06_time_period;
			case 6:return R.string.trumps_07_time_period;
			case 7:return R.string.trumps_08_time_period;
			case 8:return R.string.trumps_09_time_period;
			case 9:return R.string.trumps_10_time_period;
			case 10:return R.string.trumps_11_time_period;
			case 11:return R.string.trumps_12_time_period;
			case 12:return R.string.trumps_13_time_period;
			case 13:return R.string.trumps_14_time_period;
			case 14:return R.string.trumps_15_time_period;
			case 15:return R.string.trumps_16_time_period;
			case 16:return R.string.trumps_17_time_period;
			case 17:return R.string.trumps_18_time_period;
			case 18:return R.string.trumps_19_time_period;		
			case 19:return R.string.trumps_20_time_period;
			case 20:return R.string.trumps_21_time_period;
			case 21:return R.string.trumps_22_time_period;
	
			case 22:return R.string.wands_01_time_period;
			case 23:return R.string.wands_02_time_period;
			case 24:return R.string.wands_03_time_period;
			case 25:return R.string.wands_04_time_period;
			case 26:return R.string.wands_05_time_period;
			case 27:return R.string.wands_06_time_period;
			case 28:return R.string.wands_07_time_period;
			case 29:return R.string.wands_08_time_period;
			case 30:return R.string.wands_09_time_period;
			case 31:return R.string.wands_10_time_period;
			case 32:return R.string.wands_page_time_period;
			case 33:return R.string.wands_knight_time_period;
			case 34:return R.string.wands_queen_time_period;
			case 35:return R.string.wands_king_time_period;
	
			case 36:return R.string.cups_01_time_period;
			case 37:return R.string.cups_02_time_period;
			case 38:return R.string.cups_03_time_period;
			case 39:return R.string.cups_04_time_period;
			case 40:return R.string.cups_05_time_period;
			case 41:return R.string.cups_06_time_period;
			case 42:return R.string.cups_07_time_period;
			case 43:return R.string.cups_08_time_period;
			case 44:return R.string.cups_09_time_period;
			case 45:return R.string.cups_10_time_period;
			case 46:return R.string.cups_page_time_period;
			case 47:return R.string.cups_knight_time_period;
			case 48:return R.string.cups_queen_time_period;
			case 49:return R.string.cups_king_time_period;
	
			case 50:return R.string.swords_01_time_period;
			case 51:return R.string.swords_02_time_period;
			case 52:return R.string.swords_03_time_period;
			case 53:return R.string.swords_04_time_period;
			case 54:return R.string.swords_05_time_period;
			case 55:return R.string.swords_06_time_period;
			case 56:return R.string.swords_07_time_period;
			case 57:return R.string.swords_08_time_period;
			case 58:return R.string.swords_09_time_period;
			case 59:return R.string.swords_10_time_period;
			case 60:return R.string.swords_page_time_period;
			case 61:return R.string.swords_knight_time_period;
			case 62:return R.string.swords_queen_time_period;
			case 63:return R.string.swords_king_time_period;
	
			case 64:return R.string.pentacles_01_time_period;
			case 65:return R.string.pentacles_02_time_period;
			case 66:return R.string.pentacles_03_time_period;
			case 67:return R.string.pentacles_04_time_period;
			case 68:return R.string.pentacles_05_time_period;
			case 69:return R.string.pentacles_06_time_period;
			case 70:return R.string.pentacles_07_time_period;
			case 71:return R.string.pentacles_08_time_period;
			case 72:return R.string.pentacles_09_time_period;
			case 73:return R.string.pentacles_10_time_period;
			case 74:return R.string.pentacles_page_time_period;
			case 75:return R.string.pentacles_knight_time_period;
			case 76:return R.string.pentacles_queen_time_period;
			case 77:return R.string.pentacles_king_time_period;				
		}
		return 0;
	}
	
	public static int getArchetype(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_archetype;
			case 1:return R.string.trumps_02_archetype;
			case 2:return R.string.trumps_03_archetype;
			case 3:return R.string.trumps_04_archetype;
			case 4:return R.string.trumps_05_archetype;
			case 5:return R.string.trumps_06_archetype;
			case 6:return R.string.trumps_07_archetype;
			case 7:return R.string.trumps_08_archetype;
			case 8:return R.string.trumps_09_archetype;
			case 9:return R.string.trumps_10_archetype;
			case 10:return R.string.trumps_11_archetype;
			case 11:return R.string.trumps_12_archetype;
			case 12:return R.string.trumps_13_archetype;
			case 13:return R.string.trumps_14_archetype;
			case 14:return R.string.trumps_15_archetype;
			case 15:return R.string.trumps_16_archetype;
			case 16:return R.string.trumps_17_archetype;
			case 17:return R.string.trumps_18_archetype;
			case 18:return R.string.trumps_19_archetype;		
			case 19:return R.string.trumps_20_archetype;
			case 20:return R.string.trumps_21_archetype;
			case 21:return R.string.trumps_22_archetype;
	
			case 22:return R.string.wands_01_archetype;
			case 23:return R.string.wands_02_archetype;
			case 24:return R.string.wands_03_archetype;
			case 25:return R.string.wands_04_archetype;
			case 26:return R.string.wands_05_archetype;
			case 27:return R.string.wands_06_archetype;
			case 28:return R.string.wands_07_archetype;
			case 29:return R.string.wands_08_archetype;
			case 30:return R.string.wands_09_archetype;
			case 31:return R.string.wands_10_archetype;
			case 32:return R.string.wands_page_archetype;
			case 33:return R.string.wands_knight_archetype;
			case 34:return R.string.wands_queen_archetype;
			case 35:return R.string.wands_king_archetype;
	
			case 36:return R.string.cups_01_archetype;
			case 37:return R.string.cups_02_archetype;
			case 38:return R.string.cups_03_archetype;
			case 39:return R.string.cups_04_archetype;
			case 40:return R.string.cups_05_archetype;
			case 41:return R.string.cups_06_archetype;
			case 42:return R.string.cups_07_archetype;
			case 43:return R.string.cups_08_archetype;
			case 44:return R.string.cups_09_archetype;
			case 45:return R.string.cups_10_archetype;
			case 46:return R.string.cups_page_archetype;
			case 47:return R.string.cups_knight_archetype;
			case 48:return R.string.cups_queen_archetype;
			case 49:return R.string.cups_king_archetype;
	
			case 50:return R.string.swords_01_archetype;
			case 51:return R.string.swords_02_archetype;
			case 52:return R.string.swords_03_archetype;
			case 53:return R.string.swords_04_archetype;
			case 54:return R.string.swords_05_archetype;
			case 55:return R.string.swords_06_archetype;
			case 56:return R.string.swords_07_archetype;
			case 57:return R.string.swords_08_archetype;
			case 58:return R.string.swords_09_archetype;
			case 59:return R.string.swords_10_archetype;
			case 60:return R.string.swords_page_archetype;
			case 61:return R.string.swords_knight_archetype;
			case 62:return R.string.swords_queen_archetype;
			case 63:return R.string.swords_king_archetype;
	
			case 64:return R.string.pentacles_01_archetype;
			case 65:return R.string.pentacles_02_archetype;
			case 66:return R.string.pentacles_03_archetype;
			case 67:return R.string.pentacles_04_archetype;
			case 68:return R.string.pentacles_05_archetype;
			case 69:return R.string.pentacles_06_archetype;
			case 70:return R.string.pentacles_07_archetype;
			case 71:return R.string.pentacles_08_archetype;
			case 72:return R.string.pentacles_09_archetype;
			case 73:return R.string.pentacles_10_archetype;
			case 74:return R.string.pentacles_page_archetype;
			case 75:return R.string.pentacles_knight_archetype;
			case 76:return R.string.pentacles_queen_archetype;
			case 77:return R.string.pentacles_king_archetype;				
		}
		return 0;
	}
	
	public static int getJourney(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_journey;
			case 1:return R.string.trumps_02_journey;
			case 2:return R.string.trumps_03_journey;
			case 3:return R.string.trumps_04_journey;
			case 4:return R.string.trumps_05_journey;
			case 5:return R.string.trumps_06_journey;
			case 6:return R.string.trumps_07_journey;
			case 7:return R.string.trumps_08_journey;
			case 8:return R.string.trumps_09_journey;
			case 9:return R.string.trumps_10_journey;
			case 10:return R.string.trumps_11_journey;
			case 11:return R.string.trumps_12_journey;
			case 12:return R.string.trumps_13_journey;
			case 13:return R.string.trumps_14_journey;
			case 14:return R.string.trumps_15_journey;
			case 15:return R.string.trumps_16_journey;
			case 16:return R.string.trumps_17_journey;
			case 17:return R.string.trumps_18_journey;
			case 18:return R.string.trumps_19_journey;		
			case 19:return R.string.trumps_20_journey;
			case 20:return R.string.trumps_21_journey;
			case 21:return R.string.trumps_22_journey;
	
			case 22:return R.string.wands_01_journey;
			case 23:return R.string.wands_02_journey;
			case 24:return R.string.wands_03_journey;
			case 25:return R.string.wands_04_journey;
			case 26:return R.string.wands_05_journey;
			case 27:return R.string.wands_06_journey;
			case 28:return R.string.wands_07_journey;
			case 29:return R.string.wands_08_journey;
			case 30:return R.string.wands_09_journey;
			case 31:return R.string.wands_10_journey;
			case 32:return R.string.wands_page_journey;
			case 33:return R.string.wands_knight_journey;
			case 34:return R.string.wands_queen_journey;
			case 35:return R.string.wands_king_journey;
	
			case 36:return R.string.cups_01_journey;
			case 37:return R.string.cups_02_journey;
			case 38:return R.string.cups_03_journey;
			case 39:return R.string.cups_04_journey;
			case 40:return R.string.cups_05_journey;
			case 41:return R.string.cups_06_journey;
			case 42:return R.string.cups_07_journey;
			case 43:return R.string.cups_08_journey;
			case 44:return R.string.cups_09_journey;
			case 45:return R.string.cups_10_journey;
			case 46:return R.string.cups_page_journey;
			case 47:return R.string.cups_knight_journey;
			case 48:return R.string.cups_queen_journey;
			case 49:return R.string.cups_king_journey;
	
			case 50:return R.string.swords_01_journey;
			case 51:return R.string.swords_02_journey;
			case 52:return R.string.swords_03_journey;
			case 53:return R.string.swords_04_journey;
			case 54:return R.string.swords_05_journey;
			case 55:return R.string.swords_06_journey;
			case 56:return R.string.swords_07_journey;
			case 57:return R.string.swords_08_journey;
			case 58:return R.string.swords_09_journey;
			case 59:return R.string.swords_10_journey;
			case 60:return R.string.swords_page_journey;
			case 61:return R.string.swords_knight_journey;
			case 62:return R.string.swords_queen_journey;
			case 63:return R.string.swords_king_journey;
	
			case 64:return R.string.pentacles_01_journey;
			case 65:return R.string.pentacles_02_journey;
			case 66:return R.string.pentacles_03_journey;
			case 67:return R.string.pentacles_04_journey;
			case 68:return R.string.pentacles_05_journey;
			case 69:return R.string.pentacles_06_journey;
			case 70:return R.string.pentacles_07_journey;
			case 71:return R.string.pentacles_08_journey;
			case 72:return R.string.pentacles_09_journey;
			case 73:return R.string.pentacles_10_journey;
			case 74:return R.string.pentacles_page_journey;
			case 75:return R.string.pentacles_knight_journey;
			case 76:return R.string.pentacles_queen_journey;
			case 77:return R.string.pentacles_king_journey;				
		}
		return 0;
	}
	
	public static int getElement(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_element;
			case 1:return R.string.trumps_02_element;
			case 2:return R.string.trumps_03_element;
			case 3:return R.string.trumps_04_element;
			case 4:return R.string.trumps_05_element;
			case 5:return R.string.trumps_06_element;
			case 6:return R.string.trumps_07_element;
			case 7:return R.string.trumps_08_element;
			case 8:return R.string.trumps_09_element;
			case 9:return R.string.trumps_10_element;
			case 10:return R.string.trumps_11_element;
			case 11:return R.string.trumps_12_element;
			case 12:return R.string.trumps_13_element;
			case 13:return R.string.trumps_14_element;
			case 14:return R.string.trumps_15_element;
			case 15:return R.string.trumps_16_element;
			case 16:return R.string.trumps_17_element;
			case 17:return R.string.trumps_18_element;
			case 18:return R.string.trumps_19_element;		
			case 19:return R.string.trumps_20_element;
			case 20:return R.string.trumps_21_element;
			case 21:return R.string.trumps_22_element;
	
			case 22:return R.string.wands_01_element;
			case 23:return R.string.wands_02_element;
			case 24:return R.string.wands_03_element;
			case 25:return R.string.wands_04_element;
			case 26:return R.string.wands_05_element;
			case 27:return R.string.wands_06_element;
			case 28:return R.string.wands_07_element;
			case 29:return R.string.wands_08_element;
			case 30:return R.string.wands_09_element;
			case 31:return R.string.wands_10_element;
			case 32:return R.string.wands_page_element;
			case 33:return R.string.wands_knight_element;
			case 34:return R.string.wands_queen_element;
			case 35:return R.string.wands_king_element;
	
			case 36:return R.string.cups_01_element;
			case 37:return R.string.cups_02_element;
			case 38:return R.string.cups_03_element;
			case 39:return R.string.cups_04_element;
			case 40:return R.string.cups_05_element;
			case 41:return R.string.cups_06_element;
			case 42:return R.string.cups_07_element;
			case 43:return R.string.cups_08_element;
			case 44:return R.string.cups_09_element;
			case 45:return R.string.cups_10_element;
			case 46:return R.string.cups_page_element;
			case 47:return R.string.cups_knight_element;
			case 48:return R.string.cups_queen_element;
			case 49:return R.string.cups_king_element;
	
			case 50:return R.string.swords_01_element;
			case 51:return R.string.swords_02_element;
			case 52:return R.string.swords_03_element;
			case 53:return R.string.swords_04_element;
			case 54:return R.string.swords_05_element;
			case 55:return R.string.swords_06_element;
			case 56:return R.string.swords_07_element;
			case 57:return R.string.swords_08_element;
			case 58:return R.string.swords_09_element;
			case 59:return R.string.swords_10_element;
			case 60:return R.string.swords_page_element;
			case 61:return R.string.swords_knight_element;
			case 62:return R.string.swords_queen_element;
			case 63:return R.string.swords_king_element;
	
			case 64:return R.string.pentacles_01_element;
			case 65:return R.string.pentacles_02_element;
			case 66:return R.string.pentacles_03_element;
			case 67:return R.string.pentacles_04_element;
			case 68:return R.string.pentacles_05_element;
			case 69:return R.string.pentacles_06_element;
			case 70:return R.string.pentacles_07_element;
			case 71:return R.string.pentacles_08_element;
			case 72:return R.string.pentacles_09_element;
			case 73:return R.string.pentacles_10_element;
			case 74:return R.string.pentacles_page_element;
			case 75:return R.string.pentacles_knight_element;
			case 76:return R.string.pentacles_queen_element;
			case 77:return R.string.pentacles_king_element;				
		}
		return 0;
	}
	
	public static int getHebrew(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_hebrew;
			case 1:return R.string.trumps_02_hebrew;
			case 2:return R.string.trumps_03_hebrew;
			case 3:return R.string.trumps_04_hebrew;
			case 4:return R.string.trumps_05_hebrew;
			case 5:return R.string.trumps_06_hebrew;
			case 6:return R.string.trumps_07_hebrew;
			case 7:return R.string.trumps_08_hebrew;
			case 8:return R.string.trumps_09_hebrew;
			case 9:return R.string.trumps_10_hebrew;
			case 10:return R.string.trumps_11_hebrew;
			case 11:return R.string.trumps_12_hebrew;
			case 12:return R.string.trumps_13_hebrew;
			case 13:return R.string.trumps_14_hebrew;
			case 14:return R.string.trumps_15_hebrew;
			case 15:return R.string.trumps_16_hebrew;
			case 16:return R.string.trumps_17_hebrew;
			case 17:return R.string.trumps_18_hebrew;
			case 18:return R.string.trumps_19_hebrew;		
			case 19:return R.string.trumps_20_hebrew;
			case 20:return R.string.trumps_21_hebrew;
			case 21:return R.string.trumps_22_hebrew;
	
			case 22:return R.string.wands_01_hebrew;
			case 23:return R.string.wands_02_hebrew;
			case 24:return R.string.wands_03_hebrew;
			case 25:return R.string.wands_04_hebrew;
			case 26:return R.string.wands_05_hebrew;
			case 27:return R.string.wands_06_hebrew;
			case 28:return R.string.wands_07_hebrew;
			case 29:return R.string.wands_08_hebrew;
			case 30:return R.string.wands_09_hebrew;
			case 31:return R.string.wands_10_hebrew;
			case 32:return R.string.wands_page_hebrew;
			case 33:return R.string.wands_knight_hebrew;
			case 34:return R.string.wands_queen_hebrew;
			case 35:return R.string.wands_king_hebrew;
	
			case 36:return R.string.cups_01_hebrew;
			case 37:return R.string.cups_02_hebrew;
			case 38:return R.string.cups_03_hebrew;
			case 39:return R.string.cups_04_hebrew;
			case 40:return R.string.cups_05_hebrew;
			case 41:return R.string.cups_06_hebrew;
			case 42:return R.string.cups_07_hebrew;
			case 43:return R.string.cups_08_hebrew;
			case 44:return R.string.cups_09_hebrew;
			case 45:return R.string.cups_10_hebrew;
			case 46:return R.string.cups_page_hebrew;
			case 47:return R.string.cups_knight_hebrew;
			case 48:return R.string.cups_queen_hebrew;
			case 49:return R.string.cups_king_hebrew;
	
			case 50:return R.string.swords_01_hebrew;
			case 51:return R.string.swords_02_hebrew;
			case 52:return R.string.swords_03_hebrew;
			case 53:return R.string.swords_04_hebrew;
			case 54:return R.string.swords_05_hebrew;
			case 55:return R.string.swords_06_hebrew;
			case 56:return R.string.swords_07_hebrew;
			case 57:return R.string.swords_08_hebrew;
			case 58:return R.string.swords_09_hebrew;
			case 59:return R.string.swords_10_hebrew;
			case 60:return R.string.swords_page_hebrew;
			case 61:return R.string.swords_knight_hebrew;
			case 62:return R.string.swords_queen_hebrew;
			case 63:return R.string.swords_king_hebrew;
	
			case 64:return R.string.pentacles_01_hebrew;
			case 65:return R.string.pentacles_02_hebrew;
			case 66:return R.string.pentacles_03_hebrew;
			case 67:return R.string.pentacles_04_hebrew;
			case 68:return R.string.pentacles_05_hebrew;
			case 69:return R.string.pentacles_06_hebrew;
			case 70:return R.string.pentacles_07_hebrew;
			case 71:return R.string.pentacles_08_hebrew;
			case 72:return R.string.pentacles_09_hebrew;
			case 73:return R.string.pentacles_10_hebrew;
			case 74:return R.string.pentacles_page_hebrew;
			case 75:return R.string.pentacles_knight_hebrew;
			case 76:return R.string.pentacles_queen_hebrew;
			case 77:return R.string.pentacles_king_hebrew;				
		}
		return 0;
	}
	
	public static int getOccultTitle(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_occult_title;
			case 1:return R.string.trumps_02_occult_title;
			case 2:return R.string.trumps_03_occult_title;
			case 3:return R.string.trumps_04_occult_title;
			case 4:return R.string.trumps_05_occult_title;
			case 5:return R.string.trumps_06_occult_title;
			case 6:return R.string.trumps_07_occult_title;
			case 7:return R.string.trumps_08_occult_title;
			case 8:return R.string.trumps_09_occult_title;
			case 9:return R.string.trumps_10_occult_title;
			case 10:return R.string.trumps_11_occult_title;
			case 11:return R.string.trumps_12_occult_title;
			case 12:return R.string.trumps_13_occult_title;
			case 13:return R.string.trumps_14_occult_title;
			case 14:return R.string.trumps_15_occult_title;
			case 15:return R.string.trumps_16_occult_title;
			case 16:return R.string.trumps_17_occult_title;
			case 17:return R.string.trumps_18_occult_title;
			case 18:return R.string.trumps_19_occult_title;		
			case 19:return R.string.trumps_20_occult_title;
			case 20:return R.string.trumps_21_occult_title;
			case 21:return R.string.trumps_22_occult_title;
	
			case 22:return R.string.wands_01_occult_title;
			case 23:return R.string.wands_02_occult_title;
			case 24:return R.string.wands_03_occult_title;
			case 25:return R.string.wands_04_occult_title;
			case 26:return R.string.wands_05_occult_title;
			case 27:return R.string.wands_06_occult_title;
			case 28:return R.string.wands_07_occult_title;
			case 29:return R.string.wands_08_occult_title;
			case 30:return R.string.wands_09_occult_title;
			case 31:return R.string.wands_10_occult_title;
			case 32:return R.string.wands_page_occult_title;
			case 33:return R.string.wands_knight_occult_title;
			case 34:return R.string.wands_queen_occult_title;
			case 35:return R.string.wands_king_occult_title;
	
			case 36:return R.string.cups_01_occult_title;
			case 37:return R.string.cups_02_occult_title;
			case 38:return R.string.cups_03_occult_title;
			case 39:return R.string.cups_04_occult_title;
			case 40:return R.string.cups_05_occult_title;
			case 41:return R.string.cups_06_occult_title;
			case 42:return R.string.cups_07_occult_title;
			case 43:return R.string.cups_08_occult_title;
			case 44:return R.string.cups_09_occult_title;
			case 45:return R.string.cups_10_occult_title;
			case 46:return R.string.cups_page_occult_title;
			case 47:return R.string.cups_knight_occult_title;
			case 48:return R.string.cups_queen_occult_title;
			case 49:return R.string.cups_king_occult_title;
	
			case 50:return R.string.swords_01_occult_title;
			case 51:return R.string.swords_02_occult_title;
			case 52:return R.string.swords_03_occult_title;
			case 53:return R.string.swords_04_occult_title;
			case 54:return R.string.swords_05_occult_title;
			case 55:return R.string.swords_06_occult_title;
			case 56:return R.string.swords_07_occult_title;
			case 57:return R.string.swords_08_occult_title;
			case 58:return R.string.swords_09_occult_title;
			case 59:return R.string.swords_10_occult_title;
			case 60:return R.string.swords_page_occult_title;
			case 61:return R.string.swords_knight_occult_title;
			case 62:return R.string.swords_queen_occult_title;
			case 63:return R.string.swords_king_occult_title;
	
			case 64:return R.string.pentacles_01_occult_title;
			case 65:return R.string.pentacles_02_occult_title;
			case 66:return R.string.pentacles_03_occult_title;
			case 67:return R.string.pentacles_04_occult_title;
			case 68:return R.string.pentacles_05_occult_title;
			case 69:return R.string.pentacles_06_occult_title;
			case 70:return R.string.pentacles_07_occult_title;
			case 71:return R.string.pentacles_08_occult_title;
			case 72:return R.string.pentacles_09_occult_title;
			case 73:return R.string.pentacles_10_occult_title;
			case 74:return R.string.pentacles_page_occult_title;
			case 75:return R.string.pentacles_knight_occult_title;
			case 76:return R.string.pentacles_queen_occult_title;
			case 77:return R.string.pentacles_king_occult_title;				
		}
		return 0;
	}
	
	public static int getKeyword(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_keyword;
			case 1:return R.string.trumps_02_keyword;
			case 2:return R.string.trumps_03_keyword;
			case 3:return R.string.trumps_04_keyword;
			case 4:return R.string.trumps_05_keyword;
			case 5:return R.string.trumps_06_keyword;
			case 6:return R.string.trumps_07_keyword;
			case 7:return R.string.trumps_08_keyword;
			case 8:return R.string.trumps_09_keyword;
			case 9:return R.string.trumps_10_keyword;
			case 10:return R.string.trumps_11_keyword;
			case 11:return R.string.trumps_12_keyword;
			case 12:return R.string.trumps_13_keyword;
			case 13:return R.string.trumps_14_keyword;
			case 14:return R.string.trumps_15_keyword;
			case 15:return R.string.trumps_16_keyword;
			case 16:return R.string.trumps_17_keyword;
			case 17:return R.string.trumps_18_keyword;
			case 18:return R.string.trumps_19_keyword;		
			case 19:return R.string.trumps_20_keyword;
			case 20:return R.string.trumps_21_keyword;
			case 21:return R.string.trumps_22_keyword;
	
			case 22:return R.string.wands_01_keyword;
			case 23:return R.string.wands_02_keyword;
			case 24:return R.string.wands_03_keyword;
			case 25:return R.string.wands_04_keyword;
			case 26:return R.string.wands_05_keyword;
			case 27:return R.string.wands_06_keyword;
			case 28:return R.string.wands_07_keyword;
			case 29:return R.string.wands_08_keyword;
			case 30:return R.string.wands_09_keyword;
			case 31:return R.string.wands_10_keyword;
			case 32:return R.string.wands_page_keyword;
			case 33:return R.string.wands_knight_keyword;
			case 34:return R.string.wands_queen_keyword;
			case 35:return R.string.wands_king_keyword;
	
			case 36:return R.string.cups_01_keyword;
			case 37:return R.string.cups_02_keyword;
			case 38:return R.string.cups_03_keyword;
			case 39:return R.string.cups_04_keyword;
			case 40:return R.string.cups_05_keyword;
			case 41:return R.string.cups_06_keyword;
			case 42:return R.string.cups_07_keyword;
			case 43:return R.string.cups_08_keyword;
			case 44:return R.string.cups_09_keyword;
			case 45:return R.string.cups_10_keyword;
			case 46:return R.string.cups_page_keyword;
			case 47:return R.string.cups_knight_keyword;
			case 48:return R.string.cups_queen_keyword;
			case 49:return R.string.cups_king_keyword;
	
			case 50:return R.string.swords_01_keyword;
			case 51:return R.string.swords_02_keyword;
			case 52:return R.string.swords_03_keyword;
			case 53:return R.string.swords_04_keyword;
			case 54:return R.string.swords_05_keyword;
			case 55:return R.string.swords_06_keyword;
			case 56:return R.string.swords_07_keyword;
			case 57:return R.string.swords_08_keyword;
			case 58:return R.string.swords_09_keyword;
			case 59:return R.string.swords_10_keyword;
			case 60:return R.string.swords_page_keyword;
			case 61:return R.string.swords_knight_keyword;
			case 62:return R.string.swords_queen_keyword;
			case 63:return R.string.swords_king_keyword;
	
			case 64:return R.string.pentacles_01_keyword;
			case 65:return R.string.pentacles_02_keyword;
			case 66:return R.string.pentacles_03_keyword;
			case 67:return R.string.pentacles_04_keyword;
			case 68:return R.string.pentacles_05_keyword;
			case 69:return R.string.pentacles_06_keyword;
			case 70:return R.string.pentacles_07_keyword;
			case 71:return R.string.pentacles_08_keyword;
			case 72:return R.string.pentacles_09_keyword;
			case 73:return R.string.pentacles_10_keyword;
			case 74:return R.string.pentacles_page_keyword;
			case 75:return R.string.pentacles_knight_keyword;
			case 76:return R.string.pentacles_queen_keyword;
			case 77:return R.string.pentacles_king_keyword;				
		}
		return 0;
	}
	
	public static int getAbst(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_abst;
			case 1:return R.string.trumps_02_abst;
			case 2:return R.string.trumps_03_abst;
			case 3:return R.string.trumps_04_abst;
			case 4:return R.string.trumps_05_abst;
			case 5:return R.string.trumps_06_abst;
			case 6:return R.string.trumps_07_abst;
			case 7:return R.string.trumps_08_abst;
			case 8:return R.string.trumps_09_abst;
			case 9:return R.string.trumps_10_abst;
			case 10:return R.string.trumps_11_abst;
			case 11:return R.string.trumps_12_abst;
			case 12:return R.string.trumps_13_abst;
			case 13:return R.string.trumps_14_abst;
			case 14:return R.string.trumps_15_abst;
			case 15:return R.string.trumps_16_abst;
			case 16:return R.string.trumps_17_abst;
			case 17:return R.string.trumps_18_abst;
			case 18:return R.string.trumps_19_abst;		
			case 19:return R.string.trumps_20_abst;
			case 20:return R.string.trumps_21_abst;
			case 21:return R.string.trumps_22_abst;
	
			case 22:return R.string.wands_01_abst;
			case 23:return R.string.wands_02_abst;
			case 24:return R.string.wands_03_abst;
			case 25:return R.string.wands_04_abst;
			case 26:return R.string.wands_05_abst;
			case 27:return R.string.wands_06_abst;
			case 28:return R.string.wands_07_abst;
			case 29:return R.string.wands_08_abst;
			case 30:return R.string.wands_09_abst;
			case 31:return R.string.wands_10_abst;
			case 32:return R.string.wands_page_abst;
			case 33:return R.string.wands_knight_abst;
			case 34:return R.string.wands_queen_abst;
			case 35:return R.string.wands_king_abst;
	
			case 36:return R.string.cups_01_abst;
			case 37:return R.string.cups_02_abst;
			case 38:return R.string.cups_03_abst;
			case 39:return R.string.cups_04_abst;
			case 40:return R.string.cups_05_abst;
			case 41:return R.string.cups_06_abst;
			case 42:return R.string.cups_07_abst;
			case 43:return R.string.cups_08_abst;
			case 44:return R.string.cups_09_abst;
			case 45:return R.string.cups_10_abst;
			case 46:return R.string.cups_page_abst;
			case 47:return R.string.cups_knight_abst;
			case 48:return R.string.cups_queen_abst;
			case 49:return R.string.cups_king_abst;
	
			case 50:return R.string.swords_01_abst;
			case 51:return R.string.swords_02_abst;
			case 52:return R.string.swords_03_abst;
			case 53:return R.string.swords_04_abst;
			case 54:return R.string.swords_05_abst;
			case 55:return R.string.swords_06_abst;
			case 56:return R.string.swords_07_abst;
			case 57:return R.string.swords_08_abst;
			case 58:return R.string.swords_09_abst;
			case 59:return R.string.swords_10_abst;
			case 60:return R.string.swords_page_abst;
			case 61:return R.string.swords_knight_abst;
			case 62:return R.string.swords_queen_abst;
			case 63:return R.string.swords_king_abst;
	
			case 64:return R.string.pentacles_01_abst;
			case 65:return R.string.pentacles_02_abst;
			case 66:return R.string.pentacles_03_abst;
			case 67:return R.string.pentacles_04_abst;
			case 68:return R.string.pentacles_05_abst;
			case 69:return R.string.pentacles_06_abst;
			case 70:return R.string.pentacles_07_abst;
			case 71:return R.string.pentacles_08_abst;
			case 72:return R.string.pentacles_09_abst;
			case 73:return R.string.pentacles_10_abst;
			case 74:return R.string.pentacles_page_abst;
			case 75:return R.string.pentacles_knight_abst;
			case 76:return R.string.pentacles_queen_abst;
			case 77:return R.string.pentacles_king_abst;				
		}
		return 0;
	}
	
	public static int getMeanings(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_meanings;
			case 1:return R.string.trumps_02_meanings;
			case 2:return R.string.trumps_03_meanings;
			case 3:return R.string.trumps_04_meanings;
			case 4:return R.string.trumps_05_meanings;
			case 5:return R.string.trumps_06_meanings;
			case 6:return R.string.trumps_07_meanings;
			case 7:return R.string.trumps_08_meanings;
			case 8:return R.string.trumps_09_meanings;
			case 9:return R.string.trumps_10_meanings;
			case 10:return R.string.trumps_11_meanings;
			case 11:return R.string.trumps_12_meanings;
			case 12:return R.string.trumps_13_meanings;
			case 13:return R.string.trumps_14_meanings;
			case 14:return R.string.trumps_15_meanings;
			case 15:return R.string.trumps_16_meanings;
			case 16:return R.string.trumps_17_meanings;
			case 17:return R.string.trumps_18_meanings;
			case 18:return R.string.trumps_19_meanings;		
			case 19:return R.string.trumps_20_meanings;
			case 20:return R.string.trumps_21_meanings;
			case 21:return R.string.trumps_22_meanings;
	
			case 22:return R.string.wands_01_meanings;
			case 23:return R.string.wands_02_meanings;
			case 24:return R.string.wands_03_meanings;
			case 25:return R.string.wands_04_meanings;
			case 26:return R.string.wands_05_meanings;
			case 27:return R.string.wands_06_meanings;
			case 28:return R.string.wands_07_meanings;
			case 29:return R.string.wands_08_meanings;
			case 30:return R.string.wands_09_meanings;
			case 31:return R.string.wands_10_meanings;
			case 32:return R.string.wands_page_meanings;
			case 33:return R.string.wands_knight_meanings;
			case 34:return R.string.wands_queen_meanings;
			case 35:return R.string.wands_king_meanings;
	
			case 36:return R.string.cups_01_meanings;
			case 37:return R.string.cups_02_meanings;
			case 38:return R.string.cups_03_meanings;
			case 39:return R.string.cups_04_meanings;
			case 40:return R.string.cups_05_meanings;
			case 41:return R.string.cups_06_meanings;
			case 42:return R.string.cups_07_meanings;
			case 43:return R.string.cups_08_meanings;
			case 44:return R.string.cups_09_meanings;
			case 45:return R.string.cups_10_meanings;
			case 46:return R.string.cups_page_meanings;
			case 47:return R.string.cups_knight_meanings;
			case 48:return R.string.cups_queen_meanings;
			case 49:return R.string.cups_king_meanings;
	
			case 50:return R.string.swords_01_meanings;
			case 51:return R.string.swords_02_meanings;
			case 52:return R.string.swords_03_meanings;
			case 53:return R.string.swords_04_meanings;
			case 54:return R.string.swords_05_meanings;
			case 55:return R.string.swords_06_meanings;
			case 56:return R.string.swords_07_meanings;
			case 57:return R.string.swords_08_meanings;
			case 58:return R.string.swords_09_meanings;
			case 59:return R.string.swords_10_meanings;
			case 60:return R.string.swords_page_meanings;
			case 61:return R.string.swords_knight_meanings;
			case 62:return R.string.swords_queen_meanings;
			case 63:return R.string.swords_king_meanings;
	
			case 64:return R.string.pentacles_01_meanings;
			case 65:return R.string.pentacles_02_meanings;
			case 66:return R.string.pentacles_03_meanings;
			case 67:return R.string.pentacles_04_meanings;
			case 68:return R.string.pentacles_05_meanings;
			case 69:return R.string.pentacles_06_meanings;
			case 70:return R.string.pentacles_07_meanings;
			case 71:return R.string.pentacles_08_meanings;
			case 72:return R.string.pentacles_09_meanings;
			case 73:return R.string.pentacles_10_meanings;
			case 74:return R.string.pentacles_page_meanings;
			case 75:return R.string.pentacles_knight_meanings;
			case 76:return R.string.pentacles_queen_meanings;
			case 77:return R.string.pentacles_king_meanings;				
		}
		return 0;
	}
	
	public static int getInSpiritualMatters(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_in_spiritual;
			case 1:return R.string.trumps_02_in_spiritual;
			case 2:return R.string.trumps_03_in_spiritual;
			case 3:return R.string.trumps_04_in_spiritual;
			case 4:return R.string.trumps_05_in_spiritual;
			case 5:return R.string.trumps_06_in_spiritual;
			case 6:return R.string.trumps_07_in_spiritual;
			case 7:return R.string.trumps_08_in_spiritual;
			case 8:return R.string.trumps_09_in_spiritual;
			case 9:return R.string.trumps_10_in_spiritual;
			case 10:return R.string.trumps_11_in_spiritual;
			case 11:return R.string.trumps_12_in_spiritual;
			case 12:return R.string.trumps_13_in_spiritual;
			case 13:return R.string.trumps_14_in_spiritual;
			case 14:return R.string.trumps_15_in_spiritual;
			case 15:return R.string.trumps_16_in_spiritual;
			case 16:return R.string.trumps_17_in_spiritual;
			case 17:return R.string.trumps_18_in_spiritual;
			case 18:return R.string.trumps_19_in_spiritual;		
			case 19:return R.string.trumps_20_in_spiritual;
			case 20:return R.string.trumps_21_in_spiritual;
			case 21:return R.string.trumps_22_in_spiritual;
	
			case 22:return R.string.wands_01_in_spiritual;
			case 23:return R.string.wands_02_in_spiritual;
			case 24:return R.string.wands_03_in_spiritual;
			case 25:return R.string.wands_04_in_spiritual;
			case 26:return R.string.wands_05_in_spiritual;
			case 27:return R.string.wands_06_in_spiritual;
			case 28:return R.string.wands_07_in_spiritual;
			case 29:return R.string.wands_08_in_spiritual;
			case 30:return R.string.wands_09_in_spiritual;
			case 31:return R.string.wands_10_in_spiritual;
			case 32:return R.string.wands_page_in_spiritual;
			case 33:return R.string.wands_knight_in_spiritual;
			case 34:return R.string.wands_queen_in_spiritual;
			case 35:return R.string.wands_king_in_spiritual;
	
			case 36:return R.string.cups_01_in_spiritual;
			case 37:return R.string.cups_02_in_spiritual;
			case 38:return R.string.cups_03_in_spiritual;
			case 39:return R.string.cups_04_in_spiritual;
			case 40:return R.string.cups_05_in_spiritual;
			case 41:return R.string.cups_06_in_spiritual;
			case 42:return R.string.cups_07_in_spiritual;
			case 43:return R.string.cups_08_in_spiritual;
			case 44:return R.string.cups_09_in_spiritual;
			case 45:return R.string.cups_10_in_spiritual;
			case 46:return R.string.cups_page_in_spiritual;
			case 47:return R.string.cups_knight_in_spiritual;
			case 48:return R.string.cups_queen_in_spiritual;
			case 49:return R.string.cups_king_in_spiritual;
	
			case 50:return R.string.swords_01_in_spiritual;
			case 51:return R.string.swords_02_in_spiritual;
			case 52:return R.string.swords_03_in_spiritual;
			case 53:return R.string.swords_04_in_spiritual;
			case 54:return R.string.swords_05_in_spiritual;
			case 55:return R.string.swords_06_in_spiritual;
			case 56:return R.string.swords_07_in_spiritual;
			case 57:return R.string.swords_08_in_spiritual;
			case 58:return R.string.swords_09_in_spiritual;
			case 59:return R.string.swords_10_in_spiritual;
			case 60:return R.string.swords_page_in_spiritual;
			case 61:return R.string.swords_knight_in_spiritual;
			case 62:return R.string.swords_queen_in_spiritual;
			case 63:return R.string.swords_king_in_spiritual;
	
			case 64:return R.string.pentacles_01_in_spiritual;
			case 65:return R.string.pentacles_02_in_spiritual;
			case 66:return R.string.pentacles_03_in_spiritual;
			case 67:return R.string.pentacles_04_in_spiritual;
			case 68:return R.string.pentacles_05_in_spiritual;
			case 69:return R.string.pentacles_06_in_spiritual;
			case 70:return R.string.pentacles_07_in_spiritual;
			case 71:return R.string.pentacles_08_in_spiritual;
			case 72:return R.string.pentacles_09_in_spiritual;
			case 73:return R.string.pentacles_10_in_spiritual;
			case 74:return R.string.pentacles_page_in_spiritual;
			case 75:return R.string.pentacles_knight_in_spiritual;
			case 76:return R.string.pentacles_queen_in_spiritual;
			case 77:return R.string.pentacles_king_in_spiritual;				
		}
		return 0;
	}
	
	public static int getInMaterialMatters(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_in_material;
			case 1:return R.string.trumps_02_in_material;
			case 2:return R.string.trumps_03_in_material;
			case 3:return R.string.trumps_04_in_material;
			case 4:return R.string.trumps_05_in_material;
			case 5:return R.string.trumps_06_in_material;
			case 6:return R.string.trumps_07_in_material;
			case 7:return R.string.trumps_08_in_material;
			case 8:return R.string.trumps_09_in_material;
			case 9:return R.string.trumps_10_in_material;
			case 10:return R.string.trumps_11_in_material;
			case 11:return R.string.trumps_12_in_material;
			case 12:return R.string.trumps_13_in_material;
			case 13:return R.string.trumps_14_in_material;
			case 14:return R.string.trumps_15_in_material;
			case 15:return R.string.trumps_16_in_material;
			case 16:return R.string.trumps_17_in_material;
			case 17:return R.string.trumps_18_in_material;
			case 18:return R.string.trumps_19_in_material;		
			case 19:return R.string.trumps_20_in_material;
			case 20:return R.string.trumps_21_in_material;
			case 21:return R.string.trumps_22_in_material;
	
			case 22:return R.string.wands_01_in_material;
			case 23:return R.string.wands_02_in_material;
			case 24:return R.string.wands_03_in_material;
			case 25:return R.string.wands_04_in_material;
			case 26:return R.string.wands_05_in_material;
			case 27:return R.string.wands_06_in_material;
			case 28:return R.string.wands_07_in_material;
			case 29:return R.string.wands_08_in_material;
			case 30:return R.string.wands_09_in_material;
			case 31:return R.string.wands_10_in_material;
			case 32:return R.string.wands_page_in_material;
			case 33:return R.string.wands_knight_in_material;
			case 34:return R.string.wands_queen_in_material;
			case 35:return R.string.wands_king_in_material;
	
			case 36:return R.string.cups_01_in_material;
			case 37:return R.string.cups_02_in_material;
			case 38:return R.string.cups_03_in_material;
			case 39:return R.string.cups_04_in_material;
			case 40:return R.string.cups_05_in_material;
			case 41:return R.string.cups_06_in_material;
			case 42:return R.string.cups_07_in_material;
			case 43:return R.string.cups_08_in_material;
			case 44:return R.string.cups_09_in_material;
			case 45:return R.string.cups_10_in_material;
			case 46:return R.string.cups_page_in_material;
			case 47:return R.string.cups_knight_in_material;
			case 48:return R.string.cups_queen_in_material;
			case 49:return R.string.cups_king_in_material;
	
			case 50:return R.string.swords_01_in_material;
			case 51:return R.string.swords_02_in_material;
			case 52:return R.string.swords_03_in_material;
			case 53:return R.string.swords_04_in_material;
			case 54:return R.string.swords_05_in_material;
			case 55:return R.string.swords_06_in_material;
			case 56:return R.string.swords_07_in_material;
			case 57:return R.string.swords_08_in_material;
			case 58:return R.string.swords_09_in_material;
			case 59:return R.string.swords_10_in_material;
			case 60:return R.string.swords_page_in_material;
			case 61:return R.string.swords_knight_in_material;
			case 62:return R.string.swords_queen_in_material;
			case 63:return R.string.swords_king_in_material;
	
			case 64:return R.string.pentacles_01_in_material;
			case 65:return R.string.pentacles_02_in_material;
			case 66:return R.string.pentacles_03_in_material;
			case 67:return R.string.pentacles_04_in_material;
			case 68:return R.string.pentacles_05_in_material;
			case 69:return R.string.pentacles_06_in_material;
			case 70:return R.string.pentacles_07_in_material;
			case 71:return R.string.pentacles_08_in_material;
			case 72:return R.string.pentacles_09_in_material;
			case 73:return R.string.pentacles_10_in_material;
			case 74:return R.string.pentacles_page_in_material;
			case 75:return R.string.pentacles_knight_in_material;
			case 76:return R.string.pentacles_queen_in_material;
			case 77:return R.string.pentacles_king_in_material;				
		}
		return 0;
	}
	
	public static int getWellDignified(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_well_dignified;
			case 1:return R.string.trumps_02_well_dignified;
			case 2:return R.string.trumps_03_well_dignified;
			case 3:return R.string.trumps_04_well_dignified;
			case 4:return R.string.trumps_05_well_dignified;
			case 5:return R.string.trumps_06_well_dignified;
			case 6:return R.string.trumps_07_well_dignified;
			case 7:return R.string.trumps_08_well_dignified;
			case 8:return R.string.trumps_09_well_dignified;
			case 9:return R.string.trumps_10_well_dignified;
			case 10:return R.string.trumps_11_well_dignified;
			case 11:return R.string.trumps_12_well_dignified;
			case 12:return R.string.trumps_13_well_dignified;
			case 13:return R.string.trumps_14_well_dignified;
			case 14:return R.string.trumps_15_well_dignified;
			case 15:return R.string.trumps_16_well_dignified;
			case 16:return R.string.trumps_17_well_dignified;
			case 17:return R.string.trumps_18_well_dignified;
			case 18:return R.string.trumps_19_well_dignified;		
			case 19:return R.string.trumps_20_well_dignified;
			case 20:return R.string.trumps_21_well_dignified;
			case 21:return R.string.trumps_22_well_dignified;
	
			case 22:return R.string.wands_01_well_dignified;
			case 23:return R.string.wands_02_well_dignified;
			case 24:return R.string.wands_03_well_dignified;
			case 25:return R.string.wands_04_well_dignified;
			case 26:return R.string.wands_05_well_dignified;
			case 27:return R.string.wands_06_well_dignified;
			case 28:return R.string.wands_07_well_dignified;
			case 29:return R.string.wands_08_well_dignified;
			case 30:return R.string.wands_09_well_dignified;
			case 31:return R.string.wands_10_well_dignified;
			case 32:return R.string.wands_page_well_dignified;
			case 33:return R.string.wands_knight_well_dignified;
			case 34:return R.string.wands_queen_well_dignified;
			case 35:return R.string.wands_king_well_dignified;
	
			case 36:return R.string.cups_01_well_dignified;
			case 37:return R.string.cups_02_well_dignified;
			case 38:return R.string.cups_03_well_dignified;
			case 39:return R.string.cups_04_well_dignified;
			case 40:return R.string.cups_05_well_dignified;
			case 41:return R.string.cups_06_well_dignified;
			case 42:return R.string.cups_07_well_dignified;
			case 43:return R.string.cups_08_well_dignified;
			case 44:return R.string.cups_09_well_dignified;
			case 45:return R.string.cups_10_well_dignified;
			case 46:return R.string.cups_page_well_dignified;
			case 47:return R.string.cups_knight_well_dignified;
			case 48:return R.string.cups_queen_well_dignified;
			case 49:return R.string.cups_king_well_dignified;
	
			case 50:return R.string.swords_01_well_dignified;
			case 51:return R.string.swords_02_well_dignified;
			case 52:return R.string.swords_03_well_dignified;
			case 53:return R.string.swords_04_well_dignified;
			case 54:return R.string.swords_05_well_dignified;
			case 55:return R.string.swords_06_well_dignified;
			case 56:return R.string.swords_07_well_dignified;
			case 57:return R.string.swords_08_well_dignified;
			case 58:return R.string.swords_09_well_dignified;
			case 59:return R.string.swords_10_well_dignified;
			case 60:return R.string.swords_page_well_dignified;
			case 61:return R.string.swords_knight_well_dignified;
			case 62:return R.string.swords_queen_well_dignified;
			case 63:return R.string.swords_king_well_dignified;
	
			case 64:return R.string.pentacles_01_well_dignified;
			case 65:return R.string.pentacles_02_well_dignified;
			case 66:return R.string.pentacles_03_well_dignified;
			case 67:return R.string.pentacles_04_well_dignified;
			case 68:return R.string.pentacles_05_well_dignified;
			case 69:return R.string.pentacles_06_well_dignified;
			case 70:return R.string.pentacles_07_well_dignified;
			case 71:return R.string.pentacles_08_well_dignified;
			case 72:return R.string.pentacles_09_well_dignified;
			case 73:return R.string.pentacles_10_well_dignified;
			case 74:return R.string.pentacles_page_well_dignified;
			case 75:return R.string.pentacles_knight_well_dignified;
			case 76:return R.string.pentacles_queen_well_dignified;
			case 77:return R.string.pentacles_king_well_dignified;				
		}
		return 0;
	}
	
	public static int getIllDignified(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_ill_dignified;
			case 1:return R.string.trumps_02_ill_dignified;
			case 2:return R.string.trumps_03_ill_dignified;
			case 3:return R.string.trumps_04_ill_dignified;
			case 4:return R.string.trumps_05_ill_dignified;
			case 5:return R.string.trumps_06_ill_dignified;
			case 6:return R.string.trumps_07_ill_dignified;
			case 7:return R.string.trumps_08_ill_dignified;
			case 8:return R.string.trumps_09_ill_dignified;
			case 9:return R.string.trumps_10_ill_dignified;
			case 10:return R.string.trumps_11_ill_dignified;
			case 11:return R.string.trumps_12_ill_dignified;
			case 12:return R.string.trumps_13_ill_dignified;
			case 13:return R.string.trumps_14_ill_dignified;
			case 14:return R.string.trumps_15_ill_dignified;
			case 15:return R.string.trumps_16_ill_dignified;
			case 16:return R.string.trumps_17_ill_dignified;
			case 17:return R.string.trumps_18_ill_dignified;
			case 18:return R.string.trumps_19_ill_dignified;		
			case 19:return R.string.trumps_20_ill_dignified;
			case 20:return R.string.trumps_21_ill_dignified;
			case 21:return R.string.trumps_22_ill_dignified;
	
			case 22:return R.string.wands_01_ill_dignified;
			case 23:return R.string.wands_02_ill_dignified;
			case 24:return R.string.wands_03_ill_dignified;
			case 25:return R.string.wands_04_ill_dignified;
			case 26:return R.string.wands_05_ill_dignified;
			case 27:return R.string.wands_06_ill_dignified;
			case 28:return R.string.wands_07_ill_dignified;
			case 29:return R.string.wands_08_ill_dignified;
			case 30:return R.string.wands_09_ill_dignified;
			case 31:return R.string.wands_10_ill_dignified;
			case 32:return R.string.wands_page_ill_dignified;
			case 33:return R.string.wands_knight_ill_dignified;
			case 34:return R.string.wands_queen_ill_dignified;
			case 35:return R.string.wands_king_ill_dignified;
	
			case 36:return R.string.cups_01_ill_dignified;
			case 37:return R.string.cups_02_ill_dignified;
			case 38:return R.string.cups_03_ill_dignified;
			case 39:return R.string.cups_04_ill_dignified;
			case 40:return R.string.cups_05_ill_dignified;
			case 41:return R.string.cups_06_ill_dignified;
			case 42:return R.string.cups_07_ill_dignified;
			case 43:return R.string.cups_08_ill_dignified;
			case 44:return R.string.cups_09_ill_dignified;
			case 45:return R.string.cups_10_ill_dignified;
			case 46:return R.string.cups_page_ill_dignified;
			case 47:return R.string.cups_knight_ill_dignified;
			case 48:return R.string.cups_queen_ill_dignified;
			case 49:return R.string.cups_king_ill_dignified;
	
			case 50:return R.string.swords_01_ill_dignified;
			case 51:return R.string.swords_02_ill_dignified;
			case 52:return R.string.swords_03_ill_dignified;
			case 53:return R.string.swords_04_ill_dignified;
			case 54:return R.string.swords_05_ill_dignified;
			case 55:return R.string.swords_06_ill_dignified;
			case 56:return R.string.swords_07_ill_dignified;
			case 57:return R.string.swords_08_ill_dignified;
			case 58:return R.string.swords_09_ill_dignified;
			case 59:return R.string.swords_10_ill_dignified;
			case 60:return R.string.swords_page_ill_dignified;
			case 61:return R.string.swords_knight_ill_dignified;
			case 62:return R.string.swords_queen_ill_dignified;
			case 63:return R.string.swords_king_ill_dignified;
	
			case 64:return R.string.pentacles_01_ill_dignified;
			case 65:return R.string.pentacles_02_ill_dignified;
			case 66:return R.string.pentacles_03_ill_dignified;
			case 67:return R.string.pentacles_04_ill_dignified;
			case 68:return R.string.pentacles_05_ill_dignified;
			case 69:return R.string.pentacles_06_ill_dignified;
			case 70:return R.string.pentacles_07_ill_dignified;
			case 71:return R.string.pentacles_08_ill_dignified;
			case 72:return R.string.pentacles_09_ill_dignified;
			case 73:return R.string.pentacles_10_ill_dignified;
			case 74:return R.string.pentacles_page_ill_dignified;
			case 75:return R.string.pentacles_knight_ill_dignified;
			case 76:return R.string.pentacles_queen_ill_dignified;
			case 77:return R.string.pentacles_king_ill_dignified;				
		}
		return 0;
	}
	
	public static int getReversed(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_reversed;
			case 1:return R.string.trumps_02_reversed;
			case 2:return R.string.trumps_03_reversed;
			case 3:return R.string.trumps_04_reversed;
			case 4:return R.string.trumps_05_reversed;
			case 5:return R.string.trumps_06_reversed;
			case 6:return R.string.trumps_07_reversed;
			case 7:return R.string.trumps_08_reversed;
			case 8:return R.string.trumps_09_reversed;
			case 9:return R.string.trumps_10_reversed;
			case 10:return R.string.trumps_11_reversed;
			case 11:return R.string.trumps_12_reversed;
			case 12:return R.string.trumps_13_reversed;
			case 13:return R.string.trumps_14_reversed;
			case 14:return R.string.trumps_15_reversed;
			case 15:return R.string.trumps_16_reversed;
			case 16:return R.string.trumps_17_reversed;
			case 17:return R.string.trumps_18_reversed;
			case 18:return R.string.trumps_19_reversed;		
			case 19:return R.string.trumps_20_reversed;
			case 20:return R.string.trumps_21_reversed;
			case 21:return R.string.trumps_22_reversed;
	
			case 22:return R.string.wands_01_reversed;
			case 23:return R.string.wands_02_reversed;
			case 24:return R.string.wands_03_reversed;
			case 25:return R.string.wands_04_reversed;
			case 26:return R.string.wands_05_reversed;
			case 27:return R.string.wands_06_reversed;
			case 28:return R.string.wands_07_reversed;
			case 29:return R.string.wands_08_reversed;
			case 30:return R.string.wands_09_reversed;
			case 31:return R.string.wands_10_reversed;
			case 32:return R.string.wands_page_reversed;
			case 33:return R.string.wands_knight_reversed;
			case 34:return R.string.wands_queen_reversed;
			case 35:return R.string.wands_king_reversed;
	
			case 36:return R.string.cups_01_reversed;
			case 37:return R.string.cups_02_reversed;
			case 38:return R.string.cups_03_reversed;
			case 39:return R.string.cups_04_reversed;
			case 40:return R.string.cups_05_reversed;
			case 41:return R.string.cups_06_reversed;
			case 42:return R.string.cups_07_reversed;
			case 43:return R.string.cups_08_reversed;
			case 44:return R.string.cups_09_reversed;
			case 45:return R.string.cups_10_reversed;
			case 46:return R.string.cups_page_reversed;
			case 47:return R.string.cups_knight_reversed;
			case 48:return R.string.cups_queen_reversed;
			case 49:return R.string.cups_king_reversed;
	
			case 50:return R.string.swords_01_reversed;
			case 51:return R.string.swords_02_reversed;
			case 52:return R.string.swords_03_reversed;
			case 53:return R.string.swords_04_reversed;
			case 54:return R.string.swords_05_reversed;
			case 55:return R.string.swords_06_reversed;
			case 56:return R.string.swords_07_reversed;
			case 57:return R.string.swords_08_reversed;
			case 58:return R.string.swords_09_reversed;
			case 59:return R.string.swords_10_reversed;
			case 60:return R.string.swords_page_reversed;
			case 61:return R.string.swords_knight_reversed;
			case 62:return R.string.swords_queen_reversed;
			case 63:return R.string.swords_king_reversed;
	
			case 64:return R.string.pentacles_01_reversed;
			case 65:return R.string.pentacles_02_reversed;
			case 66:return R.string.pentacles_03_reversed;
			case 67:return R.string.pentacles_04_reversed;
			case 68:return R.string.pentacles_05_reversed;
			case 69:return R.string.pentacles_06_reversed;
			case 70:return R.string.pentacles_07_reversed;
			case 71:return R.string.pentacles_08_reversed;
			case 72:return R.string.pentacles_09_reversed;
			case 73:return R.string.pentacles_10_reversed;
			case 74:return R.string.pentacles_page_reversed;
			case 75:return R.string.pentacles_knight_reversed;
			case 76:return R.string.pentacles_queen_reversed;
			case 77:return R.string.pentacles_king_reversed;				
		}
		return 0;
	}
	
	public static int getActions(int index) {
		switch (index) {
			case 0:return R.string.trumps_01_actions;
			case 1:return R.string.trumps_02_actions;
			case 2:return R.string.trumps_03_actions;
			case 3:return R.string.trumps_04_actions;
			case 4:return R.string.trumps_05_actions;
			case 5:return R.string.trumps_06_actions;
			case 6:return R.string.trumps_07_actions;
			case 7:return R.string.trumps_08_actions;
			case 8:return R.string.trumps_09_actions;
			case 9:return R.string.trumps_10_actions;
			case 10:return R.string.trumps_11_actions;
			case 11:return R.string.trumps_12_actions;
			case 12:return R.string.trumps_13_actions;
			case 13:return R.string.trumps_14_actions;
			case 14:return R.string.trumps_15_actions;
			case 15:return R.string.trumps_16_actions;
			case 16:return R.string.trumps_17_actions;
			case 17:return R.string.trumps_18_actions;
			case 18:return R.string.trumps_19_actions;		
			case 19:return R.string.trumps_20_actions;
			case 20:return R.string.trumps_21_actions;
			case 21:return R.string.trumps_22_actions;
	
			case 22:return R.string.wands_01_actions;
			case 23:return R.string.wands_02_actions;
			case 24:return R.string.wands_03_actions;
			case 25:return R.string.wands_04_actions;
			case 26:return R.string.wands_05_actions;
			case 27:return R.string.wands_06_actions;
			case 28:return R.string.wands_07_actions;
			case 29:return R.string.wands_08_actions;
			case 30:return R.string.wands_09_actions;
			case 31:return R.string.wands_10_actions;
			case 32:return R.string.wands_page_actions;
			case 33:return R.string.wands_knight_actions;
			case 34:return R.string.wands_queen_actions;
			case 35:return R.string.wands_king_actions;
	
			case 36:return R.string.cups_01_actions;
			case 37:return R.string.cups_02_actions;
			case 38:return R.string.cups_03_actions;
			case 39:return R.string.cups_04_actions;
			case 40:return R.string.cups_05_actions;
			case 41:return R.string.cups_06_actions;
			case 42:return R.string.cups_07_actions;
			case 43:return R.string.cups_08_actions;
			case 44:return R.string.cups_09_actions;
			case 45:return R.string.cups_10_actions;
			case 46:return R.string.cups_page_actions;
			case 47:return R.string.cups_knight_actions;
			case 48:return R.string.cups_queen_actions;
			case 49:return R.string.cups_king_actions;
	
			case 50:return R.string.swords_01_actions;
			case 51:return R.string.swords_02_actions;
			case 52:return R.string.swords_03_actions;
			case 53:return R.string.swords_04_actions;
			case 54:return R.string.swords_05_actions;
			case 55:return R.string.swords_06_actions;
			case 56:return R.string.swords_07_actions;
			case 57:return R.string.swords_08_actions;
			case 58:return R.string.swords_09_actions;
			case 59:return R.string.swords_10_actions;
			case 60:return R.string.swords_page_actions;
			case 61:return R.string.swords_knight_actions;
			case 62:return R.string.swords_queen_actions;
			case 63:return R.string.swords_king_actions;
	
			case 64:return R.string.pentacles_01_actions;
			case 65:return R.string.pentacles_02_actions;
			case 66:return R.string.pentacles_03_actions;
			case 67:return R.string.pentacles_04_actions;
			case 68:return R.string.pentacles_05_actions;
			case 69:return R.string.pentacles_06_actions;
			case 70:return R.string.pentacles_07_actions;
			case 71:return R.string.pentacles_08_actions;
			case 72:return R.string.pentacles_09_actions;
			case 73:return R.string.pentacles_10_actions;
			case 74:return R.string.pentacles_page_actions;
			case 75:return R.string.pentacles_knight_actions;
			case 76:return R.string.pentacles_queen_actions;
			case 77:return R.string.pentacles_king_actions;				
		}
		return 0;
	}

//	public static int getOppositionNumber(int index) {
//		switch (index) {
//			case 0:return R.array.trumps_01_opposition_number;
//			case 1:return R.array.trumps_02_opposition_number;
//			case 2:return R.array.trumps_03_opposition_number;
//			case 3:return R.array.trumps_04_opposition_number;
//			case 4:return R.array.trumps_05_opposition_number;
//			case 5:return R.array.trumps_06_opposition_number;
//			case 6:return R.array.trumps_07_opposition_number;
//			case 7:return R.array.trumps_08_opposition_number;
//			case 8:return R.array.trumps_09_opposition_number;
//			case 9:return R.array.trumps_10_opposition_number;
//			case 10:return R.array.trumps_11_opposition_number;
//			case 11:return R.array.trumps_12_opposition_number;
//			case 12:return R.array.trumps_13_opposition_number;
//			case 13:return R.array.trumps_14_opposition_number;
//			case 14:return R.array.trumps_15_opposition_number;
//			case 15:return R.array.trumps_16_opposition_number;
//			case 16:return R.array.trumps_17_opposition_number;
//			case 17:return R.array.trumps_18_opposition_number;
//			case 18:return R.array.trumps_19_opposition_number;		
//			case 19:return R.array.trumps_20_opposition_number;
//			case 20:return R.array.trumps_21_opposition_number;
//			case 21:return R.array.trumps_22_opposition_number;
//	
//			case 22:return 0;
//			case 23:return R.array.wands_02_opposition_number;
//			case 24:return R.array.wands_03_opposition_number;
//			case 25:return R.array.wands_04_opposition_number;
//			case 26:return R.array.wands_05_opposition_number;
//			case 27:return R.array.wands_06_opposition_number;
//			case 28:return R.array.wands_07_opposition_number;
//			case 29:return R.array.wands_08_opposition_number;
//			case 30:return R.array.wands_09_opposition_number;
//			case 31:return R.array.wands_10_opposition_number;
//			case 32:return 0;
//			case 33:return 0;
//			case 34:return 0;
//			case 35:return 0;
//	
//			case 36:return 0;
//			case 37:return R.array.cups_02_opposition_number;
//			case 38:return R.array.cups_03_opposition_number;
//			case 39:return R.array.cups_04_opposition_number;
//			case 40:return R.array.cups_05_opposition_number;
//			case 41:return R.array.cups_06_opposition_number;
//			case 42:return R.array.cups_07_opposition_number;
//			case 43:return R.array.cups_08_opposition_number;
//			case 44:return R.array.cups_09_opposition_number;
//			case 45:return R.array.cups_10_opposition_number;
//			case 46:return 0;
//			case 47:return 0;
//			case 48:return 0;
//			case 49:return 0;
//	
//			case 50:return 0;
//			case 51:return R.array.swords_02_opposition_number;
//			case 52:return R.array.swords_03_opposition_number;
//			case 53:return R.array.swords_04_opposition_number;
//			case 54:return R.array.swords_05_opposition_number;
//			case 55:return R.array.swords_06_opposition_number;
//			case 56:return R.array.swords_07_opposition_number;
//			case 57:return R.array.swords_08_opposition_number;
//			case 58:return R.array.swords_09_opposition_number;
//			case 59:return R.array.swords_10_opposition_number;
//			case 60:return 0;
//			case 61:return 0;
//			case 62:return 0;
//			case 63:return 0;
//	
//			case 64:return 0;
//			case 65:return R.array.pentacles_02_opposition_number;
//			case 66:return R.array.pentacles_03_opposition_number;
//			case 67:return R.array.pentacles_04_opposition_number;
//			case 68:return R.array.pentacles_05_opposition_number;
//			case 69:return 0;
//			case 70:return R.array.pentacles_07_opposition_number;
//			case 71:return R.array.pentacles_08_opposition_number;
//			case 72:return R.array.pentacles_09_opposition_number;
//			case 73:return R.array.pentacles_10_opposition_number;
//			case 74:return 0;
//			case 75:return 0;
//			case 76:return 0;
//			case 77:return 0;				
//		}
//		return 0;
//	}
//	
//	public static int getOppositionText(int index) {
//		switch (index) {
//			case 0:return R.array.trumps_01_opposition_text;
//			case 1:return R.array.trumps_02_opposition_text;
//			case 2:return R.array.trumps_03_opposition_text;
//			case 3:return R.array.trumps_04_opposition_text;
//			case 4:return R.array.trumps_05_opposition_text;
//			case 5:return R.array.trumps_06_opposition_text;
//			case 6:return R.array.trumps_07_opposition_text;
//			case 7:return R.array.trumps_08_opposition_text;
//			case 8:return R.array.trumps_09_opposition_text;
//			case 9:return R.array.trumps_10_opposition_text;
//			case 10:return R.array.trumps_11_opposition_text;
//			case 11:return R.array.trumps_12_opposition_text;
//			case 12:return R.array.trumps_13_opposition_text;
//			case 13:return R.array.trumps_14_opposition_text;
//			case 14:return R.array.trumps_15_opposition_text;
//			case 15:return R.array.trumps_16_opposition_text;
//			case 16:return R.array.trumps_17_opposition_text;
//			case 17:return R.array.trumps_18_opposition_text;
//			case 18:return R.array.trumps_19_opposition_text;		
//			case 19:return R.array.trumps_20_opposition_text;
//			case 20:return R.array.trumps_21_opposition_text;
//			case 21:return R.array.trumps_22_opposition_text;
//	
//			case 22:return 0;
//			case 23:return R.array.wands_02_opposition_text;
//			case 24:return R.array.wands_03_opposition_text;
//			case 25:return R.array.wands_04_opposition_text;
//			case 26:return R.array.wands_05_opposition_text;
//			case 27:return R.array.wands_06_opposition_text;
//			case 28:return R.array.wands_07_opposition_text;
//			case 29:return R.array.wands_08_opposition_text;
//			case 30:return R.array.wands_09_opposition_text;
//			case 31:return R.array.wands_10_opposition_text;
//			case 32:return 0;
//			case 33:return 0;
//			case 34:return 0;
//			case 35:return 0;
//	
//			case 36:return 0;
//			case 37:return R.array.cups_02_opposition_text;
//			case 38:return R.array.cups_03_opposition_text;
//			case 39:return R.array.cups_04_opposition_text;
//			case 40:return R.array.cups_05_opposition_text;
//			case 41:return R.array.cups_06_opposition_text;
//			case 42:return R.array.cups_07_opposition_text;
//			case 43:return R.array.cups_08_opposition_text;
//			case 44:return R.array.cups_09_opposition_text;
//			case 45:return R.array.cups_10_opposition_text;
//			case 46:return 0;
//			case 47:return 0;
//			case 48:return 0;
//			case 49:return 0;
//	
//			case 50:return 0;
//			case 51:return R.array.swords_02_opposition_text;
//			case 52:return R.array.swords_03_opposition_text;
//			case 53:return R.array.swords_04_opposition_text;
//			case 54:return R.array.swords_05_opposition_text;
//			case 55:return R.array.swords_06_opposition_text;
//			case 56:return R.array.swords_07_opposition_text;
//			case 57:return R.array.swords_08_opposition_text;
//			case 58:return R.array.swords_09_opposition_text;
//			case 59:return R.array.swords_10_opposition_text;
//			case 60:return 0;
//			case 61:return 0;
//			case 62:return 0;
//			case 63:return 0;
//	
//			case 64:return 0;
//			case 65:return R.array.pentacles_02_opposition_text;
//			case 66:return R.array.pentacles_03_opposition_text;
//			case 67:return R.array.pentacles_04_opposition_text;
//			case 68:return R.array.pentacles_05_opposition_text;
//			case 69:return 0;
//			case 70:return R.array.pentacles_07_opposition_text;
//			case 71:return R.array.pentacles_08_opposition_text;
//			case 72:return R.array.pentacles_09_opposition_text;
//			case 73:return R.array.pentacles_10_opposition_text;
//			case 74:return 0;
//			case 75:return 0;
//			case 76:return 0;
//			case 77:return 0;				
//		}
//		return 0;
//	}
//
//	public static int getReinforcementNumber(int index) {
//		switch (index) {
//			case 0:return R.array.trumps_01_reinforcement_number;
//			case 1:return R.array.trumps_02_reinforcement_number;
//			case 2:return R.array.trumps_03_reinforcement_number;
//			case 3:return R.array.trumps_04_reinforcement_number;
//			case 4:return R.array.trumps_05_reinforcement_number;
//			case 5:return R.array.trumps_06_reinforcement_number;
//			case 6:return R.array.trumps_07_reinforcement_number;
//			case 7:return R.array.trumps_08_reinforcement_number;
//			case 8:return R.array.trumps_09_reinforcement_number;
//			case 9:return R.array.trumps_10_reinforcement_number;
//			case 10:return R.array.trumps_11_reinforcement_number;
//			case 11:return R.array.trumps_12_reinforcement_number;
//			case 12:return R.array.trumps_13_reinforcement_number;
//			case 13:return R.array.trumps_14_reinforcement_number;
//			case 14:return R.array.trumps_15_reinforcement_number;
//			case 15:return R.array.trumps_16_reinforcement_number;
//			case 16:return R.array.trumps_17_reinforcement_number;
//			case 17:return R.array.trumps_18_reinforcement_number;
//			case 18:return R.array.trumps_19_reinforcement_number;		
//			case 19:return R.array.trumps_20_reinforcement_number;
//			case 20:return R.array.trumps_21_reinforcement_number;
//			case 21:return R.array.trumps_22_reinforcement_number;
//	
//			case 22:return R.array.wands_01_reinforcement_number;
//			case 23:return R.array.wands_02_reinforcement_number;
//			case 24:return R.array.wands_03_reinforcement_number;
//			case 25:return R.array.wands_04_reinforcement_number;
//			case 26:return R.array.wands_05_reinforcement_number;
//			case 27:return R.array.wands_06_reinforcement_number;
//			case 28:return R.array.wands_07_reinforcement_number;
//			case 29:return R.array.wands_08_reinforcement_number;
//			case 30:return R.array.wands_09_reinforcement_number;
//			case 31:return R.array.wands_10_reinforcement_number;
//			case 32:return 0;
//			case 33:return 0;
//			case 34:return 0;
//			case 35:return 0;
//	
//			case 36:return R.array.cups_01_reinforcement_number;
//			case 37:return R.array.cups_02_reinforcement_number;
//			case 38:return R.array.cups_03_reinforcement_number;
//			case 39:return R.array.cups_04_reinforcement_number;
//			case 40:return R.array.cups_05_reinforcement_number;
//			case 41:return R.array.cups_06_reinforcement_number;
//			case 42:return R.array.cups_07_reinforcement_number;
//			case 43:return R.array.cups_08_reinforcement_number;
//			case 44:return R.array.cups_09_reinforcement_number;
//			case 45:return R.array.cups_10_reinforcement_number;
//			case 46:return 0;
//			case 47:return 0;
//			case 48:return 0;
//			case 49:return 0;
//	
//			case 50:return R.array.swords_01_reinforcement_number;
//			case 51:return R.array.swords_02_reinforcement_number;
//			case 52:return R.array.swords_03_reinforcement_number;
//			case 53:return R.array.swords_04_reinforcement_number;
//			case 54:return R.array.swords_05_reinforcement_number;
//			case 55:return R.array.swords_06_reinforcement_number;
//			case 56:return R.array.swords_07_reinforcement_number;
//			case 57:return R.array.swords_08_reinforcement_number;
//			case 58:return R.array.swords_09_reinforcement_number;
//			case 59:return R.array.swords_10_reinforcement_number;
//			case 60:return 0;
//			case 61:return 0;
//			case 62:return 0;
//			case 63:return 0;
//	
//			case 64:return R.array.pentacles_01_reinforcement_number;
//			case 65:return R.array.pentacles_02_reinforcement_number;
//			case 66:return R.array.pentacles_03_reinforcement_number;
//			case 67:return R.array.pentacles_04_reinforcement_number;
//			case 68:return R.array.pentacles_05_reinforcement_number;
//			case 69:return R.array.pentacles_06_reinforcement_number;
//			case 70:return R.array.pentacles_07_reinforcement_number;
//			case 71:return R.array.pentacles_08_reinforcement_number;
//			case 72:return R.array.pentacles_09_reinforcement_number;
//			case 73:return R.array.pentacles_10_reinforcement_number;
//			case 74:return 0;
//			case 75:return 0;
//			case 76:return 0;
//			case 77:return 0;				
//		}
//		return 0;
//	}
//	
//	public static int getReinforcementText(int index) {
//		switch (index) {
//			case 0:return R.array.trumps_01_reinforcement_text;
//			case 1:return R.array.trumps_02_reinforcement_text;
//			case 2:return R.array.trumps_03_reinforcement_text;
//			case 3:return R.array.trumps_04_reinforcement_text;
//			case 4:return R.array.trumps_05_reinforcement_text;
//			case 5:return R.array.trumps_06_reinforcement_text;
//			case 6:return R.array.trumps_07_reinforcement_text;
//			case 7:return R.array.trumps_08_reinforcement_text;
//			case 8:return R.array.trumps_09_reinforcement_text;
//			case 9:return R.array.trumps_10_reinforcement_text;
//			case 10:return R.array.trumps_11_reinforcement_text;
//			case 11:return R.array.trumps_12_reinforcement_text;
//			case 12:return R.array.trumps_13_reinforcement_text;
//			case 13:return R.array.trumps_14_reinforcement_text;
//			case 14:return R.array.trumps_15_reinforcement_text;
//			case 15:return R.array.trumps_16_reinforcement_text;
//			case 16:return R.array.trumps_17_reinforcement_text;
//			case 17:return R.array.trumps_18_reinforcement_text;
//			case 18:return R.array.trumps_19_reinforcement_text;		
//			case 19:return R.array.trumps_20_reinforcement_text;
//			case 20:return R.array.trumps_21_reinforcement_text;
//			case 21:return R.array.trumps_22_reinforcement_text;
//	
//			case 22:return R.array.wands_01_reinforcement_text;
//			case 23:return R.array.wands_02_reinforcement_text;
//			case 24:return R.array.wands_03_reinforcement_text;
//			case 25:return R.array.wands_04_reinforcement_text;
//			case 26:return R.array.wands_05_reinforcement_text;
//			case 27:return R.array.wands_06_reinforcement_text;
//			case 28:return R.array.wands_07_reinforcement_text;
//			case 29:return R.array.wands_08_reinforcement_text;
//			case 30:return R.array.wands_09_reinforcement_text;
//			case 31:return R.array.wands_10_reinforcement_text;
//			case 32:return 0;
//			case 33:return 0;
//			case 34:return 0;
//			case 35:return 0;
//	
//			case 36:return R.array.cups_01_reinforcement_text;
//			case 37:return R.array.cups_02_reinforcement_text;
//			case 38:return R.array.cups_03_reinforcement_text;
//			case 39:return R.array.cups_04_reinforcement_text;
//			case 40:return R.array.cups_05_reinforcement_text;
//			case 41:return R.array.cups_06_reinforcement_text;
//			case 42:return R.array.cups_07_reinforcement_text;
//			case 43:return R.array.cups_08_reinforcement_text;
//			case 44:return R.array.cups_09_reinforcement_text;
//			case 45:return R.array.cups_10_reinforcement_text;
//			case 46:return 0;
//			case 47:return 0;
//			case 48:return 0;
//			case 49:return 0;
//	
//			case 50:return R.array.swords_01_reinforcement_text;
//			case 51:return R.array.swords_02_reinforcement_text;
//			case 52:return R.array.swords_03_reinforcement_text;
//			case 53:return R.array.swords_04_reinforcement_text;
//			case 54:return R.array.swords_05_reinforcement_text;
//			case 55:return R.array.swords_06_reinforcement_text;
//			case 56:return R.array.swords_07_reinforcement_text;
//			case 57:return R.array.swords_08_reinforcement_text;
//			case 58:return R.array.swords_09_reinforcement_text;
//			case 59:return R.array.swords_10_reinforcement_text;
//			case 60:return 0;
//			case 61:return 0;
//			case 62:return 0;
//			case 63:return 0;
//	
//			case 64:return R.array.pentacles_01_reinforcement_text;
//			case 65:return R.array.pentacles_02_reinforcement_text;
//			case 66:return R.array.pentacles_03_reinforcement_text;
//			case 67:return R.array.pentacles_04_reinforcement_text;
//			case 68:return R.array.pentacles_05_reinforcement_text;
//			case 69:return R.array.pentacles_06_reinforcement_text;
//			case 70:return R.array.pentacles_07_reinforcement_text;
//			case 71:return R.array.pentacles_08_reinforcement_text;
//			case 72:return R.array.pentacles_09_reinforcement_text;
//			case 73:return R.array.pentacles_10_reinforcement_text;
//			case 74:return 0;
//			case 75:return 0;
//			case 76:return 0;
//			case 77:return 0;				
//		}
//		return 0;
//	}
}
