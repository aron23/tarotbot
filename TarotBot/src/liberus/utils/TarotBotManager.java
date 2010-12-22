package liberus.utils;

import java.lang.reflect.Method;

import android.app.ActivityManager;

import android.content.Context;

public class TarotBotManager {
	private static Method getMemClass;
	public static boolean isCompatible() {
	       try {
	    	   getMemClass = ActivityManager.class.getMethod(
	                   "getMemoryClass", (Class[])null );
	           return true;
	       } catch (NoSuchMethodException nsme) {
	           return false;
	       }
	   }
	public static boolean hasEnoughMemory(int thisMuch, Context c) {
		if (!isCompatible()) 
			return false;
		if (getMemoryClass(c) >= thisMuch)
			return true;
		return false;
	}
	public static int getMemoryClass(Context c) {
		try {
			ActivityManager mgr = (ActivityManager) c.getSystemService( Context.ACTIVITY_SERVICE );
			return (Integer) getMemClass.invoke(mgr, (Object[])null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 16;
	}
}
