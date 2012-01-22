package liberus.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.ActivityManager;

import android.content.Context;
import android.os.Build;

public class TarotBotManager {
	private static Method getMemClass;
	private static Field getAPI;
	public static boolean isCompatible() {
	       try {
	    	   getMemClass = ActivityManager.class.getMethod(
	                   "getMemoryClass", (Class[])null );
	           return true;
	       } catch (NoSuchMethodException nsme) {
	           return false;
	       }
	   }
	public static boolean isDonut() {
	       try {
	    	   getAPI = Build.VERSION.class.getField("SDK_INT");
	           return true;
	       } catch (SecurityException e) {
	    	   return false;
			} catch (NoSuchFieldException e) {
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
	public static boolean isCompatibleAPI(int thisMuch, Context c) {
		if (!isDonut()) 
			return false;
		if (getAPI(c) >= thisMuch)
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
	public static int getAPI(Context c) {

		try {
			ActivityManager mgr = (ActivityManager) c.getSystemService( Context.ACTIVITY_SERVICE );
			return (Integer) getAPI.getInt(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static boolean hasStrongConnection(Context con) {
		if (WebUtils.checkWiFi(con) || WebUtils.check4g(con))
			return true;
		return false;
	}
}
