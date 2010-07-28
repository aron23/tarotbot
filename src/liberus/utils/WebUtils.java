package liberus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import liberus.tarot.interpretation.BotaInt;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.telephony.TelephonyManager;


public class WebUtils {

    
	public static String saveTarotBot(String spread, String deck, String reversals, String title, Context context) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		String saveResult = "";
		TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		HttpGet httpGet = new HttpGet("http://liber.us/tarotbot/postspread.php?spread="+spread+"&deck="+deck+"&reversals="+reversals+"&significator="+BotaInt.getSignificator()+"&title="+title+"&uid="+tel.getDeviceId());
		//Toast.makeText(context, "http://liber.us/tarotbot/postspread.php?spread="+spread+"&deck="+deck+"&reversals="+reversals+"&significator="+BotaInt.getSignificator()+"&title="+title+"&uid="+tel.getDeviceId(), Toast.LENGTH_LONG).show();
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			
			String line = null;
			while ((line = reader.readLine()) != null){				
				saveResult += line;
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saveResult;
	}

	public static ArrayList<String[]> loadTarotBot(Context context) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		ArrayList<String[]> loadResult = new ArrayList<String[]>();
		TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		HttpGet httpGet = new HttpGet("http://liber.us/tarotbot/readspread.php?uid="+tel.getDeviceId());
		
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			
			
			
			ArrayList<String> tester = new ArrayList<String>();
			tester.add("116,129,167,171,149,113,130,144,160,136,166,110,104,117,172,161,140,139,159;1,0,1,1,1,0,0,0,0,1,1,0,0,1,0,1,0,1,0");
			tester.add("107,176,126,177,118,161,153,158,103,137,129,114,173,172,141,142,133,160,102,167;0,1,0,0,1,1,0,1,1,0,1,1,0,1,0,1,1,1,1,0");
			for (String line: tester) {
			//String line = null;
			//while ((line = reader.readLine()) != null){	
				String[] reading = new String[2];
				reading[0] = line.split(";")[0];
				reading[1] = line.split(";")[1];
				loadResult.add(reading);
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loadResult;
	}

	
	
	
	
	
}
