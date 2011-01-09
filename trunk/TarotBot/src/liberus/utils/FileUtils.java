package liberus.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.graphics.BitmapFactory;

public class FileUtils {
	public static void createDir(File dir) {		
		if (!dir.mkdirs()) {
		throw new RuntimeException("Can not create dir " + dir);
		}
	}
	public static void copy(File in, File out) {
		writeFile(out, in);
	}
	private static void writeFile(File outfile, File in) {
		try {
			  // open myfilename.txt for writing
			  OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outfile));
			  // write the contents on mySettings to the file
			  out.write(readFile(in));
			  // close the file
			  out.close();
			} catch (java.io.IOException e) {
			  //do something if an IOException occurs.
			}
	}
	public static String readFile(File in) {
		String content = "";

		  try {
		    // open the file for reading
		    InputStream instream = new FileInputStream(in);
		 
		    
		      // prepare the file for reading
		      InputStreamReader inputreader = new InputStreamReader(instream);
		      BufferedReader buffreader = new BufferedReader(inputreader);
		 
		      String line;
		 
		      // read every line of the file into the line-variable, on line at the time
		      while (( line = buffreader.readLine()) != null) {
		    	  content += line;
		      }
		    // close the file again
		    instream.close();
		  } catch (java.io.FileNotFoundException e) {
		    // do something if the myfilename.txt does not exits
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return content;
	}
	public static void copyZipStream(ZipEntry entry, ZipInputStream in, File outfile) {
		writeZipStream(entry,outfile, in);		
	}
	private static void writeZipStream(ZipEntry entry, File outfile, ZipInputStream in) {
		byte[] buf = null;

		  try {
		      // prepare the file for reading
			  BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outfile),(int)entry.getSize());
		   
		 
    		buf = new byte[(int)entry.getSize()];
    		for (int n = 0, x; n < buf.length; n += x ) {
    			x = in.read(buf, n, buf.length);
    			out.write(buf);
    		}    		
    		out.close();  		
		  } catch (java.io.FileNotFoundException e) {
		    // do something if the myfilename.txt does not exits
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
