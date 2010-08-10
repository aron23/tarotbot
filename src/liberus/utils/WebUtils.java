package liberus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import liberus.tarot.interpretation.BotaInt;
import liberus.tarot.spread.BotaSpread;

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

    
	public static String saveTarotBot(String spread, String deck, String reversals, String title, String style, Context context) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		String saveResult = "";
		TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		HttpGet httpGet;
		if (style == "bota")
			httpGet = new HttpGet("http://liber.us/tarotbot/postspread.test.php?spread="+spread+"&deck="+deck+"&reversals="+reversals+"&significator="+BotaSpread.getSignificator()+"&title="+title+"&uid="+tel.getDeviceId()+"&sytle="+style);
		else
			httpGet = new HttpGet("http://liber.us/tarotbot/postspread.test.php?spread="+spread+"&deck="+deck+"&reversals="+reversals+"&significator="+0+"&title="+title+"&uid="+tel.getDeviceId()+"&style="+style);
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
		
		HttpGet httpGet = new HttpGet("http://liber.us/tarotbot/readspread.test.php?uid="+tel.getDeviceId());
		
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			String reading= reader.readLine();
			while (reading != null) {
				if (reading.length() > 0) {
				String[] read = reading.split(",");
				loadResult.add(read);				
				}
				reading= reader.readLine();
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
//
	public static String bitly(String saveResult) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://api.bit.ly/v3/shorten?login=aron23&apiKey=R_ae5060608a3d31022536ce4eac996546&format=txt&longUrl="+URLEncoder.encode("http://liber.us/tarotbot/reading?id="+saveResult));
		//Toast.makeText(context, "http://liber.us/tarotbot/postspread.php?spread="+spread+"&deck="+deck+"&reversals="+reversals+"&significator="+BotaInt.getSignificator()+"&title="+title+"&uid="+tel.getDeviceId(), Toast.LENGTH_LONG).show();
		String encoded = "";
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			
			String line = null;
			while ((line = reader.readLine()) != null){				
				encoded += line;
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
		
		return encoded;
	}
	public static ArrayList<String[]> loadTarotBotReading(Context applicationContext, int id) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		ArrayList<String[]> deck = new ArrayList<String[]>();
		HttpGet httpGet = new HttpGet("http://liber.us/tarotbot/pullspread.php?id="+id);
		
		try {
			HttpResponse response = httpClient.execute(httpGet, localContext);
			 
			BufferedReader reader = new BufferedReader(
			    new InputStreamReader(
			      response.getEntity().getContent()
			    )
			  );
			String reading= reader.readLine();
			while (reading != null) {
				if (reading.length() > 0) {
					String[] decked = reading.split(",");
					deck.add(decked);
				}
				reading= reader.readLine();
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
		return deck;
	}

	
	
	
	
	
}
