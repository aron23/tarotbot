package liberus.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;


public class WebUtils {

	public static void Download(String Url, String destination, Context con, ProgressDialog pd)
	 {		
		Configuration conf =con.getResources().getConfiguration();
		 if ((conf.screenLayout&Configuration.SCREENLAYOUT_SIZE_MASK) != Configuration.SCREENLAYOUT_SIZE_LARGE)
			 return;
	  String filepath=null;
	  try {
	   //set the download URL, a url that points to a file on the internet
	   //this is the file to be downloaded
	   URL url = new URL(Url);
	   //create the new connection
	   HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	   //set up some things on the connection
	   urlConnection.setRequestMethod("GET");
	   urlConnection.setDoOutput(true); 
	    //and connect!
	   urlConnection.connect();
	   //set the path where we want to save the file
	   //in this case, going to save it on the root directory of the
	   //sd card.
	   File SDCardRoot = Environment.getExternalStorageDirectory();
	   //create a new file, specifying the path, and the filename
	   //which we want to save the file as.
	   
	   String filename= destination;   // you can download to any type of file ex:.jpeg (image) ,.txt(text file),.mp3 (audio file)
	   
	   File file = new File(filename);
	   if(file.createNewFile())
	   {
	    file.createNewFile();
	   }
	   
	   //this will be used to write the downloaded data into the file we created
	   FileOutputStream fileOutput = new FileOutputStream(file);

	   //this will be used in reading the data from the internet
	   InputStream inputStream = urlConnection.getInputStream();

	   //this is the total size of the file
	   int totalSize = urlConnection.getContentLength();
	   //variable to store total downloaded bytes
	   int downloadedSize = 0;

	   //create a buffer...
	   byte[] buffer = new byte[1024];
	   int bufferLength = 0; //used to store a temporary size of the buffer
	   
	   //now, read through the input buffer and write the contents to the file
	   double off = 0;
	   while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
		
	    //add the data in the buffer to the file in the file output stream (the file on the sd card
	    fileOutput.write(buffer, 0, bufferLength);
	    //add up the size so we know how much is downloaded
	    downloadedSize += bufferLength;
	    pd.setProgress((int)(100*((double)downloadedSize/(double)totalSize)));
	    
	    
	    
	    
	    //this is where you would do something to report the prgress, like this maybe
	    if (downloadedSize % 1024 == 0)
	    	System.err.println("downloadedSize:"+downloadedSize+"totalSize:"+ totalSize) ;

	   }
	   //close the output stream when done
	   fileOutput.close();
	   if(downloadedSize==totalSize)   filepath=file.getPath();
	   
	  //catch some possible errors...
	  } catch (MalformedURLException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   filepath=null;
	   e.printStackTrace();
	  }
	  System.err.println(" "+filepath) ;

	 }
	public static String saveTarotBot(String spread, String deck, String reversals, String title, String style, Context context) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		String saveResult = "";
		TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		HttpGet httpGet;
		title = title.replaceAll("\\s+", "+");
		if (style == "bota")
			httpGet = new HttpGet("http://liber.us/tarotbot/postspread.test.php?spread="+spread+"&deck="+deck+"&reversals="+reversals+"&significator="+BotaSpread.getSignificator()+"&title="+title+"&uid=private&sytle="+style);
		else
			httpGet = new HttpGet("http://liber.us/tarotbot/postspread.test.php?spread="+spread+"&deck="+deck+"&reversals="+reversals+"&significator="+0+"&title="+title+"&uid=private&style="+style);
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
		
		HttpGet httpGet = new HttpGet("http://liber.us/tarotbot/readspread.test.php?uid=private");
		
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

	
	
	public static String md5(String s) {  
	    try {  
	        // Create MD5 Hash  
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");  
	        digest.update(s.getBytes());  
	        byte messageDigest[] = digest.digest();  
	          
	        // Create Hex String  
	        StringBuffer hexString = new StringBuffer();  
	        for (int i=0; i<messageDigest.length; i++)  
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));  
	        return hexString.toString();  
	          
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    }  
	    return "";  
	}
	
	
}
